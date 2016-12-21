package com.luxoft.parser;

import com.luxoft.model.CustomDate;
import com.luxoft.model.Employee;
import com.luxoft.model.WorkTime;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by user on 21.12.2016.
 */
public class ExcelParser implements Parser {

    private static final int DATES_ROW_INDEX = 2;
    private static final int DATES_FIRST_COLUMN_INDEX = 4;
    private static final int EMPLOYEE_FIRST_ROW_INDEX = 4;
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final DataFormatter CELL_FORMATTER = new DataFormatter();
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public Collection<Employee> parse(InputStream input) throws Exception {
        Map<Long, Employee> employeeMap = new HashMap<>();
        Map<Long, Long> employeeManagerMap = new HashMap<>();
        Workbook wb = WorkbookFactory.create(input);
        Sheet sheet = wb.getSheetAt(0);

        Collection<LocalDate> dates = getDates(sheet);

        int ri = EMPLOYEE_FIRST_ROW_INDEX;
        while (true) {
            Row row = sheet.getRow(ri++);
            EmployeeInfo employeeInfo = getEmployeeInfo(row);
            if (employeeInfo == null) {
                break;
            }

            Employee employee = new Employee(employeeInfo.name, employeeInfo.program, null);
            employeeMap.put(employeeInfo.id, employee);
            employeeManagerMap.put(employeeInfo.id, employeeInfo.managerId);

            Collection<WorkTime> workTimes = getWorkTimes(row, EmployeeColumn.values().length, dates, employee);
            workTimes.forEach(employee::addWorkTime);
        }
        return linkEmployeeWithManagers(employeeMap, employeeManagerMap);
    }

    private Collection<LocalDate> getDates(Sheet sheet) {
        Collection<LocalDate> dates = new ArrayList<>();
        Row row = sheet.getRow(DATES_ROW_INDEX);
        int ci = DATES_FIRST_COLUMN_INDEX;
        while (true) {
            Cell dateCell = row.getCell(ci);
            if (dateCell == null || StringUtils.isEmpty(dateCell.getStringCellValue())) break;
            LocalDate date = LocalDate.parse(dateCell.getStringCellValue(), DateTimeFormatter.ofPattern(DATE_PATTERN));
            dates.add(date);
            ci += 4;
        }
        return dates;
    }

    private EmployeeInfo getEmployeeInfo(Row row) {
        Cell idCell = row.getCell(EmployeeColumn.ID.ordinal());
        if (idCell == null) {
            return null;
        }

        EmployeeInfo employeeInfo = new EmployeeInfo();

        employeeInfo.id = (long) idCell.getNumericCellValue();
        Cell managerCell = row.getCell(EmployeeColumn.MANAGER_ID.ordinal());
        employeeInfo.managerId = managerCell != null ? (long) managerCell.getNumericCellValue() : null;
        employeeInfo.name = row.getCell(EmployeeColumn.NAME.ordinal()).getStringCellValue();
        employeeInfo.program = row.getCell(EmployeeColumn.PROGRAM.ordinal()).getStringCellValue();

        return employeeInfo;
    }

    private Collection<WorkTime> getWorkTimes(Row row, int firstCellIndex, Collection<LocalDate> dates, Employee employee) {
        Collection<WorkTime> workTimes = new ArrayList<>();
        int lastCellIndex = firstCellIndex + dates.size() * 4;
        Iterator<LocalDate> dateIterator = dates.iterator();
        for (int ci = firstCellIndex; ci < lastCellIndex; ci += 4) {
            LocalTime entry = getCellValueAsTime(row, ci);
            LocalTime exit = getCellValueAsTime(row, ci + 1);
            LocalTime pure = getCellValueAsTime(row, ci + 2);
            LocalTime total = getCellValueAsTime(row, ci + 3);
            WorkTime workTime = new WorkTime(new CustomDate(dateIterator.next()), employee, entry, exit, total, pure);
            workTimes.add(workTime);
        }
        return workTimes;
    }

    private LocalTime getCellValueAsTime(Row row, int ci) {
        String value = CELL_FORMATTER.formatCellValue(row.getCell(ci));
        return !StringUtils.isEmpty(value) ? LocalTime.parse(value, TIME_FORMATTER) : null;
    }

    private Collection<Employee> linkEmployeeWithManagers(Map<Long, Employee> employeeMap, Map<Long, Long> employeeManagerMap) {
        for (Map.Entry<Long, Employee> employeeEntry : employeeMap.entrySet()) {
            Long managerId = employeeManagerMap.get(employeeEntry.getKey());
            Employee manager = employeeMap.get(managerId);
            employeeEntry.getValue().setManager(manager);
        }
        return employeeMap.values();
    }

    private static class EmployeeInfo {
        public Long id;
        public Long managerId;
        public String name;
        public String program;
    }

    private enum EmployeeColumn {
        ID,
        MANAGER_ID,
        NAME,
        PROGRAM
    }

    public static void main(String[] args) throws Exception {
        String fileName = "C:\\Users\\user\\Downloads\\Демо отчет SPb_21.11-27.11.2016.xls";
        Collection<Employee> employees = new ExcelParser().parse(new FileInputStream(fileName));
        employees.forEach(e -> System.out.printf("%s (%s) - %s%n", e.getName(), (e.getManager() != null ? e.getManager().getName() : null), e.getWorkTimes().iterator().next().getDate()));
    }

}

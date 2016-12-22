/*
 * Copyright 2001-2016, Deutsche Bank AG. All Rights Reserved.
 * Confidential and Proprietary Information of Deutsche Bank.
 *
 * @author: Alexey Grishkov
 * Created: 22.12.2016
 */
package com.luxoft.init;

import com.luxoft.model.Employee;
import com.luxoft.parser.ExcelParser;
import com.luxoft.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;

import static java.nio.file.FileVisitResult.CONTINUE;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    @Value("${excel.reports.dir}")
    private String excelReportsDir;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void run(String... strings) throws Exception {
        Collection<Employee> employees = parseExcelReports();
        employees.forEach(employeeRepository::save);
    }

    private Collection<Employee> parseExcelReports() throws IOException {
        Collection<Employee> allEmployees = new ArrayList<>();

        String reportsPath = getReportsPath();

        final ExcelParser excelParser = new ExcelParser();
        Files.walkFileTree(Paths.get(reportsPath), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attr) {

                if (attr.isRegularFile() && file.getFileName().toString().endsWith(".xls")) {
                    try {
                        Collection<Employee> employees = excelParser.parse(Files.newInputStream(file));
                        allEmployees.addAll(employees);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse file " + file.toString(), e);
                    }
                }
                return CONTINUE;
            }
        });
        return allEmployees;
    }

    private String getReportsPath() {
        String reportsPath;
        final String CLASSPATH_PREFIX = "classpath:";
        if (excelReportsDir.startsWith(CLASSPATH_PREFIX)) {
            URL excelReportsResource = getClass().getClassLoader().getResource(excelReportsDir.substring(CLASSPATH_PREFIX.length()));
            if (excelReportsResource == null) {
                throw new RuntimeException("Cannot find reports directory at " + excelReportsDir);
            }
            reportsPath = excelReportsResource.getPath().substring(1); // removes the starting "/"
        } else {
            reportsPath = excelReportsDir;
        }
        return reportsPath;
    }
}

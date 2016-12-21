package com.luxoft.parser;

import com.luxoft.model.Employee;

import java.io.InputStream;
import java.util.Collection;

/**
 * Created by user on 21.12.2016.
 */
public class ExcelParser implements Parser {
    @Override
    public Collection<Employee> parse(InputStream is) {
//        Workbook wb = WorkbookFactory.create(new FileInputStream("MyExcel.xlsx"));
        return null;
    }
}

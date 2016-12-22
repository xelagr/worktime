/*
 * Copyright 2001-2016, Deutsche Bank AG. All Rights Reserved.
 * Confidential and Proprietary Information of Deutsche Bank.
 *
 * @author: Alexey Grishkov
 * Created: 22.12.2016
 */
package com.luxoft;

import com.luxoft.helper.DefaultTestSettings;
import com.luxoft.model.Employee;
import com.luxoft.parser.ExcelParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DefaultTestSettings
public class ExcelParserTest {

    @Value("${excel.file}")
    private String excelFilePath;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void testLoadFile() throws Exception {
        Resource excelFile = resourceLoader.getResource(excelFilePath);
        List<Employee> employees = new ArrayList<>(new ExcelParser().parse(excelFile.getInputStream()));
        assertThat(employees.size(), is(16));
        assertNull(employees.get(0).getManager());
        assertThat(employees.get(1).getManager(), is(employees.get(0)));
    }
}

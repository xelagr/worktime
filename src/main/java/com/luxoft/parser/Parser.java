package com.luxoft.parser;

import com.luxoft.model.Employee;

import java.io.InputStream;
import java.util.Collection;

/**
 * Created by user on 21.12.2016.
 */
public interface Parser {
    Collection<Employee> parse(InputStream is) throws Exception;
}

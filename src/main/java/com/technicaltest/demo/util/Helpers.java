package com.technicaltest.demo.util;

import com.technicaltest.demo.DemoApplication;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Slf4j
public class Helpers {

    private Helpers() {
        throw new UnsupportedOperationException("Unsupported operation exception");
    }

    public static LocalDate toDate(String stringAsDate) {
        var formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(stringAsDate, formatter);
    }

    public static String removeWhiteSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    public static String readFile(String fileName) {
        try (InputStream is = DemoApplication.class.getClassLoader().getResourceAsStream(fileName)) {
            log.info("readFile InputStream is not null {} ", is != null);
            if (is == null) return "";
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (Exception ex) {
            log.error("Unable to read resource {}, error: {}", fileName, ex.getMessage());
            return "";
        }
    }
}

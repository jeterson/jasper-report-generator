package com.bergamota.jasperreports.common.domain.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public final class ParseUtils {

    private static SimpleDateFormat simpleDateFormat;

    public static List<?> toCollection(Object value) {
        try {
            return (List<?>) value;
        }catch (Exception e) {
            return List.of(value);
        }
    }

    public static Optional<Double> parseDouble(Object value) {
        try {
            return Optional.of(Double.parseDouble(parseString(value)));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    public static Optional<Integer> parseInteger(Object value) {
        try {
            return Optional.of(Integer.parseInt(parseString(value)));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    public static Optional<Boolean> parseBoolean(Object value) {
        try {
            var str = parseString(value).toLowerCase();

            if(str.equals("s") || str.equals("y") || str.equals("1") || str.equals("sim") || str.equals("yes"))
                str = "true";
            else
                str = "false";

            return Optional.of(Boolean.parseBoolean(str));
        }catch (Exception e) {
            return Optional.of(false);
        }
    }
    public static Optional<String> parseToYesNo(Object value){
        return parseBoolean(value).isPresent() ? parseBoolean(value).map(e -> e ? "S" : "N") : Optional.of("N");

    }
    public static Optional<Date> parseDateUtil(Object value, String format) {
        simpleDateFormat = new SimpleDateFormat(format);
        try {
            return Optional.of(simpleDateFormat.parse(parseString(value)));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    public static Optional<LocalDate> parseLocalDate(Object value, String format) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        try {
            return Optional.of(LocalDate.parse(parseString(value), dtf));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    public static Optional<String> formatLocalDate(LocalDate value, String format) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        try {
            return Optional.of(dtf.format(value));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    public static Optional<LocalDate> dateToLocalDate(Date value, String format) {
        var dateStr = formatDateUtil(value, format);
        return parseLocalDate(dateStr.orElse(""), format);
    }
    public static Optional<Date> localDateToDate(LocalDate value, String format) {
        var dateStr = formatLocalDate(value, format);
        return parseDateUtil(dateStr.orElse(""), format);
    }
    public static Optional<String> formatDateUtil(Date value, String format) {
        simpleDateFormat = new SimpleDateFormat(format);
        try {
            return Optional.of(simpleDateFormat.format(value));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    public static String parseString(Object value) {
        return String.valueOf(value);
    }
}

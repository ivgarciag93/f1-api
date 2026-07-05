package org.ivione93.utils;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@ApplicationScoped
public class TimeUtils {

  private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static LocalDate parseDate(String date) {
    if (date == null || date.isBlank()) {
      throw new IllegalArgumentException("Date cannot be null or blank");
    }
    try {
      return LocalDate.parse(date, YYYY_MM_DD);
    } catch (DateTimeParseException e) {
      return LocalDate.parse(date, DD_MM_YYYY);
    }
  }
}

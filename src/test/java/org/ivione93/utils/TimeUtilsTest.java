package org.ivione93.utils;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TimeUtilsTest {

  @Inject TimeUtils timeUtils;

  @Test
  @DisplayName("parseDate should return LocalDate when format is yyyy-MM-dd")
  void testParseDate_YyyyMmDd_Success() {
    LocalDate result = timeUtils.parseDate("2024-07-04");

    assertNotNull(result);
    assertEquals(2024, result.getYear());
    assertEquals(7, result.getMonthValue());
    assertEquals(4, result.getDayOfMonth());
  }

  @Test
  @DisplayName("parseDate should return LocalDate when format is dd/MM/yyyy")
  void testParseDate_DdMmYyyy_Success() {
    LocalDate result = timeUtils.parseDate("04/07/2024");

    assertNotNull(result);
    assertEquals(2024, result.getYear());
    assertEquals(7, result.getMonthValue());
    assertEquals(4, result.getDayOfMonth());
  }

  @Test
  @DisplayName("parseDate should throw exception when date is null")
  void testParseDate_Null_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> timeUtils.parseDate(null));
  }

  @Test
  @DisplayName("parseDate should throw exception when date is blank")
  void testParseDate_Blank_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> timeUtils.parseDate("  "));
  }

  @Test
  @DisplayName("parseDate should throw exception when date is invalid format")
  void testParseDate_InvalidFormat_ThrowsException() {
    assertThrows(DateTimeParseException.class, () -> timeUtils.parseDate("2024/07/04"));
  }
}

package org.ivione93.services.converters;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.ivione93.dto.f1api.DriversResponse;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.dto.f1api.TeamsResponse;
import org.ivione93.dto.f1api.drivers.F1Driver;
import org.ivione93.dto.f1api.drivers.F1DriversResponse;
import org.ivione93.dto.f1api.seasons.F1Championship;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.dto.f1api.teams.F1Team;
import org.ivione93.dto.f1api.teams.F1TeamsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class F1ConverterTest {

  @Inject F1Converter f1Converter;

  // SEASONS
  @Test
  @DisplayName(
      "fillSeasonsResponse should convert F1SeasonsResponse to SeasonsResponse when all is ok")
  void testFillSeasonsResponse_Success() {
    F1SeasonsResponse f1Response =
        new F1SeasonsResponse(
            30, 0, 1, List.of(new F1Championship("f1", "Formula 1", "https://example.com", 2024)));

    SeasonsResponse result = f1Converter.fillSeasonsResponse(f1Response);

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(1, result.totalResults());
    assertEquals(1, result.championships().size());
    assertEquals("Formula 1", result.championships().getFirst().name());
    assertEquals("https://example.com", result.championships().getFirst().url());
    assertEquals(2024, result.championships().getFirst().year());
  }

  @Test
  @DisplayName("fillSeasonsResponse should return empty championships when empty list")
  void testFillSeasonsResponse_EmptyChampionships() {
    F1SeasonsResponse f1Response = new F1SeasonsResponse(0, 0, 0, List.of());

    SeasonsResponse result = f1Converter.fillSeasonsResponse(f1Response);

    assertNotNull(result);
    assertEquals(0, result.limit());
    assertEquals(0, result.offset());
    assertEquals(0, result.totalResults());
    assertTrue(result.championships().isEmpty());
  }

  @Test
  @DisplayName("fillSeasonsResponse should handle null values when fields are null")
  void testFillSeasonsResponse_NullFields() {
    F1SeasonsResponse f1Response = new F1SeasonsResponse(null, null, null, List.of());

    SeasonsResponse result = f1Converter.fillSeasonsResponse(f1Response);

    assertNotNull(result);
    assertNull(result.limit());
    assertNull(result.offset());
    assertNull(result.totalResults());
    assertTrue(result.championships().isEmpty());
  }

  // CURRENT TEAMS
  @Test
  @DisplayName(
      "fillCurrentTeamsResponse should convert F1TeamsResponse to TeamsResponse when all is ok")
  void testFillCurrentTeamsResponse_Success() {
    F1TeamsResponse f1Response =
        new F1TeamsResponse(
            30,
            0,
            1,
            2024,
            List.of(
                new F1Team(
                    "red_bull", "Red Bull Racing", "Austria", 2005, 6, 7, "https://example.com")));

    TeamsResponse result = f1Converter.fillCurrentTeamsResponse(f1Response);

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(1, result.totalResults());
    assertEquals(1, result.teams().size());
    assertEquals("Red Bull Racing", result.teams().getFirst().name());
    assertEquals("Austria", result.teams().getFirst().nationality());
    assertEquals(2005, result.teams().getFirst().year());
    assertEquals(6, result.teams().getFirst().teamWins());
    assertEquals(7, result.teams().getFirst().driverWins());
  }

  @Test
  @DisplayName("fillCurrentTeamsResponse should return empty teams when empty list")
  void testFillCurrentTeamsResponse_EmptyTeams() {
    F1TeamsResponse f1Response = new F1TeamsResponse(0, 0, 0, 2024, List.of());

    TeamsResponse result = f1Converter.fillCurrentTeamsResponse(f1Response);

    assertNotNull(result);
    assertEquals(0, result.limit());
    assertEquals(0, result.offset());
    assertEquals(0, result.totalResults());
    assertTrue(result.teams().isEmpty());
  }

  @Test
  @DisplayName("fillCurrentTeamsResponse should handle null values when fields are null")
  void testFillCurrentTeamsResponse_NullFields() {
    F1TeamsResponse f1Response = new F1TeamsResponse(null, null, null, null, List.of());

    TeamsResponse result = f1Converter.fillCurrentTeamsResponse(f1Response);

    assertNotNull(result);
    assertNull(result.limit());
    assertNull(result.offset());
    assertNull(result.totalResults());
    assertTrue(result.teams().isEmpty());
  }

  @Test
  @DisplayName("fillCurrentTeamsResponse should convert multiple teams when multiple entries")
  void testFillCurrentTeamsResponse_MultipleTeams() {
    F1TeamsResponse f1Response =
        new F1TeamsResponse(
            30,
            0,
            2,
            2024,
            List.of(
                new F1Team(
                    "red_bull", "Red Bull Racing", "Austria", 2005, 6, 7, "https://example.com"),
                new F1Team("ferrari", "Ferrari", "Italy", 1950, 16, 15, "https://example.com")));

    TeamsResponse result = f1Converter.fillCurrentTeamsResponse(f1Response);

    assertNotNull(result);
    assertEquals(2, result.totalResults());
    assertEquals(2, result.teams().size());
    assertEquals("Red Bull Racing", result.teams().get(0).name());
    assertEquals("Ferrari", result.teams().get(1).name());
  }

  @Test
  @DisplayName("fillCurrentTeamsResponse should handle null championships with default zero")
  void testFillCurrentTeamsResponse_NullChampionships_DefaultZero() {
    F1TeamsResponse f1Response =
        new F1TeamsResponse(
            30,
            0,
            1,
            2024,
            List.of(
                new F1Team(
                    "red_bull",
                    "Red Bull Racing",
                    "Austria",
                    2005,
                    null,
                    null,
                    "https://example.com")));

    TeamsResponse result = f1Converter.fillCurrentTeamsResponse(f1Response);

    assertNotNull(result);
    assertEquals(1, result.teams().size());
    assertEquals(0, result.teams().getFirst().teamWins());
    assertEquals(0, result.teams().getFirst().driverWins());
  }

  // CURRENT DRIVERS
  @Test
  @DisplayName(
      "fillCurrentDriversResponse should convert F1DriversResponse to DriversResponse when all is ok")
  void testFillCurrentDriversResponse_Success() {
    F1DriversResponse f1Response =
        new F1DriversResponse(
            30,
            0,
            1,
            2024,
            "f1",
            List.of(
                new F1Driver(
                    "ver",
                    "Max",
                    "Verstappen",
                    "Netherlands",
                    "1997-09-30",
                    1,
                    "VER",
                    "https://example.com",
                    "red_bull")));

    DriversResponse result = f1Converter.fillCurrentDriversResponse(f1Response);

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(1, result.totalResults());
    assertEquals(1, result.drivers().size());
    assertEquals("Max Verstappen", result.drivers().getFirst().name());
    assertEquals("Netherlands", result.drivers().getFirst().nationality());
    assertEquals(LocalDate.of(1997, 9, 30), result.drivers().getFirst().birthday());
    assertEquals(1, result.drivers().getFirst().number());
    assertEquals("red_bull", result.drivers().getFirst().teamId());
  }

  @Test
  @DisplayName("fillCurrentDriversResponse should return empty drivers when empty list")
  void testFillCurrentDriversResponse_EmptyDrivers() {
    F1DriversResponse f1Response = new F1DriversResponse(0, 0, 0, 2024, "f1", List.of());

    DriversResponse result = f1Converter.fillCurrentDriversResponse(f1Response);

    assertNotNull(result);
    assertEquals(0, result.limit());
    assertEquals(0, result.offset());
    assertEquals(0, result.totalResults());
    assertTrue(result.drivers().isEmpty());
  }

  @Test
  @DisplayName("fillCurrentDriversResponse should handle null values when fields are null")
  void testFillCurrentDriversResponse_NullFields() {
    F1DriversResponse f1Response = new F1DriversResponse(null, null, null, null, null, List.of());

    DriversResponse result = f1Converter.fillCurrentDriversResponse(f1Response);

    assertNotNull(result);
    assertNull(result.limit());
    assertNull(result.offset());
    assertNull(result.totalResults());
    assertTrue(result.drivers().isEmpty());
  }

  @Test
  @DisplayName("fillCurrentDriversResponse should convert multiple drivers when multiple entries")
  void testFillCurrentDriversResponse_MultipleDrivers() {
    F1DriversResponse f1Response =
        new F1DriversResponse(
            30,
            0,
            2,
            2024,
            "f1",
            List.of(
                new F1Driver(
                    "ver",
                    "Max",
                    "Verstappen",
                    "Netherlands",
                    "1997-09-30",
                    1,
                    "VER",
                    "https://example.com",
                    "red_bull"),
                new F1Driver(
                    "ham",
                    "Lewis",
                    "Hamilton",
                    "Great Britain",
                    "1985-01-07",
                    44,
                    "HAM",
                    "https://example.com",
                    "ferrari")));

    DriversResponse result = f1Converter.fillCurrentDriversResponse(f1Response);

    assertNotNull(result);
    assertEquals(2, result.totalResults());
    assertEquals(2, result.drivers().size());
    assertEquals("Max Verstappen", result.drivers().get(0).name());
    assertEquals("Lewis Hamilton", result.drivers().get(1).name());
  }
}

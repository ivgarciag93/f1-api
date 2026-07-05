package org.ivione93.collaborative;

import org.ivione93.dto.f1.Driver;
import org.ivione93.dto.f1.Team;
import org.ivione93.dto.f1.DriversResponse;
import org.ivione93.dto.f1.SeasonsResponse;
import org.ivione93.dto.f1.TeamsResponse;

import java.time.LocalDate;
import java.util.List;

public class BaseTest {

  protected SeasonsResponse createSeasonsResponse() {
    return new SeasonsResponse(
        30,
        0,
        2,
        List.of(
            new SeasonsResponse.Championship(
                "2026 Formula 1 World Championship",
                "https://en.wikipedia.org/wiki/2026_Formula_One_World_Championship",
                2026),
            new SeasonsResponse.Championship(
                "2025 Formula 1 World Championship",
                "https://en.wikipedia.org/wiki/2025_Formula_One_World_Championship",
                2025)));
  }

  protected TeamsResponse createTeamsResponse() {
    return new TeamsResponse(
        30,
        0,
        2,
        List.of(
            new Team("Red Bull Racing", "Austrian", 2024, 25, 7),
            new Team("Mercedes", "German", 2024, 20, 8)));
  }

  protected DriversResponse createDriversResponse() {
    return new DriversResponse(
        30,
        0,
        2,
        List.of(
            new Driver("Max Verstappen", "Netherlands", LocalDate.of(1997, 9, 30), 1, "red_bull"),
            new Driver(
                "Lewis Hamilton", "Great Britain", LocalDate.of(1985, 1, 7), 44, "ferrari")));
  }
}

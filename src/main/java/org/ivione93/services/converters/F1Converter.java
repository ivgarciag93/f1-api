package org.ivione93.services.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.ivione93.dto.f1.Driver;
import org.ivione93.dto.f1.Team;
import org.ivione93.dto.f1.DriversResponse;
import org.ivione93.dto.f1.SeasonsResponse;
import org.ivione93.dto.f1.TeamsResponse;
import org.ivione93.dto.f1api.drivers.F1Driver;
import org.ivione93.dto.f1api.drivers.F1DriversResponse;
import org.ivione93.dto.f1api.seasons.F1Championship;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.dto.f1api.teams.F1Team;
import org.ivione93.dto.f1api.teams.F1TeamsResponse;
import org.ivione93.utils.TimeUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class F1Converter {

  private static final String SEPARATOR = " ";

  public SeasonsResponse fillSeasonsResponse(final F1SeasonsResponse f1SeasonsResponse) {
    return new SeasonsResponse(
        f1SeasonsResponse.limit(),
        f1SeasonsResponse.offset(),
        f1SeasonsResponse.total(),
        fillChampionships(f1SeasonsResponse.championships()));
  }

  public TeamsResponse fillCurrentTeamsResponse(final F1TeamsResponse f1TeamsResponse) {
    return new TeamsResponse(
        f1TeamsResponse.limit(),
        f1TeamsResponse.offset(),
        f1TeamsResponse.total(),
        fillTeams(f1TeamsResponse.teams()));
  }

  public DriversResponse fillCurrentDriversResponse(final F1DriversResponse f1DriversResponse) {
    return new DriversResponse(
        f1DriversResponse.limit(),
        f1DriversResponse.offset(),
        f1DriversResponse.total(),
        fillDrivers(f1DriversResponse.drivers()));
  }

  private List<SeasonsResponse.Championship> fillChampionships(
      final List<F1Championship> f1championships) {
    if (f1championships.isEmpty()) return Collections.emptyList();
    return f1championships.stream()
        .map(
            f1Championship ->
                new SeasonsResponse.Championship(
                    f1Championship.championshipName(), f1Championship.url(), f1Championship.year()))
        .toList();
  }

  private List<Team> fillTeams(final List<F1Team> f1Teams) {
    if (f1Teams.isEmpty()) return Collections.emptyList();
    return f1Teams.stream()
        .map(
            f1Team ->
                new Team(
                    f1Team.teamName(),
                    f1Team.teamNationality(),
                    f1Team.firstAppeareance(),
                    Optional.ofNullable(f1Team.constructorsChampionships()).orElse(0),
                    Optional.ofNullable(f1Team.driversChampionships()).orElse(0)))
        .toList();
  }

  private List<Driver> fillDrivers(final List<F1Driver> f1Drivers) {
    if (f1Drivers.isEmpty()) return Collections.emptyList();
    return f1Drivers.stream()
        .map(
            f1Driver ->
                new Driver(
                    f1Driver.name().concat(SEPARATOR).concat(f1Driver.surname()),
                    f1Driver.nationality(),
                    TimeUtils.parseDate(f1Driver.birthday()),
                    f1Driver.number(),
                    f1Driver.teamId()))
        .toList();
  }
}

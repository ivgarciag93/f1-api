package org.ivione93.services.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.ivione93.dto.f1.Team;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.dto.f1api.TeamsResponse;
import org.ivione93.dto.f1api.seasons.F1Championship;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.dto.f1api.teams.F1Team;
import org.ivione93.dto.f1api.teams.F1TeamsResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class F1Converter {

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
}

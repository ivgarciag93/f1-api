package org.ivione93.dto.f1api.teams;

public record F1Team(
    String teamId,
    String teamName,
    String teamNationality,
    Integer firstAppeareance,
    Integer constructorsChampionships,
    Integer driversChampionships,
    String url) {}

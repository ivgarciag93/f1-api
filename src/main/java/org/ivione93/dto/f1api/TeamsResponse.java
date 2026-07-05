package org.ivione93.dto.f1api;

import org.ivione93.dto.f1.Team;

import java.util.List;

public record TeamsResponse(
    Integer limit, Integer offset, Integer totalResults, List<Team> teams) {}

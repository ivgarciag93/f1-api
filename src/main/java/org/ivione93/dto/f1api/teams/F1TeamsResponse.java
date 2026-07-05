package org.ivione93.dto.f1api.teams;

import java.util.List;

public record F1TeamsResponse(
    Integer limit, Integer offset, Integer total, Integer season, List<F1Team> teams) {}

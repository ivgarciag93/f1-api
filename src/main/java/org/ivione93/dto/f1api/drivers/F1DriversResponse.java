package org.ivione93.dto.f1api.drivers;

import java.util.List;

public record F1DriversResponse(
    Integer limit,
    Integer offset,
    Integer total,
    Integer season,
    String championshipId,
    List<F1Driver> drivers) {}

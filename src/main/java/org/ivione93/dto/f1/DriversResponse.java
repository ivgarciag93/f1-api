package org.ivione93.dto.f1;

import java.util.List;

public record DriversResponse(
    Integer limit, Integer offset, Integer totalResults, List<Driver> drivers) {}

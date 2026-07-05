package org.ivione93.dto.f1api;

import org.ivione93.dto.f1.Driver;

import java.util.List;

public record DriversResponse(
    Integer limit, Integer offset, Integer totalResults, List<Driver> drivers) {}

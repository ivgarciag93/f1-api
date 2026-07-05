package org.ivione93.dto.f1api.drivers;

public record F1Driver(
    String driverId,
    String name,
    String surname,
    String nationality,
    String birthday,
    Integer number,
    String shortName,
    String url,
    String teamId) {}

package org.ivione93.dto.f1;

import java.time.LocalDate;

public record Driver(
    String name, String nationality, LocalDate birthday, Integer number, String teamId) {}

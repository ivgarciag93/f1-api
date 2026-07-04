package org.ivione93.dto.f1api.seasons;

public record F1Championship(
        String championshipId,
        String championshipName,
        String url,
        Integer year) {}

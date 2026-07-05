package org.ivione93.dto.f1;

import java.util.List;

public record SeasonsResponse(
    Integer limit, Integer offset, Integer totalResults, List<Championship> championships) {
  public record Championship(String name, String url, Integer year) {}
}

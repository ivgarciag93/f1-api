package org.ivione93.collaborative;

import org.ivione93.dto.f1api.SeasonsResponse;

import java.util.List;

public class BaseTest {

  public SeasonsResponse createSeasonsResponse() {
    return new SeasonsResponse(
        30,
        0,
        2,
        List.of(
            new SeasonsResponse.Championship(
                "2026 Formula 1 World Championship",
                "https://en.wikipedia.org/wiki/2026_Formula_One_World_Championship",
                2026),
            new SeasonsResponse.Championship(
                "2025 Formula 1 World Championship",
                "https://en.wikipedia.org/wiki/2025_Formula_One_World_Championship",
                2025)));
  }
}

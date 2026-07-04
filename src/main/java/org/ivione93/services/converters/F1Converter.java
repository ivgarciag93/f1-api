package org.ivione93.services.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.dto.f1api.seasons.F1Championship;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class F1Converter {

  public SeasonsResponse fillSeasonsResponse(final F1SeasonsResponse f1SeasonsResponse) {
    return new SeasonsResponse(
        f1SeasonsResponse.limit(),
        f1SeasonsResponse.offset(),
        f1SeasonsResponse.total(),
        fillChampionships(f1SeasonsResponse.championships()));
  }

  private List<SeasonsResponse.Championship> fillChampionships(final List<F1Championship> f1championships) {
    if (f1championships.isEmpty()) return Collections.emptyList();
    return f1championships.stream().map(
        f1Championship -> new SeasonsResponse.Championship(
        f1Championship.championshipName(),
        f1Championship.url(),
        f1Championship.year())).toList();
  }
}

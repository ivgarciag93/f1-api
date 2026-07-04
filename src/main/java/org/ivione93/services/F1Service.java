package org.ivione93.services;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.services.async.F1ApiAsyncCallService;
import org.ivione93.services.converters.F1Converter;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class F1Service {

  @Inject
  F1ApiAsyncCallService f1ApiAsyncCallService;

  @Inject
  F1Converter f1Converter;

  public SeasonsResponse getSeasons() {
    final CompletableFuture<F1SeasonsResponse> futureSeasons = f1ApiAsyncCallService.getSeasons();
    try {
      return f1Converter.fillSeasonsResponse(futureSeasons.join());
    } catch (Exception ex) {
      Log.errorf(ex, "Error while obtaining seasons.");
      throw new RuntimeException(ex);
    }
  }
}

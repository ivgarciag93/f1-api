package org.ivione93.services;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ivione93.dto.f1api.DriversResponse;
import org.ivione93.dto.f1api.PaginationParams;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.dto.f1api.TeamsResponse;
import org.ivione93.dto.f1api.drivers.F1DriversResponse;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.dto.f1api.teams.F1TeamsResponse;
import org.ivione93.services.async.F1ApiAsyncCallService;
import org.ivione93.services.converters.F1Converter;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class F1Service {

  @Inject F1ApiAsyncCallService f1ApiAsyncCallService;

  @Inject F1Converter f1Converter;

  public SeasonsResponse getSeasons(final PaginationParams paginationParams) {
    final CompletableFuture<F1SeasonsResponse> futureSeasons =
        f1ApiAsyncCallService.getSeasons(paginationParams);
    try {
      return f1Converter.fillSeasonsResponse(futureSeasons.join());
    } catch (Exception ex) {
      Log.errorf(ex, "Error while obtaining seasons.");
      throw new RuntimeException(ex);
    }
  }

  public TeamsResponse getCurrentTeams(final PaginationParams paginationParams) {
    final CompletableFuture<F1TeamsResponse> futureTeams =
        f1ApiAsyncCallService.getCurrentTeams(paginationParams);
    try {
      return f1Converter.fillCurrentTeamsResponse(futureTeams.join());
    } catch (Exception ex) {
      Log.errorf(ex, "Error while obtaining current teams.");
      throw new RuntimeException(ex);
    }
  }

  public DriversResponse getCurrentDrivers(final PaginationParams paginationParams) {
    final CompletableFuture<F1DriversResponse> futureDrivers =
        f1ApiAsyncCallService.getCurrentDrivers(paginationParams);
    try {
      return f1Converter.fillCurrentDriversResponse(futureDrivers.join());
    } catch (Exception ex) {
      Log.errorf(ex, "Error while obtaining current drivers.");
      throw new RuntimeException(ex);
    }
  }
}

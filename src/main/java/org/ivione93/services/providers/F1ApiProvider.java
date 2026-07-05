package org.ivione93.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ivione93.dto.f1api.PaginationParams;
import org.ivione93.dto.f1api.drivers.F1DriversResponse;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.dto.f1api.teams.F1TeamsResponse;
import org.ivione93.services.dataservices.F1ApiDataService;

@ApplicationScoped
public class F1ApiProvider {

  @RestClient F1ApiDataService f1ApiDataService;

  public F1SeasonsResponse getSeasons(final PaginationParams paginationParams) {
    return f1ApiDataService.getSeasons(paginationParams);
  }

  public F1TeamsResponse getCurrentTeams(final PaginationParams paginationParams) {
    return f1ApiDataService.getCurrentTeams(paginationParams);
  }

  public F1DriversResponse getCurrentDrivers(final PaginationParams paginationParams) {
    return f1ApiDataService.getCurrentDrivers(paginationParams);
  }
}

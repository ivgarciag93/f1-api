package org.ivione93.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.services.dataservices.F1ApiDataService;

@ApplicationScoped
public class F1ApiProvider {

  @RestClient F1ApiDataService f1ApiDataService;

  public F1SeasonsResponse getSeasons() {
    return f1ApiDataService.getSeasons();
  }

}

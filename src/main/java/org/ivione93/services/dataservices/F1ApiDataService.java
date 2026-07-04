package org.ivione93.services.dataservices;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;

@Path("/api")
@RegisterRestClient(configKey = "f1")
@RegisterForReflection
public interface F1ApiDataService {

  @GET
  @Path("/seasons")
  F1SeasonsResponse getSeasons();

}

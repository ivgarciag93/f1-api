package org.ivione93.services.dataservices;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.ivione93.dto.f1api.PaginationParams;
import org.ivione93.dto.f1api.drivers.F1DriversResponse;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.dto.f1api.teams.F1TeamsResponse;

@Path("/api")
@RegisterRestClient(configKey = "f1")
@RegisterForReflection
public interface F1ApiDataService {

  ////////////////////////
  ///     SEASONS      ///
  ////////////////////////
  @GET
  @Path("/seasons")
  F1SeasonsResponse getSeasons(@BeanParam PaginationParams paginationParams);

  ////////////////////////
  ///      TEAMS       ///
  ////////////////////////
  @GET
  @Path("/current/teams")
  F1TeamsResponse getCurrentTeams(@BeanParam PaginationParams paginationParams);

  ////////////////////////
  ///     DRIVERS      ///
  ////////////////////////
  @GET
  @Path("/current/drivers")
  F1DriversResponse getCurrentDrivers(@BeanParam PaginationParams paginationParams);
}

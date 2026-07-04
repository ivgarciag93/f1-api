package org.ivione93.boundary;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.ivione93.dto.f1api.PaginationParams;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.services.F1Service;

@ApplicationScoped
@Path("/v1/f1-api")
public class F1Api {

  @Inject F1Service f1Service;

  @GET
  @Path("/seasons")
  public SeasonsResponse getSeasons(@BeanParam PaginationParams paginationParams) {
    Log.info("Call to get seasons");
    return f1Service.getSeasons(paginationParams);
  }
}

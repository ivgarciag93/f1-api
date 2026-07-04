package org.ivione93.dto.f1api;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

@RegisterForReflection
public class PaginationParams {

  @QueryParam("limit")
  @DefaultValue("5")
  Integer limit;

  @QueryParam("offset")
  @DefaultValue("0")
  Integer offset;
}

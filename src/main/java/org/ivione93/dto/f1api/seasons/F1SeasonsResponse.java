package org.ivione93.dto.f1api.seasons;

import java.util.List;

public record F1SeasonsResponse(
   Integer limit,
   Integer offset,
   Integer total,
   List<F1Championship> championships
) {}

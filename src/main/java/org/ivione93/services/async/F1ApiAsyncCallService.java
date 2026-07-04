package org.ivione93.services.async;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.services.providers.F1ApiProvider;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class F1ApiAsyncCallService extends AsyncCallService {

  @Inject
  F1ApiProvider f1ApiProvider;

  public CompletableFuture<F1SeasonsResponse> getSeasons() {
    return managedExecutor
        .supplyAsync(() -> f1ApiProvider.getSeasons())
        .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
        .exceptionally(ex -> {
          Log.errorf(ex, "Error obtaining seasons in F1 API");
          throw toCompletionException(ex);
        });
  }

}

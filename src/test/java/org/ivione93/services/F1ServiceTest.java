package org.ivione93.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.dto.f1api.seasons.F1Championship;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.services.async.F1ApiAsyncCallService;
import org.ivione93.services.converters.F1Converter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class F1ServiceTest {

  @InjectMock F1ApiAsyncCallService f1ApiAsyncCallService;

  @InjectMock F1Converter f1Converter;

  @Inject F1Service f1Service;

  @Test
  @DisplayName("getSeasons should return converted response when all is ok")
  void testGetSeasons_Success() {
    F1SeasonsResponse f1Response =
        new F1SeasonsResponse(
            30, 0, 1, List.of(new F1Championship("f1", "Formula 1", "https://example.com", 2024)));
    SeasonsResponse expected =
        new SeasonsResponse(
            30,
            0,
            1,
            List.of(new SeasonsResponse.Championship("Formula 1", "https://example.com", 2024)));

    when(f1ApiAsyncCallService.getSeasons())
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillSeasonsResponse(f1Response)).thenReturn(expected);

    SeasonsResponse result = f1Service.getSeasons();

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(1, result.totalResults());
    assertEquals(1, result.championships().size());
    assertEquals("Formula 1", result.championships().getFirst().name());
    assertEquals(2024, result.championships().getFirst().year());

    verify(f1ApiAsyncCallService).getSeasons();
    verify(f1Converter).fillSeasonsResponse(f1Response);
  }

  @Test
  @DisplayName("getSeasons should return empty championships when empty response")
  void testGetSeasons_EmptyResponse_Success() {
    F1SeasonsResponse f1Response = new F1SeasonsResponse(0, 0, 0, Collections.emptyList());
    SeasonsResponse expected = new SeasonsResponse(0, 0, 0, Collections.emptyList());

    when(f1ApiAsyncCallService.getSeasons())
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillSeasonsResponse(f1Response)).thenReturn(expected);

    SeasonsResponse result = f1Service.getSeasons();

    assertNotNull(result);
    assertEquals(0, result.totalResults());
    assertTrue(result.championships().isEmpty());
  }

  @Test
  @DisplayName("getSeasons should throw RuntimeException when service fails")
  void testGetSeasons_ServiceFails_ThrowRuntimeException() {
    CompletableFuture<F1SeasonsResponse> failedFuture = new CompletableFuture<>();
    failedFuture.completeExceptionally(new RuntimeException("API error"));

    when(f1ApiAsyncCallService.getSeasons()).thenReturn(failedFuture);

    RuntimeException ex = assertThrows(RuntimeException.class, f1Service::getSeasons);
    assertInstanceOf(java.util.concurrent.CompletionException.class, ex.getCause());
    assertEquals("API error", ex.getCause().getCause().getMessage());
    verify(f1Converter, never()).fillSeasonsResponse(any());
  }

  @Test
  @DisplayName("getSeasons should throw RuntimeException when converter fails")
  void testGetSeasons_ConverterFails_ThrowRuntimeException() {
    F1SeasonsResponse f1Response = new F1SeasonsResponse(30, 0, 1, Collections.emptyList());

    when(f1ApiAsyncCallService.getSeasons())
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillSeasonsResponse(f1Response))
        .thenThrow(new RuntimeException("Conversion error"));

    RuntimeException ex = assertThrows(RuntimeException.class, f1Service::getSeasons);
    assertInstanceOf(RuntimeException.class, ex.getCause());
    assertEquals("Conversion error", ex.getCause().getMessage());
  }
}

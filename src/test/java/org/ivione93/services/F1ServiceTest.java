package org.ivione93.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.ivione93.dto.f1api.PaginationParams;
import org.ivione93.dto.f1.Driver;
import org.ivione93.dto.f1.Team;
import org.ivione93.dto.f1api.DriversResponse;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.dto.f1api.TeamsResponse;
import org.ivione93.dto.f1api.drivers.F1Driver;
import org.ivione93.dto.f1api.drivers.F1DriversResponse;
import org.ivione93.dto.f1api.seasons.F1Championship;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.ivione93.dto.f1api.teams.F1Team;
import org.ivione93.dto.f1api.teams.F1TeamsResponse;
import org.ivione93.services.async.F1ApiAsyncCallService;
import org.ivione93.services.converters.F1Converter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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

  // SEASONS
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

    when(f1ApiAsyncCallService.getSeasons(any(PaginationParams.class)))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillSeasonsResponse(f1Response)).thenReturn(expected);

    SeasonsResponse result = f1Service.getSeasons(new PaginationParams());

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(1, result.totalResults());
    assertEquals(1, result.championships().size());
    assertEquals("Formula 1", result.championships().getFirst().name());
    assertEquals(2024, result.championships().getFirst().year());

    verify(f1ApiAsyncCallService).getSeasons(any(PaginationParams.class));
    verify(f1Converter).fillSeasonsResponse(f1Response);
  }

  @Test
  @DisplayName("getSeasons should return empty championships when empty response")
  void testGetSeasons_EmptyResponse_Success() {
    F1SeasonsResponse f1Response = new F1SeasonsResponse(0, 0, 0, Collections.emptyList());
    SeasonsResponse expected = new SeasonsResponse(0, 0, 0, Collections.emptyList());

    when(f1ApiAsyncCallService.getSeasons(any(PaginationParams.class)))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillSeasonsResponse(f1Response)).thenReturn(expected);

    SeasonsResponse result = f1Service.getSeasons(new PaginationParams());

    assertNotNull(result);
    assertEquals(0, result.totalResults());
    assertTrue(result.championships().isEmpty());
  }

  @Test
  @DisplayName("getSeasons should throw RuntimeException when service fails")
  void testGetSeasons_ServiceFails_ThrowRuntimeException() {
    CompletableFuture<F1SeasonsResponse> failedFuture = new CompletableFuture<>();
    failedFuture.completeExceptionally(new RuntimeException("API error"));

    when(f1ApiAsyncCallService.getSeasons(any(PaginationParams.class))).thenReturn(failedFuture);

    assertThrows(RuntimeException.class, () -> f1Service.getSeasons(new PaginationParams()));
    verify(f1Converter, never()).fillSeasonsResponse(any());
  }

  @Test
  @DisplayName("getSeasons should throw RuntimeException when converter fails")
  void testGetSeasons_ConverterFails_ThrowRuntimeException() {
    F1SeasonsResponse f1Response = new F1SeasonsResponse(30, 0, 1, Collections.emptyList());

    when(f1ApiAsyncCallService.getSeasons(any(PaginationParams.class)))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillSeasonsResponse(f1Response))
        .thenThrow(new RuntimeException("Conversion error"));

    assertThrows(RuntimeException.class, () -> f1Service.getSeasons(new PaginationParams()));
  }

  @Test
  @DisplayName("getSeasons should pass pagination params to async service")
  void testGetSeasons_PaginationParamsPassed() {
    F1SeasonsResponse f1Response = new F1SeasonsResponse(10, 5, 1, Collections.emptyList());
    SeasonsResponse expected = new SeasonsResponse(10, 5, 1, Collections.emptyList());

    PaginationParams customParams = new PaginationParams();

    when(f1ApiAsyncCallService.getSeasons(customParams))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillSeasonsResponse(f1Response)).thenReturn(expected);

    SeasonsResponse result = f1Service.getSeasons(customParams);

    assertNotNull(result);
    assertEquals(10, result.limit());
    assertEquals(5, result.offset());
    verify(f1ApiAsyncCallService).getSeasons(customParams);
  }

  // CURRENT TEAMS
  @Test
  @DisplayName("getCurrentTeams should return converted response when all is ok")
  void testGetCurrentTeams_Success() {
    F1TeamsResponse f1Response =
        new F1TeamsResponse(
            30,
            0,
            1,
            2024,
            List.of(
                new F1Team(
                    "red_bull", "Red Bull Racing", "Austrian", 2005, 6, 7, "https://example.com")));
    TeamsResponse expected =
        new TeamsResponse(30, 0, 1, List.of(new Team("Red Bull Racing", "Austrian", 2024, 6, 7)));

    when(f1ApiAsyncCallService.getCurrentTeams(any(PaginationParams.class)))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillCurrentTeamsResponse(f1Response)).thenReturn(expected);

    TeamsResponse result = f1Service.getCurrentTeams(new PaginationParams());

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(1, result.totalResults());
    assertEquals(1, result.teams().size());
    assertEquals("Red Bull Racing", result.teams().getFirst().name());
    assertEquals("Austrian", result.teams().getFirst().nationality());

    verify(f1ApiAsyncCallService).getCurrentTeams(any(PaginationParams.class));
    verify(f1Converter).fillCurrentTeamsResponse(f1Response);
  }

  @Test
  @DisplayName("getCurrentTeams should return empty teams when empty response")
  void testGetCurrentTeams_EmptyResponse_Success() {
    F1TeamsResponse f1Response = new F1TeamsResponse(0, 0, 0, 2024, Collections.emptyList());
    TeamsResponse expected = new TeamsResponse(0, 0, 0, Collections.emptyList());

    when(f1ApiAsyncCallService.getCurrentTeams(any(PaginationParams.class)))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillCurrentTeamsResponse(f1Response)).thenReturn(expected);

    TeamsResponse result = f1Service.getCurrentTeams(new PaginationParams());

    assertNotNull(result);
    assertEquals(0, result.totalResults());
    assertTrue(result.teams().isEmpty());
  }

  @Test
  @DisplayName("getCurrentTeams should throw RuntimeException when service fails")
  void testGetCurrentTeams_ServiceFails_ThrowRuntimeException() {
    CompletableFuture<F1TeamsResponse> failedFuture = new CompletableFuture<>();
    failedFuture.completeExceptionally(new RuntimeException("API error"));

    when(f1ApiAsyncCallService.getCurrentTeams(any(PaginationParams.class)))
        .thenReturn(failedFuture);

    assertThrows(RuntimeException.class, () -> f1Service.getCurrentTeams(new PaginationParams()));
    verify(f1Converter, never()).fillCurrentTeamsResponse(any());
  }

  @Test
  @DisplayName("getCurrentTeams should throw RuntimeException when converter fails")
  void testGetCurrentTeams_ConverterFails_ThrowRuntimeException() {
    F1TeamsResponse f1Response = new F1TeamsResponse(30, 0, 1, 2024, Collections.emptyList());

    when(f1ApiAsyncCallService.getCurrentTeams(any(PaginationParams.class)))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillCurrentTeamsResponse(f1Response))
        .thenThrow(new RuntimeException("Conversion error"));

    assertThrows(RuntimeException.class, () -> f1Service.getCurrentTeams(new PaginationParams()));
  }

  @Test
  @DisplayName("getCurrentTeams should pass pagination params to async service")
  void testGetCurrentTeams_PaginationParamsPassed() {
    F1TeamsResponse f1Response = new F1TeamsResponse(10, 5, 1, 2024, Collections.emptyList());
    TeamsResponse expected = new TeamsResponse(10, 5, 1, Collections.emptyList());

    PaginationParams customParams = new PaginationParams();

    when(f1ApiAsyncCallService.getCurrentTeams(customParams))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillCurrentTeamsResponse(f1Response)).thenReturn(expected);

    TeamsResponse result = f1Service.getCurrentTeams(customParams);

    assertNotNull(result);
    assertEquals(10, result.limit());
    assertEquals(5, result.offset());
    verify(f1ApiAsyncCallService).getCurrentTeams(customParams);
  }

  // CURRENT DRIVERS
  @Test
  @DisplayName("getCurrentDrivers should return converted response when all is ok")
  void testGetCurrentDrivers_Success() {
    F1DriversResponse f1Response =
        new F1DriversResponse(
            30,
            0,
            1,
            2024,
            "f1",
            List.of(
                new F1Driver(
                    "ver",
                    "Max",
                    "Verstappen",
                    "Netherlands",
                    "1997-09-30",
                    1,
                    "VER",
                    "https://example.com",
                    "red_bull")));
    DriversResponse expected =
        new DriversResponse(
            30,
            0,
            1,
            List.of(
                new Driver(
                    "Max Verstappen", "Netherlands", LocalDate.of(1997, 9, 30), 1, "red_bull")));

    when(f1ApiAsyncCallService.getCurrentDrivers(any(PaginationParams.class)))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillCurrentDriversResponse(f1Response)).thenReturn(expected);

    DriversResponse result = f1Service.getCurrentDrivers(new PaginationParams());

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(1, result.totalResults());
    assertEquals(1, result.drivers().size());
    assertEquals("Max Verstappen", result.drivers().getFirst().name());
    assertEquals("Netherlands", result.drivers().getFirst().nationality());

    verify(f1ApiAsyncCallService).getCurrentDrivers(any(PaginationParams.class));
    verify(f1Converter).fillCurrentDriversResponse(f1Response);
  }

  @Test
  @DisplayName("getCurrentDrivers should return empty drivers when empty response")
  void testGetCurrentDrivers_EmptyResponse_Success() {
    F1DriversResponse f1Response =
        new F1DriversResponse(0, 0, 0, 2024, "f1", Collections.emptyList());
    DriversResponse expected = new DriversResponse(0, 0, 0, Collections.emptyList());

    when(f1ApiAsyncCallService.getCurrentDrivers(any(PaginationParams.class)))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillCurrentDriversResponse(f1Response)).thenReturn(expected);

    DriversResponse result = f1Service.getCurrentDrivers(new PaginationParams());

    assertNotNull(result);
    assertEquals(0, result.totalResults());
    assertTrue(result.drivers().isEmpty());
  }

  @Test
  @DisplayName("getCurrentDrivers should throw RuntimeException when service fails")
  void testGetCurrentDrivers_ServiceFails_ThrowRuntimeException() {
    CompletableFuture<F1DriversResponse> failedFuture = new CompletableFuture<>();
    failedFuture.completeExceptionally(new RuntimeException("API error"));

    when(f1ApiAsyncCallService.getCurrentDrivers(any(PaginationParams.class)))
        .thenReturn(failedFuture);

    assertThrows(RuntimeException.class, () -> f1Service.getCurrentDrivers(new PaginationParams()));
    verify(f1Converter, never()).fillCurrentDriversResponse(any());
  }

  @Test
  @DisplayName("getCurrentDrivers should throw RuntimeException when converter fails")
  void testGetCurrentDrivers_ConverterFails_ThrowRuntimeException() {
    F1DriversResponse f1Response =
        new F1DriversResponse(30, 0, 1, 2024, "f1", Collections.emptyList());

    when(f1ApiAsyncCallService.getCurrentDrivers(any(PaginationParams.class)))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillCurrentDriversResponse(f1Response))
        .thenThrow(new RuntimeException("Conversion error"));

    assertThrows(RuntimeException.class, () -> f1Service.getCurrentDrivers(new PaginationParams()));
  }

  @Test
  @DisplayName("getCurrentDrivers should pass pagination params to async service")
  void testGetCurrentDrivers_PaginationParamsPassed() {
    F1DriversResponse f1Response =
        new F1DriversResponse(10, 5, 1, 2024, "f1", Collections.emptyList());
    DriversResponse expected = new DriversResponse(10, 5, 1, Collections.emptyList());

    PaginationParams customParams = new PaginationParams();

    when(f1ApiAsyncCallService.getCurrentDrivers(customParams))
        .thenReturn(CompletableFuture.completedFuture(f1Response));
    when(f1Converter.fillCurrentDriversResponse(f1Response)).thenReturn(expected);

    DriversResponse result = f1Service.getCurrentDrivers(customParams);

    assertNotNull(result);
    assertEquals(10, result.limit());
    assertEquals(5, result.offset());
    verify(f1ApiAsyncCallService).getCurrentDrivers(customParams);
  }
}

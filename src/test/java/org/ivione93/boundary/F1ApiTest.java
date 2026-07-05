package org.ivione93.boundary;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.ivione93.collaborative.BaseTest;
import org.ivione93.dto.f1.Driver;
import org.ivione93.dto.f1api.DriversResponse;
import org.ivione93.dto.f1api.PaginationParams;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.dto.f1api.TeamsResponse;
import org.ivione93.services.F1Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class F1ApiTest extends BaseTest {

  private static final String BASE_F1_URI = "/v1/f1-api";
  private static final String GET_SEASONS_URI = BASE_F1_URI + "/seasons";
  private static final String GET_TEAMS_URI = BASE_F1_URI + "/teams/current";
  private static final String GET_DRIVERS_URI = BASE_F1_URI + "/drivers/current";

  @InjectMock F1Service f1Service;

  // Endpoint 1: Obtener temporadas
  @Test
  @DisplayName("getSeasons should returns 200 OK when all is ok")
  void testGetSeasons_Success() {
    SeasonsResponse response = createSeasonsResponse();
    when(f1Service.getSeasons(any(PaginationParams.class))).thenReturn(response);

    SeasonsResponse result =
        given()
            .when()
            .get(GET_SEASONS_URI)
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(SeasonsResponse.class);

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(2, result.totalResults());
  }

  @Test
  @DisplayName("getSeasons should returns 200 OK with empty championships when empty response")
  void testGetSeasons_EmptyResponse_Success() {
    SeasonsResponse response = new SeasonsResponse(0, 0, 0, List.of());
    when(f1Service.getSeasons(any(PaginationParams.class))).thenReturn(response);

    given()
        .when()
        .get(GET_SEASONS_URI)
        .then()
        .statusCode(200)
        .body("limit", equalTo(0))
        .body("championships.size()", equalTo(0));
  }

  @Test
  @DisplayName("getSeasons should returns 500 when external service failure")
  void testGetSeasons_ServiceFailure_Return500() {
    when(f1Service.getSeasons(any(PaginationParams.class)))
        .thenThrow(new WebApplicationException("External service failure"));

    given()
        .when()
        .get(GET_SEASONS_URI)
        .then()
        .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
  }

  @Test
  @DisplayName("getSeasons should returns 200 OK with custom pagination params")
  void testGetSeasons_CustomPagination_Success() {
    SeasonsResponse response = new SeasonsResponse(10, 5, 1, List.of());
    when(f1Service.getSeasons(any(PaginationParams.class))).thenReturn(response);

    SeasonsResponse result =
        given()
            .queryParam("limit", 10)
            .queryParam("offset", 5)
            .when()
            .get(GET_SEASONS_URI)
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(SeasonsResponse.class);

    assertNotNull(result);
    assertEquals(10, result.limit());
    assertEquals(5, result.offset());
  }

  // Endpoint 2: Obtener equipos actuales
  @Test
  @DisplayName("getCurrentTeams should returns 200 OK when all is ok")
  void testGetCurrentTeams_Success() {
    TeamsResponse response = createTeamsResponse();
    when(f1Service.getCurrentTeams(any(PaginationParams.class))).thenReturn(response);

    TeamsResponse result =
        given()
            .when()
            .get(GET_TEAMS_URI)
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(TeamsResponse.class);

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(2, result.totalResults());
  }

  @Test
  @DisplayName("getCurrentTeams should returns 200 OK with empty teams when empty response")
  void testGetCurrentTeams_EmptyResponse_Success() {
    TeamsResponse response = new TeamsResponse(0, 0, 0, List.of());
    when(f1Service.getCurrentTeams(any(PaginationParams.class))).thenReturn(response);

    given()
        .when()
        .get(GET_TEAMS_URI)
        .then()
        .statusCode(200)
        .body("limit", equalTo(0))
        .body("teams.size()", equalTo(0));
  }

  @Test
  @DisplayName("getCurrentTeams should returns 500 when external service failure")
  void testGetCurrentTeams_ServiceFailure_Return500() {
    when(f1Service.getCurrentTeams(any(PaginationParams.class)))
        .thenThrow(new WebApplicationException("External service failure"));

    given()
        .when()
        .get(GET_TEAMS_URI)
        .then()
        .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
  }

  @Test
  @DisplayName("getCurrentTeams should returns 200 OK with custom pagination params")
  void testGetCurrentTeams_CustomPagination_Success() {
    TeamsResponse response = new TeamsResponse(10, 5, 1, List.of());
    when(f1Service.getCurrentTeams(any(PaginationParams.class))).thenReturn(response);

    TeamsResponse result =
        given()
            .queryParam("limit", 10)
            .queryParam("offset", 5)
            .when()
            .get(GET_TEAMS_URI)
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(TeamsResponse.class);

    assertNotNull(result);
    assertEquals(10, result.limit());
    assertEquals(5, result.offset());
  }

  // Endpoint 3: Obtener pilotos actuales
  @Test
  @DisplayName("getCurrentDrivers should returns 200 OK when all is ok")
  void testGetCurrentDrivers_Success() {
    DriversResponse response = createDriversResponse();
    when(f1Service.getCurrentDrivers(any(PaginationParams.class))).thenReturn(response);

    DriversResponse result =
        given()
            .when()
            .get(GET_DRIVERS_URI)
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(DriversResponse.class);

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(2, result.totalResults());
  }

  @Test
  @DisplayName("getCurrentDrivers should returns 200 OK with empty drivers when empty response")
  void testGetCurrentDrivers_EmptyResponse_Success() {
    DriversResponse response = new DriversResponse(0, 0, 0, List.of());
    when(f1Service.getCurrentDrivers(any(PaginationParams.class))).thenReturn(response);

    given()
        .when()
        .get(GET_DRIVERS_URI)
        .then()
        .statusCode(200)
        .body("limit", equalTo(0))
        .body("drivers.size()", equalTo(0));
  }

  @Test
  @DisplayName("getCurrentDrivers should returns 500 when external service failure")
  void testGetCurrentDrivers_ServiceFailure_Return500() {
    when(f1Service.getCurrentDrivers(any(PaginationParams.class)))
        .thenThrow(new WebApplicationException("External service failure"));

    given()
        .when()
        .get(GET_DRIVERS_URI)
        .then()
        .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
  }

  @Test
  @DisplayName("getCurrentDrivers should returns 200 OK with custom pagination params")
  void testGetCurrentDrivers_CustomPagination_Success() {
    DriversResponse response = new DriversResponse(10, 5, 1, List.of());
    when(f1Service.getCurrentDrivers(any(PaginationParams.class))).thenReturn(response);

    DriversResponse result =
        given()
            .queryParam("limit", 10)
            .queryParam("offset", 5)
            .when()
            .get(GET_DRIVERS_URI)
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(DriversResponse.class);

    assertNotNull(result);
    assertEquals(10, result.limit());
    assertEquals(5, result.offset());
  }
}

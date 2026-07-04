package org.ivione93.boundary;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.ivione93.collaborative.BaseTest;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.services.F1Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class F1ApiTest extends BaseTest {

  private static final String BASE_F1_URI = "/v1/f1-api";
  private static final String GET_SEASONS_URI = BASE_F1_URI + "/seasons";

  @InjectMock F1Service f1Service;

  @Test
  @DisplayName("getSeasons should returns 200 OK when all is ok")
  void testGetSeasons_Success() {
    SeasonsResponse response = createSeasonsResponse();
    when(f1Service.getSeasons()).thenReturn(response);

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
    when(f1Service.getSeasons()).thenReturn(response);

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
    when(f1Service.getSeasons()).thenThrow(new WebApplicationException("External service failure"));

    given()
        .when()
        .get(GET_SEASONS_URI)
        .then()
        .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
  }
}

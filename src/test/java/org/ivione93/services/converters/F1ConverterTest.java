package org.ivione93.services.converters;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.ivione93.dto.f1api.SeasonsResponse;
import org.ivione93.dto.f1api.seasons.F1Championship;
import org.ivione93.dto.f1api.seasons.F1SeasonsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class F1ConverterTest {

  @Inject F1Converter f1Converter;

  @Test
  @DisplayName(
      "fillSeasonsResponse should convert F1SeasonsResponse to SeasonsResponse when all is ok")
  void testFillSeasonsResponse_Success() {
    F1SeasonsResponse f1Response =
        new F1SeasonsResponse(
            30, 0, 1, List.of(new F1Championship("f1", "Formula 1", "https://example.com", 2024)));

    SeasonsResponse result = f1Converter.fillSeasonsResponse(f1Response);

    assertNotNull(result);
    assertEquals(30, result.limit());
    assertEquals(0, result.offset());
    assertEquals(1, result.totalResults());
    assertEquals(1, result.championships().size());
    assertEquals("Formula 1", result.championships().getFirst().name());
    assertEquals("https://example.com", result.championships().getFirst().url());
    assertEquals(2024, result.championships().getFirst().year());
  }

  @Test
  @DisplayName("fillSeasonsResponse should return empty championships when empty list")
  void testFillSeasonsResponse_EmptyChampionships() {
    F1SeasonsResponse f1Response = new F1SeasonsResponse(0, 0, 0, List.of());

    SeasonsResponse result = f1Converter.fillSeasonsResponse(f1Response);

    assertNotNull(result);
    assertEquals(0, result.limit());
    assertEquals(0, result.offset());
    assertEquals(0, result.totalResults());
    assertTrue(result.championships().isEmpty());
  }

  @Test
  @DisplayName("fillSeasonsResponse should handle null values when fields are null")
  void testFillSeasonsResponse_NullFields() {
    F1SeasonsResponse f1Response = new F1SeasonsResponse(null, null, null, List.of());

    SeasonsResponse result = f1Converter.fillSeasonsResponse(f1Response);

    assertNotNull(result);
    assertNull(result.limit());
    assertNull(result.offset());
    assertNull(result.totalResults());
    assertTrue(result.championships().isEmpty());
  }
}

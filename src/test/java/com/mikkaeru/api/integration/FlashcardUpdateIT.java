package com.mikkaeru.api.integration;

import com.mikkaeru.api.helper.IntegrationHelper;
import com.mikkaeru.api.repository.FlashcardRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class FlashcardUpdateIT extends IntegrationHelper {

    @Autowired
    private FlashcardRepository flashcardRepository;

    private static final String ENDPOINT = "/v1/flashcards/{externalId}";
    private static final String EXTERNAL_ID = "7fe7db1d-2031-4778-80f5-f8c45f5fd86d";

    @Test
    @DisplayName("Caso o payload seja valido retorna status code 204")
    void GIVEN_ValidPayload_MUST_ReturnNoContent() throws Exception {
        // GIVEN
        var front = faker.book().title();
        var back = faker.book().author();

        JSONObject payload = new JSONObject();

        payload.put("back", back);
        payload.put("front", front);

        mockMvc.perform(put(ENDPOINT, EXTERNAL_ID)
                .contentType(APPLICATION_JSON)
                .content(payload.toString()))
                .andExpect(status().isNoContent());

        var flashcardUpdated = flashcardRepository.findByExternalId(UUID.fromString(EXTERNAL_ID));

        assertTrue(flashcardUpdated.isPresent());
        assertEquals(back, flashcardUpdated.get().getBack());
        assertEquals(front, flashcardUpdated.get().getFront());
        assertEquals(EXTERNAL_ID, flashcardUpdated.get().getExternalId().toString());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    @DisplayName("Quando chamado deve retornar status code 400")
    void GIVEN_InvalidPayload_MUST_ReturnBadRequest(String externalId, String front, String back) throws Exception {
        // Given
        JSONObject payload = new JSONObject();
        payload.put("externalId", externalId);
        payload.put("front", front);
        payload.put("back", back);

        // When
        mockMvc.perform(put(ENDPOINT, EXTERNAL_ID)
                .contentType(APPLICATION_JSON)
                .content(payload.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    private static Stream<Arguments> provideInvalidData() {
        var validExternalId = EXTERNAL_ID;
        var validFront = faker.pokemon().name();
        var validBack = faker.pokemon().location();

        return Stream.of(
                arguments(validExternalId, null, validBack),
                arguments(validExternalId, "  ", validBack),
                arguments(validExternalId, validFront, null),
                arguments(validExternalId, validFront, "  ")
        );
    }
}

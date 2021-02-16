package com.mikkaeru.api.integration;

import com.mikkaeru.api.domain.model.enumeration.Box;
import com.mikkaeru.api.helper.IntegrationHelper;
import com.mikkaeru.api.repository.FlashcardRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.OffsetDateTime;
import java.util.UUID;

public class FlashcardCreateIT extends IntegrationHelper {

    private static final String ENDPOINT = "/v1/flashcards";

    @Autowired
    FlashcardRepository flashcardRepository;

    @Test
    @DisplayName("Caso o payload seja valido retorna status code 201")
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        // Given
        JSONObject payload = new JSONObject();
        payload.put("review", false);
        payload.put("box", Box.BOX_TWO);
        payload.put("front", faker.book().title());
        payload.put("back", faker.book().author());
        payload.put("externalId", UUID.randomUUID());
        payload.put("createdAt", OffsetDateTime.now());

        // When
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Then

    }
}

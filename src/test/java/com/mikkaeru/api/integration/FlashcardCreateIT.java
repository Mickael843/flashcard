package com.mikkaeru.api.integration;

import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import com.mikkaeru.api.helper.IntegrationHelper;
import com.mikkaeru.api.repository.FlashcardRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.mikkaeru.api.domain.model.enumeration.Box.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(SpringExtension.class)
public class FlashcardCreateIT extends IntegrationHelper {

    @Autowired
    FlashcardRepository flashcardRepository;

    private static final String ENDPOINT = "/v1/flashcards";
    private static final UUID EXTERNAL_ID = UUID.randomUUID();
    private static final String LOCATION = "http://localhost/v1/flashcards/" + EXTERNAL_ID;

    @Test
    @DisplayName("Caso o payload seja valido retorna status code 201")
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        // Given
        var front = faker.book().title();
        var back = faker.book().author();

        JSONObject payload = new JSONObject();

        payload.put("back", back);
        payload.put("front", front);
        payload.put("externalId", EXTERNAL_ID);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.header().string("location", LOCATION));

        // Then
        Optional<Flashcard> flashcardOptional = flashcardRepository.findByExternalId(EXTERNAL_ID);

        Assertions.assertTrue(flashcardOptional.isPresent());

        Flashcard flashcard = flashcardOptional.get();

        assertEquals(back, flashcard.getBack());
        assertEquals(ONE, flashcard.getBox());
        assertEquals(front, flashcard.getFront());
        assertEquals(false, flashcard.getReview());
        assertEquals(EXTERNAL_ID, flashcard.getExternalId());

        assertNotNull(flashcard.getCreatedAt());
        assertNotNull(flashcard.getNextRevision());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    @DisplayName("Quando chamado deve retornar status 400")
    void GIVEN_InvalidData_MUST_ReturnBadRequest(String front, String back, UUID externalId) throws Exception {

        JSONObject payload = new JSONObject();

        payload.put("back", back);
        payload.put("front", front);
        payload.put("externalId", externalId);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private static Stream<Arguments> provideInvalidData() {
        String validFront = faker.book().title();
        String validBack = faker.book().author();
        String validExternalId = UUID.randomUUID().toString();

        String duplicatedFront = "sentença número 0";
        String duplicatedExternalId = "7fe7db1d-2031-4778-80f5-f8c45f5fd86d";

        return Stream.of(
                // Cenário do campo FRONT
                arguments("  ", validBack, validExternalId), // 1
                arguments(null, validBack, validExternalId), // 2
                arguments(duplicatedFront, validBack, validExternalId), // 3
                // Cenário do campo BACK
                arguments(validFront, "  ", validExternalId), // 4
                arguments(validFront, null, validExternalId), // 5
                // Cenário do campo EXTERNAL_ID
                arguments(validFront, validBack, null), // 6
                arguments(validFront, validBack, duplicatedExternalId) // 7
        );
    }
}

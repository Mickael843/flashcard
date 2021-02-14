package com.mikkaeru.api.repository;

import com.mikkaeru.api.domain.model.enumeration.Box;
import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import com.mikkaeru.api.helper.DataSourceHelper;
import com.mikkaeru.api.helper.TestHelper;
import com.mikkaeru.api.repository.impl.FlashcardRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

public class FlashcardRepositoryTest extends TestHelper {

    FlashcardRepository flashcardRepository;

    @BeforeEach
    void beforeEach() {
        flashcardRepository = new FlashcardRepositoryImpl(new DataSourceHelper());
    }

    @Test
    void GIVEN_ValidFlashcard_MUST_CreateFlashcard() {
        // Given
        Flashcard flashcard = new Flashcard();

        flashcard.setReview(false);
        flashcard.setBox(Box.BOX_ONE);
        flashcard.setFront(faker.book().title());
        flashcard.setBack(faker.book().author());
        flashcard.setExternalId(UUID.randomUUID());
        flashcard.setCreatedAt(OffsetDateTime.now());

        // When
        flashcardRepository.create(flashcard);

        // Then
    }
}

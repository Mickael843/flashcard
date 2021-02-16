package com.mikkaeru.api.domain.service;

import com.mikkaeru.api.domain.model.flashcard.Flashcard;

import java.util.UUID;

public interface FlashcardService {

    Flashcard create(Flashcard flashcard);

    void update(Flashcard flashcard);

    void delete(UUID externalId);
}

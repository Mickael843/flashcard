package com.mikkaeru.api.repository;

import com.mikkaeru.api.domain.model.flashcard.Flashcard;

import java.util.Optional;
import java.util.UUID;

public interface FlashcardRepository {

    String ID_FIELD = "id";
    String BOX_FIELD = "flashcard_box";
    String BACK_FIELD = "back";
    String FRONT_FIELD = "front";
    String STATUS_FIELD = "status";
    String REVIEW_FIELD = "review";
    String CREATED_AT_FIELD = "created_at";
    String UPDATED_AT_FIELD = "updated_at";
    String EXTERNAL_ID_FIELD = "external_id";
    String LAST_REVISION_FIELD = "last_revision";
    String NEXT_REVISION_FIELD = "next_revision";

    void create(Flashcard flashcard);

    void update(Flashcard flashcard);

    Optional<Flashcard> findByExternalId(UUID externalId);
}

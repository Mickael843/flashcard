package com.mikkaeru.api.repository;

import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    Optional<Flashcard> findByExternalId(UUID externalId);
}

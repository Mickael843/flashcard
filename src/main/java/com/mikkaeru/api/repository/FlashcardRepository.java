package com.mikkaeru.api.repository;

import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

}

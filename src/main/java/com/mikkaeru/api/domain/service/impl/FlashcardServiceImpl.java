package com.mikkaeru.api.domain.service.impl;

import com.mikkaeru.api.domain.model.enumeration.Box;
import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import com.mikkaeru.api.domain.service.FlashcardService;
import com.mikkaeru.api.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Override
    public Flashcard create(Flashcard flashcard) {

        flashcard.setReview(false);
        flashcard.setBox(Box.BOX_ONE);
        flashcard.setCreatedAt(OffsetDateTime.now());

        return flashcardRepository.save(flashcard);
    }
}

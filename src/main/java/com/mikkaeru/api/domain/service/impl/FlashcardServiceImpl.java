package com.mikkaeru.api.domain.service.impl;

import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import com.mikkaeru.api.domain.service.FlashcardService;
import com.mikkaeru.api.repository.FlashcardRepository;
import org.springframework.stereotype.Service;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    private final FlashcardRepository repository;

    FlashcardServiceImpl(FlashcardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flashcard create(Flashcard flashcard) {

        repository.create(flashcard);

        return null;
    }
}

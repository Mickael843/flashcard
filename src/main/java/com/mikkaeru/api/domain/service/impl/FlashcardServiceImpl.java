package com.mikkaeru.api.domain.service.impl;

import com.mikkaeru.api.domain.model.enumeration.Box;
import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import com.mikkaeru.api.domain.service.FlashcardService;
import com.mikkaeru.api.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    private static final String NOT_BE_NULL = "Não pode ser nulo!";
    private static final String INVALID_FIELDS = "Campos inválidos!";
    private static final String INVALID_EXTERNAL_ID = "O id externo é inválido!";
    private static final String NOT_FOUND = "Flashcard não encontrado!";

    @Override
    public Flashcard create(Flashcard flashcard) {

        flashcard.setReview(false);
        flashcard.setBox(Box.BOX_ONE);
        flashcard.setCreatedAt(OffsetDateTime.now());
        flashcard.setNextRevision(OffsetDateTime.now());

        return flashcardRepository.save(flashcard);
    }

    @Override
    public void update(Flashcard flashcard) {

        Optional<Flashcard> flashcardOptional = flashcardRepository.findByExternalId(flashcard.getExternalId());

        if (flashcardOptional.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        if (flashcard.getFront() == null) {
            flashcard.setFront(flashcardOptional.get().getFront());
        }

        if (flashcard.getBack() == null) {
            flashcard.setBack(flashcardOptional.get().getBack());
        }

        flashcard.setUpdatedAt(OffsetDateTime.now());
        flashcard.setId(flashcardOptional.get().getId());
        flashcard.setBox(flashcardOptional.get().getBox());
        flashcard.setReview(flashcardOptional.get().getReview());
        flashcard.setCreatedAt(flashcardOptional.get().getCreatedAt());
        flashcard.setNextRevision(flashcardOptional.get().getNextRevision());

        flashcardRepository.save(flashcard);
    }

    @Override
    public void delete(UUID externalId) {

        Optional<Flashcard> flashcard = flashcardRepository.findByExternalId(externalId);

        if (flashcard.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        flashcardRepository.delete(flashcard.get());
    }
}

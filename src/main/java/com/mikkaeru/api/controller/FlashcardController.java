package com.mikkaeru.api.controller;

import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import com.mikkaeru.api.domain.service.FlashcardService;
import com.mikkaeru.api.dto.FlashcardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    @PostMapping("/flashcards")
    public ResponseEntity<?> create(@RequestBody @Valid FlashcardRequest request) {

        Flashcard flashcardSaved = flashcardService.create(request.toModel());

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(flashcardSaved.getExternalId())
                .toUri()
        ).build();
    }

    @PutMapping("/flashcards/{externalId}")
    public ResponseEntity<?> update(@RequestBody @Valid FlashcardRequest request, @PathVariable @NotNull UUID externalId) {
        flashcardService.update(request.toModel());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/flashcards/{externalId}")
    public ResponseEntity<?> delete(@PathVariable @NotNull UUID externalId) {
        flashcardService.delete(externalId);
        return ResponseEntity.noContent().build();
    }
}

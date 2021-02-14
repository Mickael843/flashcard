package com.mikkaeru.api.controller;

import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import com.mikkaeru.api.domain.service.FlashcardService;
import com.mikkaeru.api.dto.FlashcardRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class FlashcardController {

    private FlashcardService flashcardService;

    @PostMapping
    @RequestMapping("/flashcards")
    public ResponseEntity<?> create(@RequestBody @Valid FlashcardRequest request) {

        Flashcard flashcardSaved = flashcardService.create(request.toModel());

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(flashcardSaved.getExternalId())
                .toUri()
        ).build();
    }
}

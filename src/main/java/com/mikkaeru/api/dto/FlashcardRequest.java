package com.mikkaeru.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikkaeru.api.anotation.UniqueValue;
import com.mikkaeru.api.domain.model.enumeration.Box;
import com.mikkaeru.api.domain.model.enumeration.Status;
import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardRequest {

    @NotBlank
    private String back;

    @NotBlank
    private String front;

    private Status status;

    private Boolean review;

    @NotNull
    @UniqueValue(domainClass = Flashcard.class, fieldName = "external_id")
    private UUID externalId;

    private OffsetDateTime lastRevision;
    private OffsetDateTime nextRevision;

    @JsonIgnore
    PropertyMap<FlashcardRequest, Flashcard> skipFieldsMap = new PropertyMap<>() {

        @Override
        protected void configure() {
            skip().setId(null);
        }
    };

    public Flashcard toModel() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(skipFieldsMap);
        return mapper.map(this, Flashcard.class);
    }
}

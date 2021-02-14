package com.mikkaeru.api.domain.model.flashcard;

import com.mikkaeru.api.domain.model.enumeration.Box;
import com.mikkaeru.api.domain.model.enumeration.Status;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Box box;

    @NotBlank
    private String back;

    @NotBlank
    private String front;

    private Status status;

    @NotNull
    private Boolean review;

    @NotNull
    private UUID externalId;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime lastRevision;

    private OffsetDateTime nextRevision;
}

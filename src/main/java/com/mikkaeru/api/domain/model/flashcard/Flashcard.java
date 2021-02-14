package com.mikkaeru.api.domain.model.flashcard;

import com.mikkaeru.api.domain.model.enumeration.Box;
import com.mikkaeru.api.domain.model.enumeration.Status;
import lombok.*;

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

    private Box box;

    private String back;

    private String front;

    private Status status;

    private Boolean review;

    private UUID externalId;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime lastRevision;

    private OffsetDateTime nextRevision;
}

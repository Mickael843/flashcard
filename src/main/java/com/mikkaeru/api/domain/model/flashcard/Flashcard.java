package com.mikkaeru.api.domain.model.flashcard;

import com.mikkaeru.api.domain.model.enumeration.Box;
import com.mikkaeru.api.domain.model.enumeration.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotNull
    @Enumerated(ORDINAL)
    @Column(name = "flashcard_box", nullable = false)
    private Box box;

    @NotBlank
    @Column(nullable = false)
    private String back;

    @NotBlank
    @Column(nullable = false)
    private String front;

    @Enumerated(STRING)
    private Status status;

    @NotNull
    @Column(nullable = false)
    private Boolean review;

    @NotNull
    @Column(nullable = false, unique = true)
    private UUID externalId;

    @NotNull
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime lastRevision;

    private OffsetDateTime nextRevision;
}

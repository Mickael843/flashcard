package com.mikkaeru.api.repository.impl;

import com.mikkaeru.api.domain.model.flashcard.Flashcard;
import com.mikkaeru.api.repository.FlashcardRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FlashcardRepositoryImpl implements FlashcardRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FlashcardRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public void create(Flashcard flashcard) {

        try {

            var sql = """
                    INSERT INTO flashcard(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
                    VALUES (:%s, :%s, :%s, :%s, :%s, :%s, :%s, :%s, :%s, :%s)
                """.formatted(
                    BOX_FIELD, BACK_FIELD, FRONT_FIELD, STATUS_FIELD, REVIEW_FIELD, EXTERNAL_ID_FIELD, CREATED_AT_FIELD,
                    UPDATED_AT_FIELD, LAST_REVISION_FIELD, NEXT_REVISION_FIELD,
                    BOX_FIELD, BACK_FIELD, FRONT_FIELD, STATUS_FIELD, REVIEW_FIELD, EXTERNAL_ID_FIELD, CREATED_AT_FIELD,
                    UPDATED_AT_FIELD, LAST_REVISION_FIELD, NEXT_REVISION_FIELD
            );

            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue(BACK_FIELD, flashcard.getBack())
                    .addValue(FRONT_FIELD, flashcard.getFront())
                    .addValue(STATUS_FIELD, flashcard.getStatus())
                    .addValue(REVIEW_FIELD, flashcard.getReview())
                    .addValue(BOX_FIELD, flashcard.getBox().getNumber())
                    .addValue(CREATED_AT_FIELD, flashcard.getCreatedAt())
                    .addValue(UPDATED_AT_FIELD, flashcard.getUpdatedAt())
                    .addValue(EXTERNAL_ID_FIELD, flashcard.getExternalId())
                    .addValue(LAST_REVISION_FIELD, flashcard.getLastRevision())
                    .addValue(NEXT_REVISION_FIELD, flashcard.getNextRevision());

            jdbcTemplate.update(sql, parameters);

        } catch (DuplicateKeyException e) {
            // TODO Tratar essa exceção
            e.printStackTrace();
        }

    }

    @Override
    public void update(Flashcard flashcard) {

    }

    @Override
    public Optional<Flashcard> findByExternalId(UUID externalId) {
        return Optional.empty();
    }
}

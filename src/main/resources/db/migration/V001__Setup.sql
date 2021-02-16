CREATE TABLE IF NOT EXISTS flashcard (
    id bigint NOT NULL,
    back character varying(255) NOT NULL,
    flashcard_box integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    external_id uuid NOT NULL,
    front character varying(255) NOT NULL,
    last_revision timestamp without time zone,
    next_revision timestamp without time zone,
    review boolean NOT NULL,
    status character varying(255),
    updated_at timestamp without time zone,
    CONSTRAINT flashcard_pkey PRIMARY KEY (id),
    CONSTRAINT uk_flashcard_external_id UNIQUE (external_id)
);
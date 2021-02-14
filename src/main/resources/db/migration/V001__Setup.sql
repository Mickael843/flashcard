CREATE TABLE IF NOT EXISTS flashcard (
    id BIGSERIAL PRIMARY KEY,
    flashcard_box INT8 NOT NULL,
	back TEXT NOT NULL,
	front TEXT NOT NULL,
	status VARCHAR(10),
	review BOOL,
	external_id UUID NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP,
	last_revision TIMESTAMP,
	next_revision TIMESTAMP,
	CONSTRAINT uk_flashcard_external_id UNIQUE (external_id)
);
-- changeset: Stanislaw: create-roles-table

CREATE TABLE IF NOT EXISTS roles(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    type VARCHAR(255) NOT NULL
)
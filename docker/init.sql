CREATE SCHEMA IF NOT EXISTS contacts_schema;

GRANT ALL PRIVILEGES ON SCHEMA contacts_schema TO postgres;

CREATE TABLE IF NOT EXISTS contacts_schema.contact
(
    id BIGINT PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
);

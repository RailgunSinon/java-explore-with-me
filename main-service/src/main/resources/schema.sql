DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS participation_requests CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS compilation_event CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uniq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uniq_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS locations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat real NOT NULL,
    lon real NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation VARCHAR(2000) NOT NULL,
    category_id BIGINT NOT NULL,
    created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    description VARCHAR(7000) NOT NULL,
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id BIGINT NOT NULL,
    confirmed_requests INTEGER,
    location_id BIGINT NOT NULL,
    paid BOOLEAN NOT NULL,
    participant_limit INT NOT NULL,
    request_moderation BOOLEAN NOT NULL,
    state VARCHAR(10) NOT NULL,
    title VARCHAR(120) NOT NULL,
    CONSTRAINT pk_events PRIMARY KEY (id),
    CONSTRAINT fk_event_user FOREIGN KEY(initiator_id) REFERENCES users(id),
    CONSTRAINT fk_event_category FOREIGN KEY(category_id) REFERENCES categories(id),
    CONSTRAINT fk_event_location FOREIGN KEY(location_id) REFERENCES locations(id)
);

CREATE TABLE IF NOT EXISTS participation_requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    created timestamp WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT pk_requests PRIMARY KEY (id),
    CONSTRAINT uq_request UNIQUE (user_id, event_id),
    CONSTRAINT fk_request_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_request_event FOREIGN KEY(event_id) REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS  compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title VARCHAR(50) NOT NULL,
    pinned BOOLEAN NOT NULL,
    CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  compilation_event (
    compilation_id BIGINT,
    event_id BIGINT,
    CONSTRAINT pk_compilation_event PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT fk_event FOREIGN KEY(event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT fk_compilation FOREIGN KEY(compilation_id) REFERENCES compilations(id) ON DELETE CASCADE
);
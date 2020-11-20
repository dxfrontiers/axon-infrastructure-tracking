CREATE TABLE IF NOT EXISTS domain_event_entry (
    global_index BIGSERIAL NOT NULL,
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    type VARCHAR(255),
    event_identifier VARCHAR(255) NOT NULL,
    meta_data bytea,
    payload bytea NOT NULL,
    payload_revision VARCHAR(255),
    payload_type VARCHAR(255) NOT NULL,
    time_stamp VARCHAR(255) NOT NULL,
    PRIMARY KEY (global_index),
    UNIQUE (aggregate_identifier, sequence_number),
    UNIQUE (event_identifier)
);

CREATE TABLE IF NOT EXISTS snapshot_event_entry (
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    event_identifier VARCHAR(255) NOT NULL,
    meta_data bytea,
    payload bytea NOT NULL,
    payload_revision VARCHAR(255),
    payload_type VARCHAR(255) NOT NULL,
    time_stamp VARCHAR(255) NOT NULL,
    PRIMARY KEY (aggregate_identifier, sequence_number),
    UNIQUE (event_identifier)
);

CREATE TABLE IF NOT EXISTS token_entry (
    processor_name VARCHAR(255) NOT NULL,
    segment INTEGER NOT NULL,
    token bytea NULL,
    token_type VARCHAR(255) NULL,
    time_stamp VARCHAR(255) NULL,
    owner VARCHAR(255) NULL,
    PRIMARY KEY (processor_name,segment)
);

CREATE TABLE IF NOT EXISTS singleton_aggregate_id (
    aggregate_type VARCHAR(255) NOT NULL,
    aggregate_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (aggregate_type)
);

CREATE TABLE customer (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(120) NOT NULL,
    cpf             VARCHAR(11) NOT NULL UNIQUE,
    birth_date      DATE NOT NULL
);

CREATE TABLE account (
    id              BIGSERIAL PRIMARY KEY,
    number          VARCHAR(20) NOT NULL UNIQUE,
    type            VARCHAR(20) NOT NULL,
    balance         NUMERIC(15,2) NOT NULL DEFAULT 0,
    status          VARCHAR(20) NOT NULL,
    opened_at       TIMESTAMP NOT NULL,
    customer_id     BIGINT NOT NULL REFERENCES customer(id)
);

CREATE INDEX idx_account_customer_id ON account(customer_id);
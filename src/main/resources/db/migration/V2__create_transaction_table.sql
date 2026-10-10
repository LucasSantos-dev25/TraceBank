CREATE TABLE transaction (
    id                       BIGSERIAL PRIMARY KEY,
    type                     VARCHAR(20) NOT NULL,
    amount                   NUMERIC(15,2) NOT NULL,
    timestamp                TIMESTAMP NOT NULL,
    source_account_id        BIGINT REFERENCES account(id),
    destination_account_id   BIGINT REFERENCES account(id)
);

CREATE INDEX idx_transaction_source_account_id ON transaction(source_account_id);
CREATE INDEX idx_transaction_destination_account_id ON transaction(destination_account_id);
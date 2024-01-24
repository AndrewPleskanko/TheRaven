
CREATE TABLE customer
(
    id        SERIAL PRIMARY KEY,
    created   BIGINT,
    updated   BIGINT,
    full_name VARCHAR(50)         NOT NULL,
    email     VARCHAR(100) UNIQUE NOT NULL,
    phone     VARCHAR(14),
    is_active BOOLEAN DEFAULT true
);

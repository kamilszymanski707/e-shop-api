CREATE SEQUENCE IF NOT EXISTS seq_coupon
    START 1
    INCREMENT 1;

ALTER SEQUENCE seq_coupon OWNER TO prod;

CREATE TABLE IF NOT EXISTS coupon
(
    id          INTEGER      NOT NULL
        CONSTRAINT pk_coupon
            PRIMARY KEY DEFAULT nextval('seq_coupon'),
    amount      INTEGER      NOT NULL,
    description VARCHAR(100) NOT NULL,
    product_id  VARCHAR(255) NOT NULL
        CONSTRAINT fk_product_id UNIQUE
);

ALTER TABLE coupon
    OWNER TO prod;

CREATE TABLE IF NOT EXISTS warehouse (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    quantity_on_hand INTEGER NOT NULL,
    warehouse_id BIGINT NOT NULL,
    CONSTRAINT fk_product_warehouse
        FOREIGN KEY (warehouse_id) REFERENCES warehouse(id)
);

CREATE TABLE IF NOT EXISTS shipment (
    id BIGSERIAL PRIMARY KEY,
    destination VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS shipment_item (
    id BIGSERIAL PRIMARY KEY,
    shipment_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    CONSTRAINT fk_shipment_item_shipment
        FOREIGN KEY (shipment_id) REFERENCES shipment(id),
    CONSTRAINT fk_shipment_item_product
        FOREIGN KEY (product_id) REFERENCES product(id)
);

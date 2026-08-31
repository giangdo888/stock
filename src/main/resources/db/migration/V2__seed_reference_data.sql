INSERT INTO warehouse (name, location)
SELECT 'Sydney DC', 'Sydney, NSW'
WHERE NOT EXISTS (SELECT 1 FROM warehouse WHERE name = 'Sydney DC');

INSERT INTO warehouse (name, location)
SELECT 'Melbourne Hub', 'Melbourne, VIC'
WHERE NOT EXISTS (SELECT 1 FROM warehouse WHERE name = 'Melbourne Hub');

INSERT INTO warehouse (name, location)
SELECT 'Brisbane West', 'Brisbane, QLD'
WHERE NOT EXISTS (SELECT 1 FROM warehouse WHERE name = 'Brisbane West');

INSERT INTO product (sku, name, price, quantity_on_hand, warehouse_id)
SELECT 'SKU-1001', 'Laptop Stand', 79.99, 58, w.id
FROM warehouse w
WHERE w.name = 'Sydney DC'
AND NOT EXISTS (SELECT 1 FROM product WHERE sku = 'SKU-1001');

INSERT INTO product (sku, name, price, quantity_on_hand, warehouse_id)
SELECT 'SKU-1002', 'USB-C Hub', 49.50, 132, w.id
FROM warehouse w
WHERE w.name = 'Sydney DC'
AND NOT EXISTS (SELECT 1 FROM product WHERE sku = 'SKU-1002');

INSERT INTO product (sku, name, price, quantity_on_hand, warehouse_id)
SELECT 'SKU-2001', 'Mechanical Keyboard', 129.00, 41, w.id
FROM warehouse w
WHERE w.name = 'Melbourne Hub'
AND NOT EXISTS (SELECT 1 FROM product WHERE sku = 'SKU-2001');

INSERT INTO product (sku, name, price, quantity_on_hand, warehouse_id)
SELECT 'SKU-2002', 'Monitor Arm', 189.95, 24, w.id
FROM warehouse w
WHERE w.name = 'Melbourne Hub'
AND NOT EXISTS (SELECT 1 FROM product WHERE sku = 'SKU-2002');

INSERT INTO product (sku, name, price, quantity_on_hand, warehouse_id)
SELECT 'SKU-3001', 'Wireless Mouse', 39.99, 210, w.id
FROM warehouse w
WHERE w.name = 'Brisbane West'
AND NOT EXISTS (SELECT 1 FROM product WHERE sku = 'SKU-3001');

INSERT INTO shipment (destination, created_at, status)
SELECT 'Auckland', NOW(), 'CREATED'
WHERE NOT EXISTS (SELECT 1 FROM shipment WHERE destination = 'Auckland' AND status = 'CREATED');

INSERT INTO shipment (destination, created_at, status)
SELECT 'Perth', NOW(), 'DISPATCHED'
WHERE NOT EXISTS (SELECT 1 FROM shipment WHERE destination = 'Perth' AND status = 'DISPATCHED');

INSERT INTO shipment (destination, created_at, status)
SELECT 'Adelaide', NOW(), 'DELIVERED'
WHERE NOT EXISTS (SELECT 1 FROM shipment WHERE destination = 'Adelaide' AND status = 'DELIVERED');

INSERT INTO shipment_item (shipment_id, product_id, quantity)
SELECT s.id, p.id, 8
FROM shipment s
JOIN product p ON p.sku = 'SKU-1001'
WHERE s.destination = 'Auckland'
AND NOT EXISTS (
    SELECT 1 FROM shipment_item si
    JOIN shipment sh ON sh.id = si.shipment_id
    WHERE sh.destination = 'Auckland' AND si.product_id = p.id
);

INSERT INTO shipment_item (shipment_id, product_id, quantity)
SELECT s.id, p.id, 12
FROM shipment s
JOIN product p ON p.sku = 'SKU-2001'
WHERE s.destination = 'Perth'
AND NOT EXISTS (
    SELECT 1 FROM shipment_item si
    JOIN shipment sh ON sh.id = si.shipment_id
    WHERE sh.destination = 'Perth' AND si.product_id = p.id
);

INSERT INTO shipment_item (shipment_id, product_id, quantity)
SELECT s.id, p.id, 20
FROM shipment s
JOIN product p ON p.sku = 'SKU-3001'
WHERE s.destination = 'Adelaide'
AND NOT EXISTS (
    SELECT 1 FROM shipment_item si
    JOIN shipment sh ON sh.id = si.shipment_id
    WHERE sh.destination = 'Adelaide' AND si.product_id = p.id
);

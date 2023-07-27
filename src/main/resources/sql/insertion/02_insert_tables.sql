INSERT INTO `machine` (device_id, private_key, location, description)
VALUES
    ('machine_001', 'private_key_001', 'Location 1', 'Machine 1 description'),
    ('machine_002', 'private_key_002', 'Location 2', 'Machine 2 description');

INSERT INTO `ingredient` (name, machine_id, position, description, inventory)
VALUES
    ('Ingredient 1', 1, 1, 'Ingredient 1 description', 100.0),
    ('Ingredient 2', 1, 2, 'Ingredient 2 description', 150.0),
    ('Ingredient 3', 2, 1, 'Ingredient 3 description', 200.0);

INSERT INTO `drink` (name, image_url, price, price_currency, category, description, user_id, machine_id)
VALUES
    ('Drink 1', 'https://example.com/drink1.jpg', 2.99, 'USD', 'Category A', 'Drink 1 description', NULL, 1),
    ('Drink 2', 'https://example.com/drink2.jpg', 3.49, 'USD', 'Category B', 'Drink 2 description', NULL, 1),
    ('Custom Drink 1', NULL, 4.99, 'USD', 'Custom', 'Custom Drink 1 description', 1, NULL);

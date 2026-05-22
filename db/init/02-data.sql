-- Password for all is "password"
INSERT INTO users (id, name, email, password, role, created_at, updated_at)
VALUES (gen_random_uuid(),
        'admin',
        'admin@app.com',
        '$2a$10$Hdfftkv/tfJXGaTm7lJ0wuk3hwV91Ppq491eV.nCHyTe7qQwSpfaS',
        'ADMIN',
        NOW(),
        NOW());

INSERT INTO users (id, name, email, password, role, created_at, updated_at)
VALUES (gen_random_uuid(),
        'user',
        'user@app.com',
        '$2a$10$Hdfftkv/tfJXGaTm7lJ0wuk3hwV91Ppq491eV.nCHyTe7qQwSpfaS',
        'USER',
        NOW(),
        NOW());

INSERT INTO users (id, name, email, password, role, created_at, updated_at)
SELECT gen_random_uuid(),
       'User ' || i,
       'user' || i || '@example.com',
       '$2a$10$Hdfftkv/tfJXGaTm7lJ0wuk3hwV91Ppq491eV.nCHyTe7qQwSpfaS',
       'USER',
       NOW() - (random() * interval '365 days'),
       NOW() - (random() * interval '30 days')
FROM generate_series(1, 500) AS s(i);
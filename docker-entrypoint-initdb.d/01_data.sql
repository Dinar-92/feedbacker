INSERT INTO roles(name)
VALUES ('ROLE_MODERATOR'),
       ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users(login, password, secret)
VALUES ('admin',
        '81ed7b2361b433013992acc48ecd9263:c8cc007f02f613d1b4cc89fe646a42aaceecb0b6ae1e7c184188663cebc66943',
        'ebb41dcbed87b0a79926487bc3134998:4c5849601d03fbb2ef84fbffb71257927a8cdb337b4cf48e897128c3b078276b'
       ),
       ('user',
        '81ed7b2361b433013992acc48ecd9263:c8cc007f02f613d1b4cc89fe646a42aaceecb0b6ae1e7c184188663cebc66943',
        'ebb41dcbed87b0a79926487bc3134998:4c5849601d03fbb2ef84fbffb71257927a8cdb337b4cf48e897128c3b078276b'
       ),
       ('moderator',
        '81ed7b2361b433013992acc48ecd9263:c8cc007f02f613d1b4cc89fe646a42aaceecb0b6ae1e7c184188663cebc66943',
        'ebb41dcbed87b0a79926487bc3134998:4c5849601d03fbb2ef84fbffb71257927a8cdb337b4cf48e897128c3b078276b'
       );

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.login = 'admin' AND r.name = 'ROLE_ADMIN' LIMIT 1;

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.login = 'user' AND r.name = 'ROLE_USER' LIMIT 1;

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.login = 'moderator' AND r.name = 'ROLE_MODERATOR' LIMIT 1;

INSERT INTO feedback(author_id, author_name, product, content, date_time)
SELECT a.id,
       'Ivan',
       'Lada Granta',
       'Машина нормальная. НО, приобретал автомобиль 2-3 месяца назад, потеют задние фары, протекают задние стекла, едешь и слышишь скрип высшего качества пластика, и скрип окон. А так машина хорошая.',
       now()::date
FROM users a WHERE a.login = 'user' LIMIT 1;
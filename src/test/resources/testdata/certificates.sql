insert into certificates (user_name, user_email, serial_number, send_status, update_status)
values ('user', 'admin@gmail.com', '3010000001', true, now()),
       ('user', 'admin@gmail.com', '3010000002', false, now()),
       ('user', 'admin@gmail.com', '3010000003', false, null),
       ('user', 'admin@gmail.com', null, null, null),
       ('user', 'admin@gmail.com', null, true, now()),
       ('otheruser', 'user@gmail.com', '3020000010', true, now());
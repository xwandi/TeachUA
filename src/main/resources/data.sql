-- insert into roles(name) values
-- ('ROLE_USER'),
-- ('ROLE_ADMIN');

insert into users(email, password, name, role_id) values
('user@gmail.com', '$2y$12$aDvzOnearRd4eulVJID3pOufutAIXVU5i1GKhgpXuvyVmktuSAmqe', 'user',1),
('admin@gmail.com', '$2y$12$iod5PRHZaYrIO6L3onnnk.Mhx9Hc1lb2ehBi0hRvPDD83u6OM/b66', 'admin',2);

insert into coordinates(latitude,longitude) values
(49.83817171443633, 24.029413192472404),
(50.4618198427992, 30.509667766281666),
(50.125637798477996, 36.27248293253908),
(46.652061649424084, 30.74025089769762),
(49.29723912377014, 28.46197426126145),
(50.668395461380584, 26.271188138117644),
(50.83618397763045, 25.350683721301557),
(48.0818320376332, 37.864039806345765),
(48.63918338114249, 39.15160145175507),

(49.73259434488975, 23.997036169252326),
(49.63259434488875, 23.997036168252326),
(49.83259434488775, 23.997036167252326),
(49.83259434488675, 23.997036166252326),
(49.83259434488575, 23.997036165252326),
(49.83259434488475, 23.997036164252326),
(49.83259434488375, 23.997036163252326),
(49.83259434488275, 23.997036162252326),
(49.83259434488175, 23.997036161252326),
(49.83817131443633, 24.029413132472404),
(49.83817121443633, 24.029413122472404);

insert into cities(name,coordinate_id) values
('Львів',1),
('Київ',2),
('Харків',3),
('Одеса',4),
('Вінниця',5),
('Рівне',6),
('Луцьк',7),
('Донецьк',8),
('Луганськ',9);

insert into categories(name) values
('спортивні секції'),
('танці, хореографія'),
('студії раннього розвитку'),
('програмування, робототехніка, STEM'),
('художні студії, мистецтво, дизайн'),
('вокальна студії, музика, музичні інструменти'),
('акторська майстерність, театр'),
('особистісний розвиток'),
('журналістика, дитяче телебачення, монтаж відео, влогів'),
('інше'),
('центр розвитку');

insert into club_contacts(email, phone_numbers) values
('club1@gmail.com','+380000000000'),
('club2@gmail.com','+380000000000'),
('club3@gmail.com','+380000000000'),
('club4@gmail.com','+380000000000'),
('club5@gmail.com','+380000000000'),
('club6@gmail.com','+380000000000'),
('club7@gmail.com','+380000000000'),
('club8@gmail.com','+380000000000'),
('club9@gmail.com','+380000000000'),
('club10@gmail.com','+380000000000'),
('club11@gmail.com','+380000000000'),
('club12@gmail.com','+380000000000'),
('club13@gmail.com','+380000000000'),
('club14@gmail.com','+380000000000'),
('club15@gmail.com','+380000000000'),
('club16@gmail.com','+380000000000'),
('club17@gmail.com','+380000000000'),
('club18@gmail.com','+380000000000'),
('club19@gmail.com','+380000000000'),
('club20@gmail.com','+380000000000');

insert into studios(name) values
('studio1'),
('studio2');

insert into news(title,description) values
('title1','description1'),
('title2','description2');

insert into clubs(age_from, age_to, name, url_logo, url_web, work_time,
category_id, city_id, club_contact_id, coordinate_id, studio_id, user_id) values
(6, 9, 'гурток 1', '#', '#', '09:00-16:00', 1, 1, 1, 10, null, 2),
(7, 10, 'гурток 2', '#', '#', '09:00-16:00', 2, 2, 2, 11, null, 2),
(11, 16, 'гурток 3', '#', '#', '09:00-16:00', 3, 3, 3, 12, null, 2),
(6, 9, 'гурток 4', '#', '#', '09:00-16:00', 4, 4, 4, 13, null, 2),
(5, 10, 'гурток 5', '#', '#', '09:00-16:00', 5, 5, 5, 15, null, 2),
(5, 10, 'гурток 6', '#', '#', '09:00-16:00', 6, 6, 6, 16, null, 2),
(5, 10, 'гурток 7', '#', '#', '09:00-16:00', 7, 7, 7, 17, null, 2),
(5, 10, 'гурток 8', '#', '#', '09:00-16:00', 8, 8, 8, 18, null, 2),
(5, 10, 'гурток 9', '#', '#', '09:00-16:00', 9, 9, 9, 19, null, 2),
(5, 10, 'гурток 10', '#', '#', '09:00-16:00', 1, 2, 10, 20, null, 2);

insert into feedbacks(rate, text, user_name, club_id) values
(5, 'nice club', 'John Smith', 1),
(3, ' ', 'Olia The', 1),
(5, 'nice club', 'John Smith', 2),
(4, 'nice club', 'John Smith', 3),
(5, 'nice club', 'John Smith', 4),
(4, 'nice club', 'John Smith', 5),
(5, 'nice club', 'John Smith', 6),
(4, 'nice club', 'John Smith', 7),
(5, 'nice club', 'John Smith', 8),
(2, 'bad club', 'John Smith', 9),
(1, 'bad club', 'John Smith', 10);
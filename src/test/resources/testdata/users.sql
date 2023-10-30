insert into users(id, email, password, url_logo, first_name, last_name, phone, role_id, provider, status)
values (1, 'admin@gmail.com', '$2y$12$iod5PRHZaYrIO6L3onnnk.Mhx9Hc1lb2ehBi0hRvPDD83u6OM/b66',
        '/static/images/user/avatar/user1.png', 'Admin', 'Admin', '+38000000000', 1, 'local', true),
       (2,  'user@gmail.com', '$2y$12$aDvzOnearRd4eulVJID3pOufutAIXVU5i1GKhgpXuvyVmktuSAmqe',
        '/static/images/user/avatar/user2.png', 'user', 'user', '+38000000000', 2, 'local', true),
       (3, 'user2@gmail.com', '$2y$12$aDvzOnearRd4eulVJID3pOufutAIXVU5i1GKhgpXuvyVmktuSAmqe',
        '/static/images/user/avatar/user3.png', 'user2', 'user2', '+38000000000', 2, 'local', true),
       (4, 'not_active_user@gmail.com', '$2y$12$aDvzOnearRd4eulVJID3pOufutAIXVU5i1GKhgpXuvyVmktuSAmqe',
        '/static/images/user/avatar/user2.png', 'user3', 'user3', '+38000000000', 2, 'local', false);

insert into users(id, email, password, url_logo, first_name, last_name, phone, role_id,provider,status)
values (199, 'deleted@gmail.com', '$2y$12$iod5PRHZaYrIO6L3onnnk.Mhx9Hc1lb2ehBi0hRvPDD83u6OM/b66',
        '/static/images/user/avatar/user1.png', 'Admin', 'Admin', '+38000000000', 1,'local',true);


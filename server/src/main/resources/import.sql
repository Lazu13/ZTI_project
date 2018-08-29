insert into deadline (id, date) values (1, TIMESTAMP('2018-09-05-12.00.00'));

insert into roles (id, name) values (1, 'ADMIN');
insert into roles (id, name) values (2, 'USER');

insert into users (active, email, password, create_time, username, role_id) values (true, 's@mail.com', '$2a$10$hhNmpfEbwjtKynOAC.GiDuqvrZD4ikz3kcgb8WvFCdjUxrNHJh1EW', CURRENT_TIMESTAMP, 'mb', 1);

insert into leagues (active, create_time, name) values (true, CURRENT_TIMESTAMP, 'GLOBALNA');
insert into user_leagues (league_id, user_id, status) values (1, 1, 'ADMIN');
insert into authorities (authority)
values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('admin', true, '$2a$12$mPLE7wdYAB/kNh5OTyQvr.nLMIMa25rU0vgzHIAu2PrKTyZ5QJvea',
        (select id from authorities where authority = 'ROLE_ADMIN'));
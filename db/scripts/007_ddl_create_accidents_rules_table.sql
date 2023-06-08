create table accidents_rules (
    id          serial primary key,
    accident_id int not null references accidents(id),
    rule_id     int not null references rules(id),
        unique (accident_id, rule_id)
);
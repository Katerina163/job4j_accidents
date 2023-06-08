alter table accidents add type_id int not null references types(id);
insert into accidents (name, text, address, type_id)
values ('Иван Иванов', 'что-то произошло непонятное', 'ул. Пупкина, дом 4', 1);
insert into accidents (name, text, address, type_id)
values ('Андрей Андреевич', 'авария', 'проспект Грибов, дом 10', 2);
insert into accidents (name, text, address, type_id)
values ('Василий Васильевич', 'Случилось непонятное в количестве 5 штук', 'ул. Летняя, дом 2', 3);
insert into accidents (name, text, address, type_id)
values ('Владимир Владимирович', 'что-то произошло', 'ул. Круглая , дом 4', 1);
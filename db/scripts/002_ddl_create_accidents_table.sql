create table accidents (
  id   serial primary key,
  name text not null,
  text text not null,
  address text not null,
  type_id int not null references types(id)
);
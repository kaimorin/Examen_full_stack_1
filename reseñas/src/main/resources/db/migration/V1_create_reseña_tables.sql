create table reseñas (
    id double primary key,
    descripcion varchar(255) not null,
    nota double not null check (nota >= 1 and nota <= 5),
    user varchar(255) not null,
    destination varchar(255) not null,
);
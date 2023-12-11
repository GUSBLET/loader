create table if not exists accounts
(
    id            bigserial primary key,
    login         varchar(50) not null unique,
    password      text        not null,
    role          varchar(20) not null
);

create table if not exists brands
(
    id   serial primary key,
    name varchar(50) not null unique
);

create table if not exists models
(
    id                  bigserial primary key,
    name                varchar(50) not null unique,
    description         varchar(500),
    low_polygon_path    text        not null unique,
    height_polygon_path text        not null unique,
    background_path     text unique,
    brand_id            int references brands (id)
);



INSERT INTO accounts (login, password, role)
VALUES ('admin', '$2a$10$seKGFkh9vSVkvLzipl6xNuFpEVsdzJAP05e9Twk2iTK6hpO3RH4sq', 'Admin');
--password: pupsen24

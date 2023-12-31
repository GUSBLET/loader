create table if not exists accounts
(
    id       bigserial primary key,
    login    varchar(50) not null unique,
    password text        not null,
    role     varchar(20) not null
);

create table if not exists brands
(
    id   serial primary key,
    name varchar(50) not null unique
);

create table if not exists models
(
    id                  uuid primary key,
    name                varchar(50) not null unique,
    description         varchar(500),
    low_polygon_path    text        not null unique,
    high_polygon_path text        not null unique,
    background_path     text unique,
    priority            bigserial,
    brand_id            int references brands (id)
);

create table if not exists showcase_backgrounds(
    id uuid primary key,
    name text not null  unique,
    mode_name varchar(50) not null unique
);

create table if not exists camera_point_names
(
    id   serial primary key,
    name varchar(25) not null unique
);

create table if not exists camera_points(
    id uuid primary key,
    point_x_position float,
    point_y_position float,
    point_z_position float,
    camera_x_position float,
    camera_y_position float,
    camera_z_position float,
    description varchar(500),
    camera_point_name_id int references camera_point_names (id),
    model_id uuid references models (id)
);


INSERT INTO accounts (login, password, role)
VALUES ('admin', '$2a$10$seKGFkh9vSVkvLzipl6xNuFpEVsdzJAP05e9Twk2iTK6hpO3RH4sq', 'Admin');
--password: 

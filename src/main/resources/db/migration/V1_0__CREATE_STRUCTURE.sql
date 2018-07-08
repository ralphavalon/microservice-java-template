CREATE TABLE template (
   	id varchar(255) not null,
    created_at datetime,
    updated_at datetime,
    field varchar(255),
    immutable_field varchar(255),
    primary key (id)
);
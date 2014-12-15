# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table affected_object (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               varchar(255),
  location                  varchar(255),
  type_of_disaster          varchar(255),
  image                     varchar(255),
  state_name                varchar(255),
  type_name                 varchar(255),
  constraint pk_affected_object primary key (id))
;

create table object_state (
  name                      varchar(255) not null,
  constraint pk_object_state primary key (name))
;

create table type_of_disaster (
  name                      varchar(255) not null,
  constraint pk_type_of_disaster primary key (name))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  surname                   varchar(255),
  password                  varchar(255),
  phone                     varchar(255),
  profession                varchar(255),
  constraint pk_user primary key (email))
;

alter table affected_object add constraint fk_affected_object_state_1 foreign key (state_name) references object_state (name) on delete restrict on update restrict;
create index ix_affected_object_state_1 on affected_object (state_name);
alter table affected_object add constraint fk_affected_object_type_2 foreign key (type_name) references type_of_disaster (name) on delete restrict on update restrict;
create index ix_affected_object_type_2 on affected_object (type_name);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table affected_object;

drop table object_state;

drop table type_of_disaster;

drop table user;

SET FOREIGN_KEY_CHECKS=1;


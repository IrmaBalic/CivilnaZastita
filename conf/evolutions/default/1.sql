# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table korisnik (
  email                     varchar(255) not null,
  ime                       varchar(255),
  prezime                   varchar(255),
  sifra                     varchar(255),
  telefon                   varchar(255),
  zanimanje                 varchar(255),
  privilegija_naziv         varchar(255),
  constraint pk_korisnik primary key (email))
;

create table nivo_hitnosti (
  naziv                     varchar(255) not null,
  constraint pk_nivo_hitnosti primary key (naziv))
;

create table objekat (
  id                        integer auto_increment not null,
  naziv                     varchar(255),
  opis                      varchar(255),
  lokacija                  varchar(255),
  izvjestaj                 longblob,
  stanje_objekta_naziv      varchar(255),
  tip_nepogode_naziv        varchar(255),
  nivo_hitnosti_naziv       varchar(255),
  podrucje_naziv            varchar(255),
  constraint pk_objekat primary key (id))
;

create table podrucje (
  naziv                     varchar(255) not null,
  constraint pk_podrucje primary key (naziv))
;

create table privilegija (
  naziv                     varchar(255) not null,
  constraint pk_privilegija primary key (naziv))
;

create table slika_objekta (
  id                        integer auto_increment not null,
  naziv                     varchar(255),
  komentar                  varchar(255),
  objekat_id                integer,
  constraint pk_slika_objekta primary key (id))
;

create table stanje_objekta (
  naziv                     varchar(255) not null,
  constraint pk_stanje_objekta primary key (naziv))
;

create table tip_nepogode (
  naziv                     varchar(255) not null,
  constraint pk_tip_nepogode primary key (naziv))
;

alter table korisnik add constraint fk_korisnik_privilegija_1 foreign key (privilegija_naziv) references privilegija (naziv) on delete restrict on update restrict;
create index ix_korisnik_privilegija_1 on korisnik (privilegija_naziv);
alter table objekat add constraint fk_objekat_stanjeObjekta_2 foreign key (stanje_objekta_naziv) references stanje_objekta (naziv) on delete restrict on update restrict;
create index ix_objekat_stanjeObjekta_2 on objekat (stanje_objekta_naziv);
alter table objekat add constraint fk_objekat_tipNepogode_3 foreign key (tip_nepogode_naziv) references tip_nepogode (naziv) on delete restrict on update restrict;
create index ix_objekat_tipNepogode_3 on objekat (tip_nepogode_naziv);
alter table objekat add constraint fk_objekat_nivoHitnosti_4 foreign key (nivo_hitnosti_naziv) references nivo_hitnosti (naziv) on delete restrict on update restrict;
create index ix_objekat_nivoHitnosti_4 on objekat (nivo_hitnosti_naziv);
alter table objekat add constraint fk_objekat_podrucje_5 foreign key (podrucje_naziv) references podrucje (naziv) on delete restrict on update restrict;
create index ix_objekat_podrucje_5 on objekat (podrucje_naziv);
alter table slika_objekta add constraint fk_slika_objekta_objekat_6 foreign key (objekat_id) references objekat (id) on delete restrict on update restrict;
create index ix_slika_objekta_objekat_6 on slika_objekta (objekat_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table korisnik;

drop table nivo_hitnosti;

drop table objekat;

drop table podrucje;

drop table privilegija;

drop table slika_objekta;

drop table stanje_objekta;

drop table tip_nepogode;

SET FOREIGN_KEY_CHECKS=1;


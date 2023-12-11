CREATE role evaluation login password 'evaluation';
create database evaluation;
alter database evaluation OWNER to evaluation;

create table marque(
  id_marque serial primary key ,
  nom_marque varchar(100) not null UNIQUE
);

create table model(
  id_model serial primary key ,
  nom_model varchar(100) not null UNIQUE ,
  id_marque int not null references marque(id_marque)
);

create table type_processeur(
  id_type_processeur serial primary key ,
  nom_processeur varchar(100) UNIQUE
);

create table processeur(
  id_processeur serial primary key ,
  id_type_processeur int not null references type_processeur(id_type_processeur),
  generation int not null,
  frequence numeric(20,2) not null
);

create table type_disque_dure(
  id_type_disque_dure serial primary key ,
  nom_disque_dure varchar(100) not null UNIQUE
);

create table disque_dure(
  id_disque_dure serial primary key ,
  id_type_disque_dure int not null references type_disque_dure(id_type_disque_dure),
  memoire numeric(20,2) not null
);

create table ram(
  id_ram serial primary key ,
  valeur numeric(20,2) not null
);

create table ecran(
  id_ecran serial primary key ,
  valeur numeric(20,2) not null
);

create table laptop(
  id_laptop serial primary key ,
  id_model int not null references model(id_model),
  id_processeur int not null references processeur(id_processeur),
  id_ecran int not null references ecran(id_ecran)
);

create table ram_laptop(
  id_ram_laptop serial primary key ,
  id_laptop int not null references laptop(id_laptop),
  id_ram int not null references ram(id_ram)
);

create table disque_laptop(
  id_disque_laptop serial primary key ,
  id_laptop int not null references laptop(id_laptop),
  id_disque_dure int not null references disque_dure(id_disque_dure)
);

create table points_vente(
  id_points_vente serial primary key ,
  nom_points_vente varchar(100) not null UNIQUE
);

CREATE TABLE utilisateur(
  id_utilisateur serial primary key ,
  id_compte int not null references compte(id_compte),
  nom_utilisateur varchar(100) NOT NULL
);

CREATE TABLE affectation(
  id_affectation serial primary key ,
  id_utilisateur int not null references utilisateur(id_utilisateur),
  id_points_vente int not null references points_vente(id_points_vente),
  date_affectation date not null
);

CREATE TABLE stock_magasin(
  id_stock_magasin serial primary key ,
  id_laptop int not null references laptop(id_laptop),
  prix_achat numeric(20,2) not null ,
  quantite int not null ,
  date_entre_magasin date
);

create table stock_points_vente(
  id_stock_points serial primary key ,
  id_stock_magasin int not null references stock_magasin(id_stock_magasin),
  quantite int not null ,
  date_transfere date not null ,
  dates_arrive date not null ,
  recepter int default 0
);



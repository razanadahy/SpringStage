--create database followUp;
--drop database followUp;
CREATE TABLE lead
(
    idLead serial primary key,
    nom varchar(30),
    email varchar(100),
    mdp varchar(100)
);
--mdp=teste
insert into lead(nom, email, mdp) VALUES ('teste','teste@gmail.com','46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5');
alter table lead add column valid int default 0;
CREATE TABLE developper
(
    idUtilisateur serial primary key,
    nom varchar(100),
    email varchar(100),
    mdp varchar(100)
);
--mdp=dev
insert into developper(nom, email, mdp) VALUES ('dev','dev@gmail.com','ef260e9aa3c673af240d17a2660480361a8e081d1ffeca2a5ed0e3219fc18567');
alter table developper add column valid int default 0;

CREATE TABLE typeProjet
(
    idType serial primary key,
    nomType varchar (100)
);
CREATE TABLE typeTraitement
(
    idType serial primary key,
    nomType varchar(100)
);
alter table typeProjet add column etat int default 0;
alter table typeTraitement add column etat int default 0;
CREATE TABLE etat
(
    idEtat int primary key ,
    nomEtat VARCHAR(30),
    valeur int
);
CREATE TABLE stade
(
    idStade serial primary key ,
    nomStade varchar(100)
);
create sequence secProjet start with 1 increment by 1;

CREATE TABLE Projet
(
    idProjet int primary key,
    idEtat int,
    deadlines date,
    dateCreation date default now(),
    consigne text,
    nomProjet varchar(100),
    idType int,
    foreign key (idType) references typeProjet(idType),
    foreign key (idEtat) references etat(idEtat)
);
--alter table projet add column idLead int default 1;
create sequence secPlug start with 1 increment by 1;
create table plugin
(
    idPlugin int primary key ,
    nomPlugin varchar(100),
    ssh text
);
create sequence secProtection start with 1 increment by 1;
create table protection
(
    idProtection int primary key ,
    nomProtection varchar(100),
    idDifficulte int ,
    description text,
    file text
);
--insert into plugin(idPlugin, nomPlugin, ssh) VALUES ();
CREATE TABLE site
(
    idSite serial primary key,
    nomSite varchar(10000),
    Plateforme  varchar(1000),
    idProtection  int,
    domaine varchar(100),
    idPlugin int,
    idTypeTraitement int,
    idProjet int,
    foreign key (idTypeTraitement) references typeTraitement(idType),
    foreign key (idProjet) references Projet(idProjet),
    foreign key (idProtection) references protection(idProtection),
    foreign key (idPlugin) references plugin(idPlugin)
);
create table difficulte
(
    idDifficulte serial primary key ,
    nomDifficulte varchar(30)
);
insert into difficulte(nomDifficulte) values ('facile');
insert into difficulte(nomDifficulte) values ('moyen');
insert into difficulte(nomDifficulte) values ('difficile');
insert into difficulte(nomDifficulte) values ('Tres difficille');
alter table protection add constraint sc foreign key (idDifficulte) references difficulte(idDifficulte);

CREATE TABLE respDev
(
    idProject int,
    idDev int,
    primary key(idProject,idDev),
    foreign key(idProject) references Projet(idProjet),
    foreign key(idDev) references developper (idUtilisateur),
    tempsEstimation double precision check(tempsEstimation>=0)
);
CREATE TABLE jira
(
    idJira  serial primary key,
    reference varchar(1000),
    url varchar(10000),
    idProjet int ,
    foreign key (idProjet) references projet(idProjet)
);
insert into typeProjet(nomType) values ('Mapping only');
insert into typeProjet(nomType) values ('Création');
insert into typeProjet(nomType) values ('Crawler upgrade');


insert into etat(nomEtat) values ('projets terminés');
insert into etat(nomEtat) values ('projets en cours');
insert into etat(nomEtat) values ('A faire'),('Suspendu');


-- A faire, En cours, Suspendu, Terminé

insert into developper(nom, email) values ('Andrianiavo1','andrianiavo.vit@gmail.com1');
insert into typeTraitement(nomType) values ('Crawl standard');
insert into typeTraitement(nomType) values ('traitement content');
insert into typeTraitement(nomType) values ('blast segment');
insert into typeTraitement(nomType) values ('blast keywords');
insert into typeTraitement(nomType) values ('blast url');
insert into typeTraitement(nomType) values ('blast ean');
insert into typeTraitement(nomType) values ('blast sku');

create table notification
(
    idNotification int primary key ,
    notification varchar(30)
);
insert into notification(notification,idNotification) VALUES ('Creation de Projet',1);
insert into notification(notification,idNotification) VALUES ('Deadline',2);
insert into notification(notification,idNotification) VALUES ('Prio du semaine',3);
insert into notification(notification,idNotification) values ('creation de compte',4);
insert into notification(notification,idNotification) values ('Confirmation',5);
insert into notification(notification,idNotification) values ('Changement de statut',6);
insert into notification(notification,idNotification) values ('Message',7);

create table mouvementNotifLead
(
    idMouvementNotif serial primary key ,
    idNotification int,
    dateNotif timestamp default now(),
    foreign key (idNotification) references notification(idNotification)
);

alter table mouvementNotifLead add column textNotif text ;
select * from mouvementNotifLead;

create table vueNotifLead
(
    idVueNotifLead serial primary key ,
    idMouvementNotif int,
    idLead int,
    dateVue timestamp default now(),
    foreign key (idMouvementNotif) references mouvementNotifLead(idMouvementNotif),
    foreign key (idLead) references lead(idLead)
);
insert into vueNotifLead(idMouvementNotif, idLead) VALUES (5,2);
create or replace view vNotificationLead as
select notification.idNotification,textNotif,dateNotif,mNL.idMouvementNotif,notification.notification, idLead from notification
                                                                                                                       left outer join mouvementNotifLead mNL on notification.idNotification = mNL.idNotification
                                                                                                                       left join vueNotifLead vNL on mNL.idMouvementNotif = vNL.idMouvementNotif;


select COALESCE(idMouvementNotif,-100),dateNotif,textNotif,coalesce((select true from vueNotifLead where rep.idMouvementNotif=idMouvementNotif),false) from vNotificationLead as rep where (idLead=2 or idLead is null) and idNotification=4;

select * from vnotificationlead;
select notification,idNotification,count( idMouvementNotif),idLead from vNotificationLead
group by idLead, idNotification, notification;

--ito
select count(idMouvementNotif)-(select count(idMouvementNotif) from vNotificationLead where rep.idNotification=vNotificationLead.idNotification and vNotificationLead.idLead=2 )
           as nombre,notification,idNotification
from vNotificationLead as rep
group by notification, idNotification order by nombre desc ;


select * from vueNotifLead;


select COALESCE(idMouvementNotif,-100),dateNotif,textNotif,
       coalesce((select true from vueNotifLead where rep.idMouvementNotif=idMouvementNotif limit 1),false) as vues
from vNotificationLead as rep where (idLead=2 or idLead is null) order by vues;

CREATE TABLE stadeProject
(
    idProjet int,
    idStade int,
    etat int,
    primary key(idProjet,idStade),
    foreign key (idStade) references stade(idStade),
    foreign key(idProjet) references Projet(idProjet),
    foreign key (etat) references etat(idEtat)
);
--trigger(apres insert on stade project)






-- create table mouvementNotifDev
-- (
--     idMouvementNotifDev serial primary key ,
--     idNotification int,
--     dateNotification timestamp,
--     idDev int,
--     textNotif text,
--     foreign key (idDev) references developper(idUtilisateur),
--     foreign key (idNotification) references notification(idNotification)
-- );
--select sin(30) ;
--
--
-- CREATE TABLE stadeProject(
--                              idProjet int,
--                              idStade int,
--     -- etat int
--                              primary key(idProjet,idStade),
--                              foreign key (idStade) references stade(idStade),
--                              foreign key(idProjet) references Projet(idProjet)
--
-- );
-- CREATE TABLE listSiteProjet(
--                                idProject int,
--                                idSite int,
--                                primary key(idProject,idSite),
--                                foreign key(idProject) references Projet(idProjet),
--                                foreign key(idSite) references site(idSite)
-- );

-- CREATE TABLE niveau(
--                        idNiveau serial primary key,
--                        nomNiveau varchar(30),
--                        presentant double precision
-- );
-- CREATE TABLE difficulte(
--                            idDifficulte serial primary key,
--                            dateDifficulte timestamp,
--                            niveauDifficulte int,
--                            description text,
--                            foreign key(niveauDifficulte) references niveau(idNiveau)
-- );
-- CREATE TABLE fichierRatache(
--                                idDifficulte int,
--                                image text,
--                                foreign key(idDifficulte) references difficulte(idDifficulte)
-- );


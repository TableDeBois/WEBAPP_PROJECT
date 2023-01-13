DROP TABLE TRANSACTIONS;
DROP TABLE COMPTE;
DROP TABLE TYPE_TRANSACTION;
DROP TABLE TYPE_COMPTE;
DROP TABLE CLIENT;

CREATE TABLE IF NOT EXISTS TYPE_TRANSACTION
(
	id_type_transaction BIGINT NOT NULL AUTO_INCREMENT,
    lib_type_transaction VARCHAR(20) NOT NULL UNIQUE,
    PRIMARY KEY (id_type_transaction)
);

CREATE TABLE IF NOT EXISTS TYPE_COMPTE
(
	id_type_compte BIGINT NOT NULL AUTO_INCREMENT,
    lib_type_compte VARCHAR(25) NOT NULL UNIQUE,
    PRIMARY KEY(id_type_compte)
);

CREATE TABLE IF NOT EXISTS CLIENT
(
	id_client BIGINT NOT NULL AUTO_INCREMENT,
    nom_client VARCHAR(25) NOT NULL,
    prenom_client VARCHAR(25) NOT NULL,
    adresse_client VARCHAR(60),
    PRIMARY KEY(id_client)
);

CREATE TABLE IF NOT EXISTS COMPTE 
(
	id_compte BIGINT NOT NULL AUTO_INCREMENT,
	id_type_compte BIGINT NOT NULL,
    solde float NOT NULL,
    date_creation date NOT NULL,
    date_fermeture date,
    id_client BIGINT NOT NULL,
    PRIMARY KEY(id_compte),
    FOREIGN KEY (id_type_compte) REFERENCES TYPE_COMPTE(id_type_compte),
    FOREIGN KEY (id_client) REFERENCES CLIENT(id_client)
);

CREATE TABLE IF NOT EXISTS TRANSACTIONS
(
	id_transaction BIGINT NOT NULL AUTO_INCREMENT,
    id_type_transaction BIGINT NOT NULL,
    montant float NOT NULL,
    date_transaction date NOT NULL,
    id_compte_debite BIGINT NOT NULL,
    id_compte_receveur BIGINT,
    ind_retrait VARCHAR(1) NOT NULL DEFAULT '0',
    CHECK((ind_retrait='1' AND id_compte_receveur = NULL) OR (ind_retrait='0' AND id_compte_receveur <> NULL)),
    PRIMARY KEY (id_transaction),
    FOREIGN KEY(id_type_transaction) REFERENCES TYPE_TRANSACTION(id_type_transaction),
    FOREIGN KEY(id_compte_debite) REFERENCES COMPTE(id_compte),
    FOREIGN KEY(id_compte_receveur) REFERENCES COMPTE(id_compte)
);

INSERT INTO TYPE_COMPTE(lib_type_compte) VALUES('Compte courant');
INSERT INTO TYPE_COMPTE(lib_type_compte) VALUES('Compte épargne');
INSERT INTO TYPE_COMPTE(lib_type_compte) VALUES('Compte à terme');
INSERT INTO TYPE_COMPTE(lib_type_compte) VALUES('Compte titre');

INSERT INTO TYPE_TRANSACTION(lib_type_transaction) VALUES('Retrait');
INSERT INTO TYPE_TRANSACTION(lib_type_transaction) VALUES('Versement');
INSERT INTO TYPE_TRANSACTION(lib_type_transaction) VALUES('Remboursement');

INSERT INTO CLIENT(nom_client,prenom_client,adresse_client) VALUES ('Jean','Mich','5 rue paul vaucluse');
INSERT INTO CLIENT(nom_client,prenom_client,adresse_client) VALUES ('Grégoire','Gregory','145 avenue Patrick Seb');
INSERT INTO CLIENT(nom_client,prenom_client,adresse_client) VALUES ('Léopold','Mich','76 place d\'Italie');
INSERT INTO CLIENT(nom_client,prenom_client,adresse_client) VALUES ('Victor','Mich','33 rue des ambuscades');
INSERT INTO CLIENT(nom_client,prenom_client,adresse_client) VALUES ('Merlin','Enchanteur','12 passage Tamère du Milieu');

INSERT INTO COMPTE(id_type_compte,solde,date_creation,id_client) VALUES(1,234.5,'2018-05-12',1);
INSERT INTO COMPTE(id_type_compte,solde,date_creation,id_client) VALUES(2,3057.89,'2003-09-16',2);
INSERT INTO COMPTE(id_type_compte,solde,date_creation,id_client) VALUES(2,14783.20,'1995-02-24',3);
INSERT INTO COMPTE(id_type_compte,solde,date_creation,id_client) VALUES(1,28.09,'2020-12-30',4);
INSERT INTO COMPTE(id_type_compte,solde,date_creation,id_client) VALUES(1,50000.99,'1858-01-01',5);

INSERT INTO TRANSACTIONS(id_type_transaction,montant,date_transaction,id_compte_debite,id_compte_receveur,ind_retrait) VALUES(2,23.45,'2019-03-02',1,2,0);
INSERT INTO TRANSACTIONS(id_type_transaction,montant,date_transaction,id_compte_debite,id_compte_receveur,ind_retrait) VALUES(2,2001.21,'2005-06-04',2,3,0);
INSERT INTO TRANSACTIONS(id_type_transaction,montant,date_transaction,id_compte_debite,id_compte_receveur,ind_retrait) VALUES(3,245.67,'2021-09-09',3,4,0);
INSERT INTO TRANSACTIONS(id_type_transaction,montant,date_transaction,id_compte_debite,id_compte_receveur,ind_retrait) VALUES(3,76478.20,'2022-10-06',4,5,0);
INSERT INTO TRANSACTIONS(id_type_transaction,montant,date_transaction,id_compte_debite,ind_retrait) VALUES(1,30.00,'2004-12-17',2,'1');
INSERT INTO TRANSACTIONS(id_type_transaction,montant,date_transaction,id_compte_debite,ind_retrait) VALUES(1,60.00,'2021-04-29',4,'1');
INSERT INTO TRANSACTIONS(id_type_transaction,montant,date_transaction,id_compte_debite,ind_retrait) VALUES(1,2500.00,'1995-03-23',5,'1');
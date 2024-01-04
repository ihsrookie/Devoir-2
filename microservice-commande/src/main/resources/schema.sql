CREATE TABLE commande
(
    id              INT PRIMARY KEY,
    description     VARCHAR(255) NOT NULL,
    quantite        INT NOT NULL,
    datee           DATE NOT NULL,
    montant         INT NOT NULL,
    product_id  int not null
);
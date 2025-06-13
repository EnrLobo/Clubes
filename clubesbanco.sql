CREATE DATABASE IF NOT EXISTS clubes;

CREATE TABLE IF NOT EXISTS users(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    sexo ENUM('M', 'F'),
    email VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS comps(
	id INT NOT NULL auto_increment PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
	clubeRealizacao VARCHAR(20) NOT NULL,
    cidadeRealizacao VARCHAR(20) NOT NULL
); 

CREATE TABLE IF NOT EXISTS clube(
	id INT NOT NULL auto_increment PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
	estado VARCHAR(20) NOT NULL,
    comp_id INT NOT NULL,
    
    FOREIGN KEY(comp_id)
    REFERENCES comps(id)
);    

INSERT INTO users VALUES(
default, "Enrique", "M", "enrique@mail.com"
);

INSERT INTO comps VALUES(
DEFAULT, "Mineiro", "Praia Clube", "Uberlandia"
);

INSERT INTO clubes VALUES(
DEFAULT, "Minas TC", "Minas Gerais", 1
);




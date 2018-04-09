## Spectacle-Manager

This is a Java application,for the management of musical shows.
It's a  client-server application that uses a MySql database.The application is configured to connect on localhost
but you can change this setting from properties.prop file( a file located in the project ).Also the server is configured to run on the localhost and also need to change this setting if you want to run the server on another ip-adress.
In the **Wiki** page you will find a tutorial about the application,how does it looks and how to use it.

## Creating tables

  CREATE TABLE angajati(
      codAg INT NOT NULL AUTO_INCREMENT,
      userId VARCHAR(100),
      userPasswd VARCHAR(100)
      PRIMARY KEY(codAG)
  )
  
  CREATE TABLE cumparatori(
     codC INT NOT NULL,
     nume VARCHAR(100),
     adresa VARCHAR(100),
     email VARCHAR(100)
     PRIMARY KEY(codC)
  )
  
  CREATE TABLE artisti(
      codA INT NOT NULL AUTO_INCREMENT,
      nume VARCHAR(100),
      prenume VARCHAR(100),
      email VARCHAR(80)
      PRIMARY KEY(codA)
  )
  
  CREATE TABLE spectacole(
    codS INT NOT NULL AUTO_INCREMENT,
    locatie VARCHAR(100),
    disp INT,
    vandute INT,
    incepeLa TIME,
    seTinePe DATE,
    PRIMARY KEY(codS)
 )
  
  CREATE TABLE participari(
      codA int NOT NULL,
      codS int NOT NULL,
      PRIMARY KEY(codA,codS),
      FOREIGN KEY (codA) REFERENCES artisti(codA),
      FOREIGN KEY (codS) REFERENCES spectacole(codS)
  )
  
  CREATE TABLE vanzari(
    codS INT NOT NULL,
    codC INT NOT NULL,
    dorite INT,
    codAg INT,
    FOREIGN KEY(codS) REFERENCES spectacole(codS),
    FOREIGN KEY(codC) REFERENCES cumparatori(codC),
    PRIMARY KEY(codS,codC)
  )
  
  
  
  
  

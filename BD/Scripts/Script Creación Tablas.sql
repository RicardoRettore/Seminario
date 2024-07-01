#--------------------------------------------------------------------------
# Creación de esquema

CREATE DATABASE `sgm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;


#--------------------------------------------------------------------------
# Creación Tabla Mantenimiento

CREATE TABLE `mantenimiento` (
  `ID` int NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Descripcion` varchar(1000) NOT NULL,
  `Estado` varchar(15) NOT NULL,
  `FechaProgramado` date DEFAULT NULL,
  PRIMARY KEY (`ID`));

ALTER TABLE `sgm`.`mantenimiento` 
ADD COLUMN `Prioridad` VARCHAR(10) NULL AFTER `FechaProgramado`;

ALTER TABLE `sgm`.`mantenimiento` 
ADD COLUMN `TipoMantenimiento` VARCHAR(20) NULL AFTER `Prioridad`;

#--------------------------------------------------------------------------

#--------------------------------------------------------------------------
# Creación Tabla materiales

CREATE TABLE `sgm`.`materiales` (
  `IDMat` VARCHAR(10) NOT NULL,
  `Descripcion` VARCHAR(45) NOT NULL,
  `TipoMaterial` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IDMat`));
ALTER TABLE `sgm`.`materiales` 
ADD COLUMN `Cantidad` INT NOT NULL AFTER `TipoMaterial`;

ALTER TABLE `sgm`.`materiales` 
ADD COLUMN `Unidad` VARCHAR(45) NOT NULL AFTER `Cantidad`;

ALTER TABLE `sgm`.`materiales` 
CHANGE COLUMN `Unidad` `Unidad` VARCHAR(45) NOT NULL AFTER `TipoMaterial`;

#--------------------------------------------------------------------------
# Creación Tabla materiales_mantenimieto

CREATE TABLE `sgm`.`materiales_mantenimiento` (
  `ID` INT NOT NULL,
  `ID_Mto` INT NOT NULL,
  `ID_Mat` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ID_Mto_idx` (`ID_Mto` ASC) ,
  INDEX `ID_Mat_idx` (`ID_Mat` ASC) ,
  CONSTRAINT `ID_Mto`
    FOREIGN KEY (`ID_Mto`)
    REFERENCES `sgm`.`mantenimiento` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ID_Mat`
    FOREIGN KEY (`ID_Mat`)
    REFERENCES `sgm`.`materiales` (`IDMat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `sgm`.`materiales_mantenimiento` 
CHANGE COLUMN `ID` `ID` INT NOT NULL AUTO_INCREMENT ;

ALTER TABLE `sgm`.`materiales_mantenimiento` 
ADD COLUMN `Cantidad` INT NULL AFTER `ID_Mat`;



#--------------------------------------------------------------------------
# Creación Tabla operarios

CREATE TABLE `sgm`.`operarios` (
  `ID_Op` INT NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `Cargo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID_Op`));



#--------------------------------------------------------------------------
# Creación Tabla Cuadrillas

CREATE TABLE `cuadrillas` (
  `ID_Cuad` varchar(10) NOT NULL,
  `Descripción` varchar(45) NOT NULL,
  `Responsable` varchar(45) NOT NULL,
  PRIMARY KEY (`ID_Cuad`)
) ;

ALTER TABLE `sgm`.`cuadrillas` 
CHANGE COLUMN `Descripción` `Descripcion` VARCHAR(45) NOT NULL ;

#--------------------------------------------------------------------------
# Creación Tabla Operarios_Cuadrillas

CREATE TABLE `sgm`.`operarios_cuadrillas` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ID_Cuad` VARCHAR(10) NULL,
  `ID_Op` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ID_Cuad_idx` (`ID_Cuad` ASC) ,
  INDEX `ID_Op_idx` (`ID_Op` ASC) ,
  CONSTRAINT `ID_Cuad`
    FOREIGN KEY (`ID_Cuad`)
    REFERENCES `sgm`.`cuadrillas` (`ID_Cuad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ID_Op`
    FOREIGN KEY (`ID_Op`)
    REFERENCES `sgm`.`operarios` (`ID_Op`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


#--------------------------------------------------------------------------
# Creación Tabla Mantenimiento_Cuadrillas

CREATE TABLE `sgm`.`mantenimiento_cuadrillas` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ID_Mant` INT NOT NULL,
  `ID_Cuad` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ID_Mant_idx` (`ID_Mant` ASC) ,
  INDEX `ID_Cuad_idx` (`ID_Cuad` ASC) ,
  CONSTRAINT `ID_Mant`
    FOREIGN KEY (`ID_Mant`)
    REFERENCES `sgm`.`mantenimiento` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ID_Cua`
    FOREIGN KEY (`ID_Cuad`)
    REFERENCES `sgm`.`cuadrillas` (`ID_Cuad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


#--------------------------------------------------------------------------
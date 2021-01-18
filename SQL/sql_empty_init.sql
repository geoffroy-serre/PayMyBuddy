-- MySQL Script generated by MySQL Workbench
-- Sat Jan 16 19:22:42 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema pay_my_buddy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pay_my_buddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pay_my_buddy` DEFAULT CHARACTER SET utf8 ;
USE `pay_my_buddy` ;

-- -----------------------------------------------------
-- Table `pay_my_buddy`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` TEXT(20) NOT NULL,
  `password` TEXT(20) NOT NULL,
  `first_name` TEXT(20) NOT NULL,
  `last_name` TEXT(20) NOT NULL,
  `birthdate` DATETIME NOT NULL,
  `address` TEXT(50) NOT NULL,
  `city` TEXT(40) NOT NULL,
  `zip` TEXT(10) NOT NULL,
  `phone` TEXT(15) NOT NULL,
  `treasury` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pay_my_buddy`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount_ht` DOUBLE NOT NULL,
  `date` DATETIME NOT NULL,
  `id_receiver` INT NOT NULL,
  `id_sender` INT NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `id_receiver`, `id_sender`),
  INDEX `fk_transactions_User1_idx` (`id_receiver` ASC) VISIBLE,
  INDEX `fk_transactions_User2_idx` (`id_sender` ASC) VISIBLE,
  CONSTRAINT `fk_transactions_User1`
    FOREIGN KEY (`id_receiver`)
    REFERENCES `pay_my_buddy`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transactions_User2`
    FOREIGN KEY (`id_sender`)
    REFERENCES `pay_my_buddy`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pay_my_buddy`.`friendlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`friendlists` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `id_user2` INT NOT NULL,
  PRIMARY KEY (`id`, `id_user`, `id_user2`),
  INDEX `fk_Friendlists_User1_idx` (`id_user` ASC) VISIBLE,
  INDEX `fk_Friendlists_User2_idx` (`id_user2` ASC) VISIBLE,
  CONSTRAINT `fk_Friendlists_User1`
    FOREIGN KEY (`id_user`)
    REFERENCES `pay_my_buddy`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Friendlists_User2`
    FOREIGN KEY (`id_user2`)
    REFERENCES `pay_my_buddy`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
CREATE DATABASE sfg_dev;
CREATE DATABASE sfg_prod;

# No password. To define it: IDENTIFIED BY 'password';
CREATE USER 'sfg_dev_user'@'localhost';
CREATE USER 'sfg_prod_user'@'localhost';

# Databases created by root, users doesn't have DDL access.

# Assigning DML privileges
GRANT SELECT ON sfg_dev.* TO 'sfg_dev_user'@'localhost';
GRANT INSERT ON sfg_dev.* TO 'sfg_dev_user'@'localhost';
GRANT UPDATE ON sfg_dev.* TO 'sfg_dev_user'@'localhost';
GRANT DELETE ON sfg_dev.* TO 'sfg_dev_user'@'localhost';

GRANT SELECT ON sfg_prod.* TO 'sfg_prod_user'@'localhost';
GRANT INSERT ON sfg_prod.* TO 'sfg_prod_user'@'localhost';
GRANT UPDATE ON sfg_prod.* TO 'sfg_prod_user'@'localhost';
GRANT DELETE ON sfg_prod.* TO 'sfg_prod_user'@'localhost';

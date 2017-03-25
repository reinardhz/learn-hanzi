---POSTGRESQL DATABASE 9.5---

--login to superuser before executing these sql
--Set the system message to use english language:
SET lc_messages TO 'en_US.UTF-8';

--Create new user, that has access as admin.
CREATE USER learnhanzi WITH SUPERUSER CREATEDB CREATEROLE INHERIT LOGIN REPLICATION BYPASSRLS CONNECTION LIMIT -1 ENCRYPTED PASSWORD 'xuehanzi'; 

--Create new tablespace that belongs to learnhanzi, inside specified directory.
CREATE TABLESPACE learnhanzi_tablespace OWNER learnhanzi location 'E:\\Data Pribadi\\learn_hanzi_tablespace';

--Create a new database that could be cloned.
CREATE DATABASE learnhanzi_database WITH OWNER learnhanzi TEMPLATE=template0 LC_COLLATE='C' LC_CTYPE='C' ENCODING 'UTF-8' TABLESPACE learnhanzi_tablespace ALLOW_CONNECTIONS TRUE CONNECTION LIMIT -1 IS_TEMPLATE TRUE;

--Create new schema for user 'learnhanzi'.
CREATE SCHEMA learnhanzi_schema AUTHORIZATION learnhanzi;

--





COMMIT;
---POSTGRESQL DATABASE 9.5---

---------------------------------------------------------
--login to 'superuser' before executing these sql
BEGIN;
--Set the system message to use english language.
SET lc_messages TO 'en_US.UTF-8';

--Create new user, that has access as admin.
CREATE USER learnhanzi WITH SUPERUSER CREATEDB CREATEROLE INHERIT LOGIN REPLICATION BYPASSRLS CONNECTION LIMIT -1 ENCRYPTED PASSWORD 'xuehanzi'; 

--Create new tablespace that belongs to 'learnhanzi', inside specified directory.
CREATE TABLESPACE learnhanzi_tablespace OWNER learnhanzi location 'E:\\Data Pribadi\\learn_hanzi_tablespace';

--Create a new database that could be cloned.
CREATE DATABASE learnhanzi_database WITH OWNER learnhanzi TEMPLATE=template0 ENCODING 'UTF-8' LC_COLLATE='C' LC_CTYPE='C' TABLESPACE learnhanzi_tablespace ALLOW_CONNECTIONS TRUE CONNECTION LIMIT -1 IS_TEMPLATE FALSE;
COMMIT;
---------------------------------------------------------


---------------------------------------------------------
---Login to 'learnhanzi' before executing these sql---

--Create new schema for user 'learnhanzi'.
BEGIN;
DROP SCHEMA learnhanzi_schema;
CREATE SCHEMA learnhanzi_schema AUTHORIZATION learnhanzi;
COMMIT;


---book_data table---
BEGIN;

--Remove sequence book_data in schema learnhanzi_schema, only if exist.
DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_book_data CASCADE;

--Remove table book_data in schema learnhanzi_schema, only if exist.
DROP TABLE IF EXISTS learnhanzi_schema.book_data CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_book_data INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.book_data(
book_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_book_data'),
book_name TEXT UNIQUE
) TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.book_data IS 'This is a table to store information of a book. This book is a book that contains many square box, to let you write Chinese characters by hand. Inside each book has many Chinese character and including its stroke order. You named the book, and put the name in this table';
COMMENT ON COLUMN learnhanzi_schema.book_data.book_name IS 'To store infomation about book name. Example: 第一書';

ALTER SEQUENCE learnhanzi_schema.sequence_book_data OWNED BY learnhanzi_schema.book_data.book_id;

INSERT INTO learnhanzi_schema.book_data(book_name) VALUES ('第一書');

COMMIT;


---hanzi_stroke_data table---
BEGIN;

DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_hanzi_stroke_data CASCADE;

DROP TABLE IF EXISTS learnhanzi_schema.hanzi_stroke_data CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_hanzi_stroke_data INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.hanzi_stroke_data(
hanzi_stroke_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_hanzi_stroke_data'),
hanzi_stroke TEXT,
page_number TEXT,
created_date BIGINT
) TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.hanzi_stroke_data IS 'This is a table to store infomation about Chinese character store orders that was written in the book. All Chinese stroke that was written, should be put in here.';
COMMENT ON COLUMN learnhanzi_schema.hanzi_stroke_data.hanzi_stroke IS 'A Chinese Character that was written in the book, that has stroke order. Example: 愛';
COMMENT ON COLUMN learnhanzi_schema.hanzi_stroke_data.page_number IS 'The page number in related book where this hanzi stroke exist. Example: 二十五';
COMMENT ON COLUMN learnhanzi_schema.hanzi_stroke_data.created_date IS 'A column to store the time and date when this record is inserted. This data using epoch time or unix time, to make it easier to determine the timezone and converting this time to another timezone. Example: 1491448282654';

ALTER SEQUENCE learnhanzi_schema.sequence_hanzi_stroke_data OWNED BY learnhanzi_schema.hanzi_stroke_data.hanzi_stroke_id;

INSERT INTO learnhanzi_schema.hanzi_stroke_data(hanzi_stroke_id, hanzi_stroke, page_number, created_date) VALUES (1, '營業員','一',1491448282654);

COMMIT;

---book_and_stroke table---
BEGIN;

DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_book_and_stroke CASCADE;

DROP TABLE IF EXISTS learnhanzi_schema.book_and_stroke CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_book_and_stroke INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.book_and_stroke(
book_and_stroke_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_book_and_stroke'),
book_id BIGINT NOT NULL,
hanzi_stroke_id BIGINT NOT NULL,
CONSTRAINT fk_book_and_stroke_book_data FOREIGN KEY(book_id) REFERENCES learnhanzi_schema.book_data(book_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_book_and_stroke_hanzi_stroke_data FOREIGN KEY(hanzi_stroke_id) REFERENCES learnhanzi_schema.hanzi_stroke_data(hanzi_stroke_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE
)TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.book_and_stroke IS 'A table to store relationship between book_data table and hanzi_stroke_table. On record of book_data table could relate with many record in hanzi_stroke_data table. One record of hanzi_stroke_data table could relate with many record in book_data table.';

ALTER SEQUENCE learnhanzi_schema.sequence_book_and_stroke OWNED BY learnhanzi_schema.book_and_stroke.book_and_stroke_id;

INSERT INTO learnhanzi_schema.book_and_stroke(book_id, hanzi_stroke_id) VALUES (1,1);

COMMIT;


---user_data table---
BEGIN;
--Remove sequence user_data in schema learnhanzi_schema, only if exist.
DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_user_data CASCADE;

--Remove table user_data in schema learnhanzi_schema, only if exist.
DROP TABLE IF EXISTS learnhanzi_schema.user_data CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_user_data INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.user_data(
user_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_user_data'),
username VARCHAR(50) UNIQUE,
passcode VARCHAR(100)
) TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.user_data IS 'This is a table to store information, for login to the application (username and passcode/password).';

ALTER SEQUENCE learnhanzi_schema.sequence_user_data OWNED BY learnhanzi_schema.user_data.user_id;

INSERT INTO learnhanzi_schema.user_data(username, passcode) VALUES ('admin','gototaiwan');

COMMIT;


---hanzi_data table---
BEGIN;
--Remove sequence user_data in schema learnhanzi_schema, only if exist.
DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_hanzi_data CASCADE;

--Remove table user_data in schema learnhanzi_schema, only if exist.
DROP TABLE IF EXISTS learnhanzi_schema.hanzi_data CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_hanzi_data INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.hanzi_data(
hanzi_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_hanzi_data'),
hanzi TEXT UNIQUE,
created_date BIGINT
) TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.hanzi_data IS 'This is a table to store data about Chinese characters. Must Use UTF-8 encoding. Every Chinese characters that I have learned must be inserted in this table, input the time when I start learning this character too, to monitor the speed progress of learning Chinese characters.';
COMMENT ON COLUMN learnhanzi_schema.hanzi_data.hanzi IS 'A column to store the already learned hanzi. This data must be unique.';
COMMENT ON COLUMN learnhanzi_schema.hanzi_data.created_date IS 'A column to store the time and date when this record is inserted. This data using epoch time or unix time, to make it easier to determine the timezone and converting this time to another timezone';

ALTER SEQUENCE learnhanzi_schema.sequence_hanzi_data OWNED BY learnhanzi_schema.hanzi_data.hanzi_id;

INSERT INTO learnhanzi_schema.hanzi_data(hanzi, created_date) VALUES ('我',1491448282654);

COMMIT;


---user_and_hanzi table---
BEGIN;
--Remove sequence user_and_hanzi in schema learnhanzi_schema, only if exist.
DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_user_and_hanzi CASCADE;

--Remove table user_and_hanzi in schema learnhanzi_schema, only if exist.
DROP TABLE IF EXISTS learnhanzi_schema.user_and_hanzi CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_user_and_hanzi INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.user_and_hanzi(
user_and_hanzi_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_user_and_hanzi'),
user_id BIGINT NOT NULL,
hanzi_id BIGINT NOT NULL,
CONSTRAINT fk_user_and_hanzi_user_data FOREIGN KEY(user_id) REFERENCES learnhanzi_schema.user_data(user_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_user_and_hanzi_hanzi_data FOREIGN KEY(hanzi_id) REFERENCES learnhanzi_schema.hanzi_data(hanzi_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE
) TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.user_and_hanzi IS 'This is a table to make a relationship between user_data table and hanzi_data. One record of user_data could relate with many record in hanzi_data table. One record in hanzi_data could relate with many record in user_data table.';

ALTER SEQUENCE learnhanzi_schema.sequence_user_and_hanzi OWNED BY learnhanzi_schema.user_and_hanzi.user_and_hanzi_id;

INSERT INTO learnhanzi_schema.user_and_hanzi(user_id, hanzi_id) VALUES (1,1);

COMMIT;


---group_data table---
BEGIN;
--Remove sequence group_data in schema learnhanzi_schema, only if exist.
DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_group_data CASCADE;

--Remove table group_data in schema learnhanzi_schema, only if exist.
DROP TABLE IF EXISTS learnhanzi_schema.group_data CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_group_data INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.group_data(
group_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_group_data'),
group_name TEXT
) TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.group_data IS 'This is a table to store information about group name. A group name is used to organize the hanzi. Example: A group name: "第一課" could added with hanzi: "我", "你", "好"。';

ALTER SEQUENCE learnhanzi_schema.sequence_group_data OWNED BY learnhanzi_schema.group_data.group_id;

INSERT INTO learnhanzi_schema.group_data(group_name) VALUES ('漢字');

COMMIT;


---group_and_hanzi table---
BEGIN;
--Remove sequence group_and_hanzi in schema learnhanzi_schema, only if exist.
DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_group_and_hanzi CASCADE;

--Remove table group_and_hanzi in schema learnhanzi_schema, only if exist.
DROP TABLE IF EXISTS learnhanzi_schema.group_and_hanzi CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_group_and_hanzi  INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.group_and_hanzi(
group_and_hanzi_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_group_and_hanzi'),
group_id BIGINT NOT NULL,
hanzi_id BIGINT NOT NULL,
CONSTRAINT fk_group_and_hanzi_group_data FOREIGN KEY(group_id) REFERENCES learnhanzi_schema.group_data(group_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_group_and_hanzi_hanzi_data FOREIGN KEY(hanzi_id) REFERENCES learnhanzi_schema.hanzi_data(hanzi_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE
) TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.group_and_hanzi IS 'This is a table to make a relationship between group_data table and hanzi_data table. 
One record of group_data table could relate with many record in hanzi_data table. One record in hanzi_data could relate with many record in group_data table';

ALTER SEQUENCE learnhanzi_schema.sequence_group_and_hanzi OWNED BY learnhanzi_schema.group_and_hanzi.group_and_hanzi_id;

INSERT INTO learnhanzi_schema.group_and_hanzi(group_id, hanzi_id) VALUES (1,1);

COMMIT;


---pinyin_data table---
BEGIN;

--Remove sequence pinyin_data in schema learnhanzi_schema, only if exist.
DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_pinyin_data CASCADE;

--Remove table pinyin_data in schema learnhanzi_schema, only if exist.
DROP TABLE IF EXISTS learnhanzi_schema.pinyin_data CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_pinyin_data INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.pinyin_data(
pinyin_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_pinyin_data'),
pinyin TEXT
) TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.pinyin_data IS 'This is a table to store data about original pinyin and tone mark of Chinese language (without changing tone). Must use UTF-8 encoding. Example: wǒ, nǐhǎo, yǒu, etc.';

ALTER SEQUENCE learnhanzi_schema.sequence_pinyin_data OWNED BY learnhanzi_schema.pinyin_data.pinyin_id;

INSERT INTO learnhanzi_schema.pinyin_data(pinyin) VALUES ('wǒ');

COMMIT;


---hanzi_and_pinyin table---
BEGIN;
--Remove sequence hanzi_and_pinyin in schema learnhanzi_schema, only if exist.
DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_hanzi_and_pinyin CASCADE;

--Remove table hanzi_and_pinyin in schema learnhanzi_schema, only if exist.
DROP TABLE IF EXISTS learnhanzi_schema.hanzi_and_pinyin CASCADE;

CREATE SEQUENCE learnhanzi_schema.sequence_hanzi_and_pinyin  INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE learnhanzi_schema.hanzi_and_pinyin(
hanzi_and_pinyin_id BIGINT PRIMARY KEY DEFAULT  nextval('learnhanzi_schema.sequence_hanzi_and_pinyin'),
hanzi_id BIGINT NOT NULL,
pinyin_id BIGINT NOT NULL,
CONSTRAINT fk_hanzi_and_pinyin_hanzi_data FOREIGN KEY(hanzi_id) REFERENCES learnhanzi_schema.hanzi_data(hanzi_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_hanzi_and_pinyin_pinyin_data FOREIGN KEY(pinyin_id) REFERENCES learnhanzi_schema.pinyin_data(pinyin_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE
) TABLESPACE learnhanzi_tablespace;

COMMENT ON TABLE learnhanzi_schema.hanzi_and_pinyin IS 'This is a table to make a relationship between hanzi_data table and pinyin_data table. 
One record of hanzi_data could relate with many record in pinyin_data table. One record in pinyin_data could relate with many record in hanzi_data table';

ALTER SEQUENCE learnhanzi_schema.sequence_hanzi_and_pinyin OWNED BY learnhanzi_schema.hanzi_and_pinyin.hanzi_and_pinyin_id;

INSERT INTO learnhanzi_schema.hanzi_and_pinyin(hanzi_id, pinyin_id) VALUES (1,1);

COMMIT;


---pronunciation_data table---

---pronunciation_and_definition table---

---definition_data table---

---definition_and_part_of_speech table---

---part_of_speech_data table---

---part_of_speech_and_example---

---example_data---


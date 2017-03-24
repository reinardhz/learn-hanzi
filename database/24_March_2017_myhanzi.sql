--Remove sequence group_data in schema learnhanzi_schema, only if exist.
DROP SEQUENCE IF EXISTS learnhanzi_schema.sequence_group_data CASCADE;

--Remove table group_data in schema learnhanzi_schema, only if exist.
DROP TABLE IF EXISTS learnhanzi_schema.group_data CASCADE;
COMMIT;

--Create sequence for table group_data
CREATE SEQUENCE learnhanzi_schema.sequence_group_data INCREMENT BY 1 CACHE 1 NO CYCLE OWNED BY learnhanzi_schema.group_data;




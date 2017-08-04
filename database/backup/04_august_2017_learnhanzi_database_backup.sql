--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: learnhanzi_schema; Type: SCHEMA; Schema: -; Owner: learnhanzi
--

CREATE SCHEMA learnhanzi_schema;


ALTER SCHEMA learnhanzi_schema OWNER TO learnhanzi;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = learnhanzi_schema, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: book_and_stroke; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE book_and_stroke (
    book_and_stroke_id bigint NOT NULL,
    book_id bigint NOT NULL,
    hanzi_stroke_id bigint NOT NULL
);


ALTER TABLE book_and_stroke OWNER TO learnhanzi;

--
-- Name: TABLE book_and_stroke; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE book_and_stroke IS 'A table to store relationship between book_data table and hanzi_stroke_table. On record of book_data table could relate with many record in hanzi_stroke_data table. One record of hanzi_stroke_data table could relate with many record in book_data table.';


--
-- Name: book_data; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE book_data (
    book_id bigint NOT NULL,
    book_name text
);


ALTER TABLE book_data OWNER TO learnhanzi;

--
-- Name: TABLE book_data; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE book_data IS 'This is a table to store information of a book. This book is a book that contains many square box, to let you write Chinese characters by hand. Inside each book has many Chinese character and including its stroke order. You named the book, and put the name in this table';


--
-- Name: COLUMN book_data.book_name; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON COLUMN book_data.book_name IS 'To store infomation about book name. Example: 第一書';


--
-- Name: group_and_hanzi; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE group_and_hanzi (
    group_and_hanzi_id bigint NOT NULL,
    group_id bigint NOT NULL,
    hanzi_id bigint NOT NULL
);


ALTER TABLE group_and_hanzi OWNER TO learnhanzi;

--
-- Name: TABLE group_and_hanzi; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE group_and_hanzi IS 'This is a table to make a relationship between group_data table and hanzi_data table. 
One record of group_data could relate with many record in hanzi_data table. One record in hanzi_data could relate with many record in group_data table';


--
-- Name: group_data; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE group_data (
    group_id bigint NOT NULL,
    group_name text
);


ALTER TABLE group_data OWNER TO learnhanzi;

--
-- Name: TABLE group_data; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE group_data IS 'This is a table to store information about group name. A group name is used to organize the hanzi. Example: A group name: "第一課" could added with hanzi: "我", "你", "好"。';


--
-- Name: hanzi_and_pinyin; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE hanzi_and_pinyin (
    hanzi_and_pinyin_id bigint NOT NULL,
    hanzi_id bigint NOT NULL,
    pinyin_id bigint NOT NULL
);


ALTER TABLE hanzi_and_pinyin OWNER TO learnhanzi;

--
-- Name: TABLE hanzi_and_pinyin; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE hanzi_and_pinyin IS 'This is a table to make a relationship between hanzi_data table and pinyin_data table. 
One record of hanzi_data could relate with many record in pinyin_data table. One record in pinyin_data could relate with many record in hanzi_data table';


--
-- Name: hanzi_data; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE hanzi_data (
    hanzi_id bigint NOT NULL,
    hanzi text,
    created_date bigint
);


ALTER TABLE hanzi_data OWNER TO learnhanzi;

--
-- Name: TABLE hanzi_data; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE hanzi_data IS 'This is a table to store data about Chinese characters. Must Use UTF-8 encoding. Every Chinese characters that I have learned must be inserted in this table, input the time when I start learning this character too, to monitor the speed progress of learning Chinese characters.';


--
-- Name: COLUMN hanzi_data.hanzi; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON COLUMN hanzi_data.hanzi IS 'A column to store the already learned hanzi. This data must be unique.';


--
-- Name: COLUMN hanzi_data.created_date; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON COLUMN hanzi_data.created_date IS 'A column to store the time and date when this record is inserted. This data using epoch time or unix time, to make it easier to determine the timezone and converting this time to another timezone';


--
-- Name: hanzi_stroke_data; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE hanzi_stroke_data (
    hanzi_stroke_id bigint NOT NULL,
    hanzi_stroke text,
    page_number text,
    created_date bigint
);


ALTER TABLE hanzi_stroke_data OWNER TO learnhanzi;

--
-- Name: TABLE hanzi_stroke_data; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE hanzi_stroke_data IS 'This is a table to store infomation about Chinese character store orders that was written in the book. All Chinese stroke that was written, should be put in here.';


--
-- Name: COLUMN hanzi_stroke_data.hanzi_stroke; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON COLUMN hanzi_stroke_data.hanzi_stroke IS 'A Chinese Character that was written in the book, that has stroke order. Example: 愛';


--
-- Name: COLUMN hanzi_stroke_data.page_number; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON COLUMN hanzi_stroke_data.page_number IS 'The page number in related book where this hanzi stroke exist. Example: 二十五';


--
-- Name: COLUMN hanzi_stroke_data.created_date; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON COLUMN hanzi_stroke_data.created_date IS 'A column to store the time and date when this record is inserted. This data using epoch time or unix time, to make it easier to determine the timezone and converting this time to another timezone. Example: 1491448282654';


--
-- Name: pinyin_data; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE pinyin_data (
    pinyin_id bigint NOT NULL,
    pinyin text
);


ALTER TABLE pinyin_data OWNER TO learnhanzi;

--
-- Name: TABLE pinyin_data; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE pinyin_data IS 'This is a table to store data about original pinyin and tone mark of Chinese language (without changing tone). Must use UTF-8 encoding. Example: wǒ, nǐhǎo, yǒu, etc.';


--
-- Name: sequence_book_and_stroke; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_book_and_stroke
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_book_and_stroke OWNER TO learnhanzi;

--
-- Name: sequence_book_and_stroke; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_book_and_stroke OWNED BY book_and_stroke.book_and_stroke_id;


--
-- Name: sequence_book_data; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_book_data
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_book_data OWNER TO learnhanzi;

--
-- Name: sequence_book_data; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_book_data OWNED BY book_data.book_id;


--
-- Name: sequence_group_and_hanzi; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_group_and_hanzi
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_group_and_hanzi OWNER TO learnhanzi;

--
-- Name: sequence_group_and_hanzi; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_group_and_hanzi OWNED BY group_and_hanzi.group_and_hanzi_id;


--
-- Name: sequence_group_data; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_group_data
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_group_data OWNER TO learnhanzi;

--
-- Name: sequence_group_data; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_group_data OWNED BY group_data.group_id;


--
-- Name: sequence_hanzi_and_pinyin; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_hanzi_and_pinyin
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_hanzi_and_pinyin OWNER TO learnhanzi;

--
-- Name: sequence_hanzi_and_pinyin; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_hanzi_and_pinyin OWNED BY hanzi_and_pinyin.hanzi_and_pinyin_id;


--
-- Name: sequence_hanzi_data; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_hanzi_data
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_hanzi_data OWNER TO learnhanzi;

--
-- Name: sequence_hanzi_data; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_hanzi_data OWNED BY hanzi_data.hanzi_id;


--
-- Name: sequence_hanzi_stroke_data; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_hanzi_stroke_data
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_hanzi_stroke_data OWNER TO learnhanzi;

--
-- Name: sequence_hanzi_stroke_data; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_hanzi_stroke_data OWNED BY hanzi_stroke_data.hanzi_stroke_id;


--
-- Name: sequence_pinyin_data; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_pinyin_data
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_pinyin_data OWNER TO learnhanzi;

--
-- Name: sequence_pinyin_data; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_pinyin_data OWNED BY pinyin_data.pinyin_id;


--
-- Name: user_and_hanzi; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE user_and_hanzi (
    user_and_hanzi_id bigint NOT NULL,
    user_id bigint NOT NULL,
    hanzi_id bigint NOT NULL
);


ALTER TABLE user_and_hanzi OWNER TO learnhanzi;

--
-- Name: TABLE user_and_hanzi; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE user_and_hanzi IS 'This is a table to make a relationship between user_data table and hanzi_data. One record of user_data could relate with many record in hanzi_data table. One record in hanzi_data could relate with many record in user_data table.';


--
-- Name: sequence_user_and_hanzi; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_user_and_hanzi
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_user_and_hanzi OWNER TO learnhanzi;

--
-- Name: sequence_user_and_hanzi; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_user_and_hanzi OWNED BY user_and_hanzi.user_and_hanzi_id;


--
-- Name: user_data; Type: TABLE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE TABLE user_data (
    user_id bigint NOT NULL,
    username character varying(50),
    passcode character varying(100)
);


ALTER TABLE user_data OWNER TO learnhanzi;

--
-- Name: TABLE user_data; Type: COMMENT; Schema: learnhanzi_schema; Owner: learnhanzi
--

COMMENT ON TABLE user_data IS 'This is a table to store information, for login to the application (username and passcode/password).';


--
-- Name: sequence_user_data; Type: SEQUENCE; Schema: learnhanzi_schema; Owner: learnhanzi
--

CREATE SEQUENCE sequence_user_data
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence_user_data OWNER TO learnhanzi;

--
-- Name: sequence_user_data; Type: SEQUENCE OWNED BY; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER SEQUENCE sequence_user_data OWNED BY user_data.user_id;


--
-- Name: book_and_stroke_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY book_and_stroke ALTER COLUMN book_and_stroke_id SET DEFAULT nextval('sequence_book_and_stroke'::regclass);


--
-- Name: book_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY book_data ALTER COLUMN book_id SET DEFAULT nextval('sequence_book_data'::regclass);


--
-- Name: group_and_hanzi_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY group_and_hanzi ALTER COLUMN group_and_hanzi_id SET DEFAULT nextval('sequence_group_and_hanzi'::regclass);


--
-- Name: group_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY group_data ALTER COLUMN group_id SET DEFAULT nextval('sequence_group_data'::regclass);


--
-- Name: hanzi_and_pinyin_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY hanzi_and_pinyin ALTER COLUMN hanzi_and_pinyin_id SET DEFAULT nextval('sequence_hanzi_and_pinyin'::regclass);


--
-- Name: hanzi_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY hanzi_data ALTER COLUMN hanzi_id SET DEFAULT nextval('sequence_hanzi_data'::regclass);


--
-- Name: hanzi_stroke_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY hanzi_stroke_data ALTER COLUMN hanzi_stroke_id SET DEFAULT nextval('sequence_hanzi_stroke_data'::regclass);


--
-- Name: pinyin_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY pinyin_data ALTER COLUMN pinyin_id SET DEFAULT nextval('sequence_pinyin_data'::regclass);


--
-- Name: user_and_hanzi_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY user_and_hanzi ALTER COLUMN user_and_hanzi_id SET DEFAULT nextval('sequence_user_and_hanzi'::regclass);


--
-- Name: user_id; Type: DEFAULT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY user_data ALTER COLUMN user_id SET DEFAULT nextval('sequence_user_data'::regclass);


--
-- Data for Name: book_and_stroke; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY book_and_stroke (book_and_stroke_id, book_id, hanzi_stroke_id) FROM stdin;
1	1	1
6	1	2
15	1	5
22	1	12
23	1	17
24	1	18
25	1	19
26	1	20
27	1	21
28	1	22
29	1	23
30	1	24
31	1	25
32	1	26
33	1	27
34	1	28
35	1	29
36	1	30
37	1	31
38	1	32
39	1	33
40	1	34
41	1	35
42	1	36
43	1	37
44	1	38
45	1	39
46	6	40
47	6	41
48	6	42
49	6	43
50	6	44
51	6	45
52	6	46
53	6	47
54	6	48
55	6	49
56	6	50
57	6	51
58	6	52
59	6	53
60	6	54
61	6	55
62	6	56
63	6	57
64	6	58
65	6	59
66	6	60
67	6	61
68	6	62
69	6	63
70	6	64
71	6	65
72	6	66
73	6	67
74	6	68
75	6	69
76	6	70
77	6	71
78	6	72
79	6	73
80	6	74
81	6	75
82	6	76
83	6	77
84	6	78
85	6	79
86	6	80
87	6	81
88	6	82
89	6	83
90	6	84
91	6	85
92	6	86
93	6	87
94	6	88
95	6	89
96	6	90
97	6	91
98	6	92
99	6	93
100	6	94
101	6	95
102	6	96
103	6	97
104	6	98
105	6	99
106	6	100
107	6	101
108	6	102
109	6	103
110	6	104
111	6	105
112	6	106
113	6	107
114	6	108
115	6	109
116	6	110
117	6	111
118	6	112
119	6	113
120	6	114
121	6	115
122	8	116
123	8	117
124	8	118
125	8	119
126	8	120
127	8	121
128	8	122
129	8	123
130	8	124
131	8	125
132	8	126
133	8	127
134	8	128
135	8	129
136	8	130
137	8	131
138	8	132
139	8	133
140	8	134
141	8	135
142	8	136
143	8	137
144	8	138
145	8	139
146	8	140
147	8	141
148	8	142
149	8	143
150	8	144
151	8	145
152	8	146
153	8	147
154	8	148
155	8	149
156	8	150
157	8	151
158	8	152
159	8	153
160	8	154
\.


--
-- Data for Name: book_data; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY book_data (book_id, book_name) FROM stdin;
1	第一書
6	第二書
8	第三書
51	第四書
54	第五書
55	第六書
56	第七書
\.


--
-- Data for Name: group_and_hanzi; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY group_and_hanzi (group_and_hanzi_id, group_id, hanzi_id) FROM stdin;
1	1	1
\.


--
-- Data for Name: group_data; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY group_data (group_id, group_name) FROM stdin;
1	漢字
\.


--
-- Data for Name: hanzi_and_pinyin; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY hanzi_and_pinyin (hanzi_and_pinyin_id, hanzi_id, pinyin_id) FROM stdin;
1	1	1
\.


--
-- Data for Name: hanzi_data; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY hanzi_data (hanzi_id, hanzi, created_date) FROM stdin;
1	我	1491448282654
2	你	1491637831583
3	好	1491637958453
319	師	1494579682228
5	第	1491637969700
6	一	1491637974954
7	課	1491637979107
8	文	1491637992248
9	生	1491638052643
10	詞	1491638056699
11	二	1491638102782
12	三	1491638106279
13	四	1491638109483
14	五	1491638112816
15	六	1491638116330
16	七	1491638123104
17	九	1491638126759
18	八	1491638130257
321	食	1494579704536
20	十	1491638195864
21	大	1491638210661
22	不	1491638214033
23	口	1491638245434
24	白	1491638251234
25	女	1491638254250
26	嗎	1491638262977
27	馬	1491638266202
28	語	1491638288261
29	音	1491638292552
323	身	1494579717666
31	聲	1491638379905
32	母	1491638387656
33	親	1491638390841
34	韻	1491638416437
35	拼	1491638429626
36	調	1491638450880
37	漢	1491638550212
38	多	1491638571779
39	少	1491638575580
40	數	1491638587017
41	書	1491638589769
42	自	1491638604474
43	由	1491638608074
44	和	1491638615769
45	都	1491638629611
46	是	1491638632946
47	開	1491638742201
48	頭	1491638752556
49	電	1491638765483
50	腦	1491638773747
51	叫	1491638811015
52	如	1491638820521
53	果	1491638824597
54	過	1491638828536
55	國	1491638832418
325	替	1494579730262
57	誰	1491638841741
58	水	1491638845785
59	華	1491638906164
60	話	1491638909907
61	個	1491638984850
62	要	1491638997800
327	代	1494579738956
329	謝	1494579750581
65	小	1491639061562
66	笑	1491639066450
67	間	1491639140378
68	動	1491639474917
69	錢	1491639929521
70	前	1491639937660
71	的	1491640026355
72	中	1491640074623
73	很	1491640080155
74	重	1491640085667
75	工	1491640127747
76	作	1491640133154
77	規	1491640283466
78	則	1491640289745
610	姓	1494583282804
333	種	1494579799107
81	可	1491640345337
82	以	1491640350088
83	別	1491640376706
84	日	1491640418928
85	本	1491640422135
86	用	1491640532675
87	永	1491640539993
88	吳	1491640544079
89	敏	1491640548392
90	媽	1491640622946
91	爸	1491640627291
92	姐	1491640631584
93	哥	1491640635674
94	奶	1491640639872
95	爺	1491640647683
96	在	1491640697993
97	主	1491640703961
98	意	1491640722712
99	思	1491640738507
100	他	1491640747753
101	她	1491640750652
102	愛	1491640759546
103	元	1491640809659
104	員	1491640820778
105	職	1491640833361
106	上	1491640854250
107	下	1491640857320
108	山	1491640861163
109	周	1491640872352
335	有	1494579815530
111	週	1491641860018
112	兩	1491642699689
337	又	1494579826471
339	油	1494579838131
115	字	1491642965027
116	子	1491642968840
117	了	1491642972130
118	變	1491642977498
341	左	1494579849683
614	東	1494583666996
121	練	1491643038010
122	習	1491643043232
345	年	1494579881506
618	希	1494583682620
349	杯	1494579971313
351	睡	1494579979011
353	冰	1494579988393
355	餅	1494580037465
357	服	1494580055515
359	紙	1494580070280
361	等	1494580095585
363	午	1494580116698
622	情	1494583781595
149	名	1494572833978
369	能	1494580185018
630	晚	1494583947609
152	教	1494572990629
153	會	1494572999938
155	公	1494575785911
156	斤	1494575791507
375	節	1494580229121
377	百	1494580239114
159	那	1494575822537
160	哪	1494575826688
379	千	1494580248754
381	兒	1494580264418
163	次	1494575865969
164	形	1494576388917
165	容	1494576397929
166	離	1494576431880
167	合	1494576438073
642	火	1494584057625
387	家	1494580312179
171	副	1494577762278
320	事	1494579694610
173	幫	1494577810578
174	助	1494577817740
322	堂	1494579710942
177	同	1494578450531
326	帶	1494579735190
328	些	1494579745722
180	看	1494578488828
181	請	1494578492634
182	輕	1494578499042
183	營	1494578521275
184	業	1494578530354
619	介	1494583748926
187	點	1494578547620
188	郵	1494578553965
334	朋	1494579810994
191	件	1494578572316
192	局	1494578580370
193	發	1494578585747
338	加	1494579833375
195	新	1494578600730
196	臺	1494578608164
197	幣	1494578612914
198	印	1494578624673
199	尼	1494578629986
200	盾	1494578635922
201	換	1494578646493
202	港	1494578654035
203	人	1494578662604
204	民	1494578669971
340	友	1494579843587
206	歐	1494578683723
207	雜	1494578693843
208	誌	1494578766754
209	瑪	1494578827442
210	美	1494578832778
211	麗	1494578839674
212	圖	1494578853815
342	右	1494579855974
214	館	1494578875771
344	樂	1494579873003
216	灣	1494578890518
346	念	1494579927617
218	太	1494578898403
219	繁	1494578905507
220	體	1494578913753
222	警	1494578927155
223	察	1494578934914
224	號	1494578954250
225	碼	1494578959995
226	辦	1494578972707
354	并	1494579992401
230	手	1494579005693
231	機	1494579012779
232	房	1494579023723
356	衣	1494580048977
234	見	1494579040553
235	急	1494579052531
236	救	1494579061687
358	報	1494580064530
360	總	1494580091634
639	草	1494584037907
241	酒	1494579098091
242	啤	1494579103819
243	留	1494579113577
244	學	1494579118412
366	化	1494580147513
246	雪	1494579128653
247	買	1494579142268
248	賣	1494579146070
249	吧	1494579151801
368	出	1494580177233
646	樣	1494584187995
252	快	1494579167395
649	瓜	1494584221636
254	角	1494579182679
255	毛	1494579187688
256	分	1494579193883
257	還	1494579199143
258	海	1494579202730
374	杰	1494580217025
652	莓	1494584247028
262	橘	1494579225486
861	連	1501487918001
382	先	1494580271529
265	共	1494579247331
266	給	1494579252267
267	找	1494579257067
269	地	1494579274370
386	再	1494580302817
271	打	1494579340100
388	呢	1494580321228
389	們	1494580329107
390	門	1494580333773
275	罵	1494579358020
276	忙	1494579365083
277	男	1494579371076
278	難	1494579374603
279	南	1494579378507
280	知	1494579395488
281	到	1494579401159
282	道	1494579405035
664	或	1494584641624
667	物	1494584696811
393	後	1494580423619
670	夫	1494584740151
287	去	1494579430641
288	北	1494579436561
289	京	1494579452130
290	對	1494579460920
395	餓	1494580438746
292	明	1494579471461
293	命	1494579476543
294	金	1494579485653
295	今	1494579490282
296	零	1494579495776
297	令	1494579501051
298	沒	1494579518400
299	關	1494579523966
300	係	1494579534794
301	星	1494579541735
302	期	1494579547740
303	幾	1494579556813
304	回	1494579562240
305	校	1494579570997
306	長	1494579580101
396	吃	1494580445523
308	城	1494579595980
397	做	1494580450872
398	飯	1494580455509
673	歡	1494584784318
400	包	1494580476657
313	拿	1494579618340
401	面	1494580488624
402	比	1494580500831
403	必	1494580504965
317	這	1494579664754
318	老	1494579670332
676	秘	1494584833452
405	方	1494580537018
406	高	1494580548297
679	羅	1494584876692
408	住	1494580560504
682	者	1494584908291
410	祝	1494580576061
411	聽	1494580599467
685	醫	1494584929588
414	錄	1494580622603
416	寫	1494580662866
417	時	1494580672734
418	候	1494580678753
616	洗	1494583674517
420	恨	1494580698463
421	氣	1494580724427
422	李	1494580771524
423	唱	1494580789088
424	歌	1494580794899
425	昌	1494580807504
426	甜	1494580839654
620	紹	1494583756339
428	菜	1494580859302
429	青	1494580863867
624	溫	1494583816309
431	來	1494580881155
432	哦	1494580887010
433	啊	1494580893056
434	售	1494580908760
435	貨	1494580920486
628	安	1494583932980
632	坐	1494583974306
438	嗯	1494580943874
636	進	1494584000268
440	曲	1494580953765
441	商	1494580966619
442	店	1494580975667
443	葡	1494580985287
444	萄	1494580991760
640	花	1494584047031
446	酸	1494581011830
447	哇	1494581018181
448	便	1494581026497
449	宜	1494581035746
450	嘗	1494581129699
451	哭	1494581150211
452	苦	1494581171690
453	秋	1494581180276
454	田	1494581184028
455	冬	1494581195601
456	春	1494581202119
457	夏	1494581208630
459	想	1494581270499
460	香	1494581284635
461	貴	1494581338045
462	鬼	1494581343279
463	吵	1494581484830
464	麽	1494581492979
465	神	1494581498006
466	什	1494581503154
467	真	1494581512094
647	梨	1494584200120
469	得	1494581520517
471	喝	1494581534269
653	蕉	1494584264661
473	題	1494581625817
656	心	1494584527013
477	位	1494581737450
478	銀	1494581744436
479	行	1494581749696
668	需	1494584707160
483	玩	1494581800635
484	衹	1494581923989
485	狗	1494581970539
486	走	1494581979426
487	父	1494581998836
674	迎	1494584790774
677	經	1494584844526
680	蘭	1494584879723
683	護	1494584918364
492	量	1494582055651
493	讀	1494582067197
495	豆	1494582093716
689	較	1494584978389
497	米	1494582121749
693	塊	1501480855857
500	法	1494582198716
501	昨	1494582218481
502	月	1494582230026
503	您	1494582278002
504	起	1494582287954
505	英	1494582298405
865	說	1501493143693
508	感	1494582344719
510	己	1494582363570
511	已	1494582375923
512	消	1494582392688
513	防	1494582403127
515	緊	1494582426802
517	德	1494582443378
518	韓	1494582452244
519	張	1494582459770
705	怎	1501480974413
521	西	1494582475945
522	班	1494582483274
523	牙	1494582489468
524	阿	1494582498041
525	拉	1494582509188
526	伯	1494582520157
527	查	1494582544093
529	樓	1494582558085
530	筷	1494582563845
531	條	1494582575569
532	麵	1494582581189
533	麥	1494582587644
537	雨	1494582606422
538	傘	1494582611731
539	汽	1494582617022
540	車	1494582620893
541	預	1494582631218
548	定	1494582687073
549	狀	1494582705745
550	典	1494582717656
552	湯	1494582726499
553	鷄	1494582749710
554	蛋	1494582755570
555	雞	1494582762418
556	餃	1494582770807
560	視	1494582812795
562	饅	1494582839301
571	碗	1494582947647
572	萬	1494582953730
573	光	1494582963596
613	俄	1494583659148
617	喜	1494583678372
629	早	1494583937268
580	妹	1494583008101
581	弟	1494583015324
582	桃	1494583027850
584	蘋	1494583038492
641	風	1494584052030
645	克	1494584154033
587	半	1494583057026
648	里	1494584209681
851	但	1501484024974
854	易	1501487836127
657	信	1494584531126
857	覺	1501487864746
663	活	1494584637812
860	言	1501487895231
669	倆	1494584727988
672	授	1494584768148
675	芳	1494584821196
678	理	1494584853028
681	記	1494584903043
684	士	1494584922868
687	院	1494584959124
604	取	1494583188282
608	問	1494583258684
759	匪	1501481617772
762	句	1501481667764
768	天	1501481723195
794	序	1501482053418
796	謂	1501482097813
799	室	1501482159473
818	律	1501482505689
\.


--
-- Data for Name: hanzi_stroke_data; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY hanzi_stroke_data (hanzi_stroke_id, hanzi_stroke, page_number, created_date) FROM stdin;
1	營業員	一	1491448282654
5	發音	三	1492339814022
12	新臺幣	四	1493221094569
2	電子郵件	二	1492249841461
123	音	八	1494863393753
17	印尼盾	五	1494514575211
18	換	六	1494564677388
19	港幣	七	1494564710102
20	人民幣	八	1494564725903
21	歐元	九	1494564740406
22	雜志	十	1494564758066
23	瑪麗	十一	1494564770541
24	圖書館	十二	1494564794287
25	數	十三	1494564831838
26	臺灣	十四	1494564849584
27	繁體字	十五	1494564870974
28	警察	十六	1494564883752
29	號碼	十七	1494564902929
30	辦公	十八	1494564924838
31	職員	十九	1494564937997
32	手機	二十	1494564954452
33	房間	二十一	1494564970611
34	急救	二十二	1494564996133
35	規則	二十三	1494565011322
36	輕	二十四	1494565037422
37	酒	二十五	1494565104985
38	啤酒	二十五	1494565115612
39	留學生	二十六	1494565147466
40	消防局	一	1494565207094
41	緊急電話號碼	二	1494565290079
42	德國	三	1494565334543
43	韓國	四	1494565351097
44	張	五	1494565363417
45	西班牙	六	1494565395872
46	阿拉伯	七	1494565413761
47	菲警電話號碼	八	1494565446066
48	週	九	1494565481587
49	門	十	1494565495190
50	查號台	十一	1494565599505
51	樓	十二	1494565620312
52	筷子	十三	1494565660016
53	麥子	十四	1494565698631
54	麥	十四	1494565704226
55	麵條	十五	1494565723929
56	麵	十五	1494565766486
57	傘	十六	1494566084133
58	汽車	十七	1494566103293
59	天氣	十八	1494566142184
60	預報	十九	1494566162474
61	天氣預報	二十	1494566247009
62	名詞	二十一	1494566282231
63	動詞	二十二	1494566317384
64	定語	二十四	1494566373062
65	狀語	二十五	1494566481799
66	形容詞	二十三	1494566532100
67	詞典	二十六	1494566573499
68	湯	二十七	1494566592434
69	鷄	二十八	1494566631400
70	雞	二十八	1494566655526
71	餃子	二十九	1494566681204
72	蛋	三十	1494566698468
73	錄	三十一	1494566741815
74	腦	三十二	1494566810189
75	電視	三十三	1494566847530
76	些	三十四	1494567011520
77	饅頭	三十五	1494567046587
78	米飯	三十六	1494567092465
79	副詞	三十七	1494567140457
80	錄音機	三十八	1494567212642
81	主語	三十九	1494567248184
82	代詞	四十	1494567364634
83	碗	四十五	1494567417304
84	小姐	四十六	1494567448091
85	先生	四十七	1494567505352
86	妹妹	四十八	1494567543963
87	弟弟	四十九	1494567598833
88	桃子	五十	1494567624771
89	蘋果	五十一	1494567649497
90	還	五十二	1494567700052
91	半	五十三	1494567740091
92	拼音	五十四	1494567758699
93	白	五十五	1494567835523
94	練習	五十六	1494567866382
95	聲	五十七	1494567896412
96	課	五十八	1494567951586
97	聲調	五十九	1494568108398
98	變調	六十	1494568132382
99	馬	六十一	1494568243296
100	機	六十二	1494568299597
101	哥哥	六十三	1494568340626
102	忙	六十四	1494568364937
103	很	六十五	1494568376413
104	漢	六十六	1494568393865
105	漢字	六十六	1494568401767
106	取	六十七	1494568431463
107	法國	六十八	1494568449785
108	什麽	六十九	1494568522562
109	哪	七十	1494568575710
110	問	七十一	1494568593522
111	姓	七十二	1494568639010
112	學習	七十三	1494568660695
113	橘子	七十四	1494568699669
114	俄國	七十五	1494568727449
115	東	七十六	1494568742157
116	書	一	1494592266829
117	吳	二	1494592582529
118	永	三	1494592715976
119	敏	四	1494592929528
120	語音	五	1494862089733
121	生詞	六	1494862679023
122	詞	七	1494862975091
124	你	九	1494863561994
125	好	十	1494863808301
126	一	十一	1494863959901
127	大	十二	1494864069379
128	五	十三	1494864237847
129	口	十四	1494864338925
130	女	十五	1494950215528
131	漢語	十六	1494950503728
132	太	十七	1494950603017
133	難	十八	1494950831105
134	課文	十九	1494951015505
135	嗎	二十	1494951291769
136	爸爸	二十一	1494951423078
137	媽媽	二十二	1494951504962
138	他	二十三	1494951568215
139	她	二十四	1494951621449
140	不	二十五	1501656210861
141	八	二十六	1501656617182
142	語	二十七	1501657321538
143	男	二十八	1501657872055
144	局	二十九	1501658639943
145	郵	三十	1501659654682
146	國	三十一	1501660361372
147	韓	三十二	1501661270638
148	銀行	三十三	1501664416449
149	錢	三十四	1501665089943
150	見	三十五	1501665673132
151	英國	三十六	1501666482238
152	日本	三十七	1501667482156
153	對	三十八	1501667976434
154	九	三十九	1501668133154
\.


--
-- Data for Name: pinyin_data; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY pinyin_data (pinyin_id, pinyin) FROM stdin;
1	wǒ
\.


--
-- Name: sequence_book_and_stroke; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_book_and_stroke', 160, true);


--
-- Name: sequence_book_data; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_book_data', 56, true);


--
-- Name: sequence_group_and_hanzi; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_group_and_hanzi', 1, true);


--
-- Name: sequence_group_data; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_group_data', 1, true);


--
-- Name: sequence_hanzi_and_pinyin; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_hanzi_and_pinyin', 1, true);


--
-- Name: sequence_hanzi_data; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_hanzi_data', 867, true);


--
-- Name: sequence_hanzi_stroke_data; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_hanzi_stroke_data', 154, true);


--
-- Name: sequence_pinyin_data; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_pinyin_data', 1, true);


--
-- Name: sequence_user_and_hanzi; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_user_and_hanzi', 867, true);


--
-- Name: sequence_user_data; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_user_data', 1, true);


--
-- Data for Name: user_and_hanzi; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY user_and_hanzi (user_and_hanzi_id, user_id, hanzi_id) FROM stdin;
1	1	1
2	1	2
3	1	3
5	1	5
6	1	6
7	1	7
8	1	8
9	1	9
10	1	10
11	1	11
12	1	12
13	1	13
14	1	14
15	1	15
16	1	16
17	1	17
18	1	18
20	1	20
21	1	21
22	1	22
23	1	23
24	1	24
25	1	25
26	1	26
27	1	27
28	1	28
29	1	29
31	1	31
32	1	32
33	1	33
34	1	34
35	1	35
36	1	36
37	1	37
38	1	38
39	1	39
40	1	40
41	1	41
42	1	42
43	1	43
44	1	44
45	1	45
46	1	46
47	1	47
48	1	48
49	1	49
50	1	50
51	1	51
52	1	52
53	1	53
54	1	54
55	1	55
57	1	57
58	1	58
59	1	59
60	1	60
61	1	61
62	1	62
65	1	65
66	1	66
67	1	67
68	1	68
69	1	69
70	1	70
71	1	71
72	1	72
73	1	73
74	1	74
75	1	75
76	1	76
77	1	77
78	1	78
81	1	81
82	1	82
83	1	83
84	1	84
85	1	85
86	1	86
87	1	87
88	1	88
89	1	89
90	1	90
91	1	91
92	1	92
93	1	93
94	1	94
95	1	95
96	1	96
97	1	97
98	1	98
99	1	99
100	1	100
101	1	101
102	1	102
103	1	103
104	1	104
105	1	105
106	1	106
107	1	107
108	1	108
109	1	109
111	1	111
112	1	112
115	1	115
116	1	116
117	1	117
118	1	118
121	1	121
122	1	122
149	1	149
152	1	152
153	1	153
155	1	155
156	1	156
159	1	159
160	1	160
163	1	163
164	1	164
165	1	165
166	1	166
167	1	167
171	1	171
173	1	173
174	1	174
177	1	177
180	1	180
181	1	181
182	1	182
183	1	183
184	1	184
187	1	187
188	1	188
191	1	191
192	1	192
193	1	193
195	1	195
196	1	196
197	1	197
198	1	198
199	1	199
200	1	200
201	1	201
202	1	202
203	1	203
204	1	204
206	1	206
207	1	207
208	1	208
209	1	209
210	1	210
211	1	211
212	1	212
214	1	214
216	1	216
218	1	218
219	1	219
220	1	220
222	1	222
223	1	223
224	1	224
225	1	225
226	1	226
230	1	230
231	1	231
232	1	232
234	1	234
235	1	235
236	1	236
241	1	241
242	1	242
243	1	243
244	1	244
246	1	246
247	1	247
248	1	248
249	1	249
252	1	252
254	1	254
255	1	255
256	1	256
257	1	257
258	1	258
262	1	262
265	1	265
266	1	266
267	1	267
269	1	269
271	1	271
275	1	275
276	1	276
277	1	277
278	1	278
279	1	279
280	1	280
281	1	281
282	1	282
287	1	287
288	1	288
289	1	289
290	1	290
292	1	292
293	1	293
294	1	294
295	1	295
296	1	296
297	1	297
298	1	298
299	1	299
300	1	300
301	1	301
302	1	302
303	1	303
304	1	304
305	1	305
306	1	306
308	1	308
313	1	313
317	1	317
318	1	318
319	1	319
320	1	320
321	1	321
322	1	322
323	1	323
325	1	325
326	1	326
327	1	327
328	1	328
329	1	329
333	1	333
334	1	334
335	1	335
337	1	337
338	1	338
339	1	339
340	1	340
341	1	341
342	1	342
344	1	344
345	1	345
346	1	346
349	1	349
351	1	351
353	1	353
354	1	354
355	1	355
356	1	356
357	1	357
358	1	358
359	1	359
360	1	360
361	1	361
363	1	363
366	1	366
368	1	368
369	1	369
374	1	374
375	1	375
377	1	377
379	1	379
381	1	381
382	1	382
386	1	386
387	1	387
388	1	388
389	1	389
390	1	390
393	1	393
395	1	395
396	1	396
397	1	397
398	1	398
400	1	400
401	1	401
402	1	402
403	1	403
405	1	405
406	1	406
408	1	408
410	1	410
411	1	411
414	1	414
416	1	416
417	1	417
418	1	418
420	1	420
421	1	421
422	1	422
423	1	423
424	1	424
425	1	425
426	1	426
428	1	428
429	1	429
431	1	431
432	1	432
433	1	433
434	1	434
435	1	435
438	1	438
440	1	440
441	1	441
442	1	442
443	1	443
444	1	444
446	1	446
447	1	447
448	1	448
449	1	449
450	1	450
451	1	451
452	1	452
453	1	453
454	1	454
455	1	455
456	1	456
457	1	457
459	1	459
460	1	460
461	1	461
462	1	462
463	1	463
464	1	464
465	1	465
466	1	466
467	1	467
469	1	469
471	1	471
473	1	473
477	1	477
478	1	478
479	1	479
483	1	483
484	1	484
485	1	485
486	1	486
487	1	487
492	1	492
493	1	493
495	1	495
497	1	497
500	1	500
501	1	501
502	1	502
503	1	503
504	1	504
505	1	505
508	1	508
510	1	510
511	1	511
512	1	512
513	1	513
515	1	515
517	1	517
518	1	518
519	1	519
521	1	521
522	1	522
523	1	523
524	1	524
525	1	525
526	1	526
527	1	527
529	1	529
530	1	530
531	1	531
532	1	532
533	1	533
537	1	537
538	1	538
539	1	539
540	1	540
541	1	541
548	1	548
549	1	549
550	1	550
552	1	552
553	1	553
554	1	554
555	1	555
556	1	556
560	1	560
562	1	562
571	1	571
572	1	572
573	1	573
580	1	580
581	1	581
582	1	582
584	1	584
587	1	587
604	1	604
608	1	608
610	1	610
613	1	613
614	1	614
616	1	616
617	1	617
618	1	618
619	1	619
620	1	620
622	1	622
624	1	624
628	1	628
629	1	629
630	1	630
632	1	632
636	1	636
639	1	639
640	1	640
641	1	641
642	1	642
645	1	645
646	1	646
647	1	647
648	1	648
649	1	649
652	1	652
653	1	653
656	1	656
657	1	657
663	1	663
664	1	664
667	1	667
668	1	668
669	1	669
670	1	670
672	1	672
673	1	673
674	1	674
675	1	675
676	1	676
677	1	677
678	1	678
679	1	679
680	1	680
681	1	681
682	1	682
683	1	683
684	1	684
685	1	685
687	1	687
689	1	689
693	1	693
705	1	705
759	1	759
762	1	762
768	1	768
794	1	794
796	1	796
799	1	799
818	1	818
851	1	851
854	1	854
857	1	857
860	1	860
861	1	861
865	1	865
\.


--
-- Data for Name: user_data; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY user_data (user_id, username, passcode) FROM stdin;
1	admin	gototaiwan
\.


--
-- Name: book_and_stroke_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY book_and_stroke
    ADD CONSTRAINT book_and_stroke_pkey PRIMARY KEY (book_and_stroke_id);


--
-- Name: book_data_book_name_key; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY book_data
    ADD CONSTRAINT book_data_book_name_key UNIQUE (book_name);


--
-- Name: book_data_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY book_data
    ADD CONSTRAINT book_data_pkey PRIMARY KEY (book_id);


--
-- Name: group_and_hanzi_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY group_and_hanzi
    ADD CONSTRAINT group_and_hanzi_pkey PRIMARY KEY (group_and_hanzi_id);


--
-- Name: group_data_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY group_data
    ADD CONSTRAINT group_data_pkey PRIMARY KEY (group_id);


--
-- Name: hanzi_and_pinyin_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY hanzi_and_pinyin
    ADD CONSTRAINT hanzi_and_pinyin_pkey PRIMARY KEY (hanzi_and_pinyin_id);


--
-- Name: hanzi_data_hanzi_key; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY hanzi_data
    ADD CONSTRAINT hanzi_data_hanzi_key UNIQUE (hanzi);


--
-- Name: hanzi_data_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY hanzi_data
    ADD CONSTRAINT hanzi_data_pkey PRIMARY KEY (hanzi_id);


--
-- Name: hanzi_stroke_data_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY hanzi_stroke_data
    ADD CONSTRAINT hanzi_stroke_data_pkey PRIMARY KEY (hanzi_stroke_id);


--
-- Name: pinyin_data_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY pinyin_data
    ADD CONSTRAINT pinyin_data_pkey PRIMARY KEY (pinyin_id);


--
-- Name: user_and_hanzi_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY user_and_hanzi
    ADD CONSTRAINT user_and_hanzi_pkey PRIMARY KEY (user_and_hanzi_id);


--
-- Name: user_data_pkey; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY user_data
    ADD CONSTRAINT user_data_pkey PRIMARY KEY (user_id);


--
-- Name: user_data_username_key; Type: CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY user_data
    ADD CONSTRAINT user_data_username_key UNIQUE (username);


--
-- Name: fk_book_and_stroke_book_data; Type: FK CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY book_and_stroke
    ADD CONSTRAINT fk_book_and_stroke_book_data FOREIGN KEY (book_id) REFERENCES book_data(book_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_group_and_hanzi_group_data; Type: FK CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY group_and_hanzi
    ADD CONSTRAINT fk_group_and_hanzi_group_data FOREIGN KEY (group_id) REFERENCES group_data(group_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_group_and_hanzi_hanzi_data; Type: FK CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY group_and_hanzi
    ADD CONSTRAINT fk_group_and_hanzi_hanzi_data FOREIGN KEY (hanzi_id) REFERENCES hanzi_data(hanzi_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_hanzi_and_pinyin_hanzi_data; Type: FK CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY hanzi_and_pinyin
    ADD CONSTRAINT fk_hanzi_and_pinyin_hanzi_data FOREIGN KEY (hanzi_id) REFERENCES hanzi_data(hanzi_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_hanzi_and_pinyin_pinyin_data; Type: FK CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY hanzi_and_pinyin
    ADD CONSTRAINT fk_hanzi_and_pinyin_pinyin_data FOREIGN KEY (pinyin_id) REFERENCES pinyin_data(pinyin_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_user_and_hanzi_hanzi_data; Type: FK CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY user_and_hanzi
    ADD CONSTRAINT fk_user_and_hanzi_hanzi_data FOREIGN KEY (hanzi_id) REFERENCES hanzi_data(hanzi_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_user_and_hanzi_user_data; Type: FK CONSTRAINT; Schema: learnhanzi_schema; Owner: learnhanzi
--

ALTER TABLE ONLY user_and_hanzi
    ADD CONSTRAINT fk_user_and_hanzi_user_data FOREIGN KEY (user_id) REFERENCES user_data(user_id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


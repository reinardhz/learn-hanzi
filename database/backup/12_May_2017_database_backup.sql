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
\.


--
-- Data for Name: book_data; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY book_data (book_id, book_name) FROM stdin;
1	第一書
6	第二書
8	第三書
51	第四書
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
57	誰	1491638841741
58	水	1491638845785
59	華	1491638906164
60	話	1491638909907
61	個	1491638984850
62	要	1491638997800
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
111	週	1491641860018
112	兩	1491642699689
115	字	1491642965027
116	子	1491642968840
117	了	1491642972130
118	變	1491642977498
121	練	1491643038010
122	習	1491643043232
\.


--
-- Data for Name: hanzi_stroke_data; Type: TABLE DATA; Schema: learnhanzi_schema; Owner: learnhanzi
--

COPY hanzi_stroke_data (hanzi_stroke_id, hanzi_stroke, page_number, created_date) FROM stdin;
1	營業員	一	1491448282654
5	發音	三	1492339814022
12	新臺幣	四	1493221094569
2	電子郵件	二	1492249841461
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

SELECT pg_catalog.setval('sequence_book_and_stroke', 121, true);


--
-- Name: sequence_book_data; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_book_data', 53, true);


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

SELECT pg_catalog.setval('sequence_hanzi_data', 148, true);


--
-- Name: sequence_hanzi_stroke_data; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_hanzi_stroke_data', 115, true);


--
-- Name: sequence_pinyin_data; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_pinyin_data', 1, true);


--
-- Name: sequence_user_and_hanzi; Type: SEQUENCE SET; Schema: learnhanzi_schema; Owner: learnhanzi
--

SELECT pg_catalog.setval('sequence_user_and_hanzi', 148, true);


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


package com.reinard.learnhanzi.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * A class to save data from "book_data", "book_and_stroke", "hanzi_stroke_data" table, , then it will be converted into json.
 * 
 * This is the json this class represent: 
 * { "hanzi_stroke_data_all_book":[{"hanzi_stroke":"生詞", "book_name":"第三書", "page_number":"六"}, {"hanzi_stroke":"生詞", "book_name":"第三書", "page_number":"六十三"}] }
 * 
 */
public class AllHanziStrokeInAllBookName {
	
	private Hanzi_stroke_data_all_book[] hanzi_stroke_data_all_book;
	
	public AllHanziStrokeInAllBookName() {
		super();
	}
	
	public Hanzi_stroke_data_all_book[] getHanzi_stroke_data_all_book() {
		return hanzi_stroke_data_all_book;
	}

	public void setHanzi_stroke_data_all_book(Hanzi_stroke_data_all_book[] hanzi_stroke_data_all_book) {
		this.hanzi_stroke_data_all_book = hanzi_stroke_data_all_book;
	}

	public static void main(String[] args) throws Exception {
		//testing
		Hanzi_stroke_data_all_book data1 = new Hanzi_stroke_data_all_book();
		data1.setHanzi_stroke("生詞");
		data1.setBook_name("第三書");
		data1.setPage_number("六");
		
		Hanzi_stroke_data_all_book data2 = new Hanzi_stroke_data_all_book();
		data2.setHanzi_stroke("生詞");
		data2.setBook_name("第三書");
		data2.setPage_number("六十三");
		Hanzi_stroke_data_all_book[] array = {data1, data2};
		
		
		AllHanziStrokeInAllBookName allHanziStrokeInAllBookName = new AllHanziStrokeInAllBookName();
		allHanziStrokeInAllBookName.setHanzi_stroke_data_all_book(array);
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(allHanziStrokeInAllBookName);
		
		System.out.println(result);
	}

}

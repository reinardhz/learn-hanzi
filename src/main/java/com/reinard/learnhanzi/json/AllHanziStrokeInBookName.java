package com.reinard.learnhanzi.json;

/**
 * 
 * A class to save data from "book_data" and "hanzi_stroke_data" table, then it will be converted into json.
 * 
 * One book_name, has many hanzi_stroke in it.
 * 
 * This is the json this class represent: 
 * {"book_name":"第一書", "hanzi_stroke_data":[{"hanzi_stroke":"營業員", "page_number":"一", "created_date":"1491448282654"},{"hanzi_stroke":"電子郵件", "page_number":"二", "created_date":"1491448282654"},{"hanzi_stroke":"發音", "page_number":"三", "created_date":"1492339814022"}]}
 * 
 * @author reinard.santosa
 *
 */
public class AllHanziStrokeInBookName {
	
	private String book_name;

	private Hanzi_stroke_data[] hanzi_stroke_data;
	
	public AllHanziStrokeInBookName(){
		super();
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public Hanzi_stroke_data[] getHanzi_stroke_data() {
		return hanzi_stroke_data;
	}

	public void setHanzi_stroke_data(Hanzi_stroke_data[] hanzi_stroke_data) {
		this.hanzi_stroke_data = hanzi_stroke_data;
	}

	@Override
	public String toString() {
		return "ClassPojo [book_name = " + book_name + ", hanzi_stroke_data = " + hanzi_stroke_data + "]";
	}
}

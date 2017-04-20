package com.reinard.learnhanzi.json;

/**
 * 
 * A class to save data from "book_data" and "hanzi_stroke_data" table, then it
 * will be converted into json.
 * 
 * One book_name, has many hanzi_stroke in it.
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

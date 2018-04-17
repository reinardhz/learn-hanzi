package com.reinard.learnhanzi.json;

public class Hanzi_stroke_data_all_book {
	
	private String hanzi_stroke;
	private String book_name;
	private String page_number;
	
	public Hanzi_stroke_data_all_book() {
		super();
	}

	public String getHanzi_stroke() {
		return hanzi_stroke;
	}

	public void setHanzi_stroke(String hanzi_stroke) {
		this.hanzi_stroke = hanzi_stroke;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getPage_number() {
		return page_number;
	}

	public void setPage_number(String page_number) {
		this.page_number = page_number;
	}
	
	@Override
	public String toString(){
		//{"hanzi_stroke":"生詞", "book_name":"第三書", "page_number":"六"}
		return "{\"hanzi_stroke\":\""+getHanzi_stroke()+"\", \"book_name\":\""+getBook_name()+"\", \"page_number\":\""+getPage_number()+"\"}";
	}
	
}

package com.reinard.learnhanzi.json;

/**
 * Part of "AllHanziStrokeInBookName" class.
 * 
 * @author reinard.santosa
 *
 */
public class Hanzi_stroke_data {

	private String created_date;

	private String hanzi_stroke;

	public Hanzi_stroke_data() {
		super();
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getHanzi_stroke() {
		return hanzi_stroke;
	}

	public void setHanzi_stroke(String hanzi_stroke) {
		this.hanzi_stroke = hanzi_stroke;
	}

	@Override
	public String toString() {
		return "ClassPojo [created_date = " + created_date + ", hanzi_stroke = " + hanzi_stroke + "]";
	}
}

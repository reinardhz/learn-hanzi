package com.reinard.learnhanzi.json;

/**
 * A class to convert all data from "hanzi_data" table into json.
 * 
 * @author reinard.santosa
 */
public class HanziDataJson {
	
	private Hanzi_data[] hanzi_data;
	
	public HanziDataJson(){
		super();
	}

	public Hanzi_data[] getHanzi_data() {
		return hanzi_data;
	}

	public void setHanzi_data(Hanzi_data[] hanzi_data) {
		this.hanzi_data = hanzi_data;
	}

	@Override
	public String toString() {
		return "ClassPojo [hanzi_data = " + hanzi_data + "]";
	}
}
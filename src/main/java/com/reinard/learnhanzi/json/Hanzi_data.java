package com.reinard.learnhanzi.json;

/**
 * Part of HanziDataJson class.
 * 
 * @author reinard.santosa
 */
public class Hanzi_data {
	
	//private String hanzi_id;
	
	private String hanzi;
	
	private String created_date;
	
	public Hanzi_data(){
		super();
	}

	/*
	public String getHanzi_id() {
		return hanzi_id;
	}*/

	/*
	public void setHanzi_id(String hanzi_id) {
		this.hanzi_id = hanzi_id;
	}*/

	public String getHanzi() {
		return hanzi;
	}

	public void setHanzi(String hanzi) {
		this.hanzi = hanzi;
	}
	
	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	/*
	@Override
	public String toString(){
		//Example: {"hanzi_id":"1","hanzi":"我"}
		//return "{\"hanzi_id\":\"+1+\",\"+hanzi+\":\"+我+\"}";
		return "{\"+hanzi+\":\""+this.getHanzi()+"\"}";
	}*/
	
}

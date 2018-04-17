package com.reinard.learnhanzi.json;

/**
 * Part of "HanziDataJsonResponseObject" class.
 * 
 * @author reinard.santosa
 */
public class Hanzi_data {
	
	private String hanzi;
	
	private String created_date;
	
	public Hanzi_data(){
		super();
	}

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

	
	@Override
	public String toString(){
		//Example: {"hanzi_id":"1","hanzi":"我"}
		//return "{\"hanzi_id\":\"+1+\",\"+hanzi+\":\"+我+\"}";
		//Example: {"hanzi_data":[{"hanzi":"wo", "created_date":"2017-04-04 09:15"}]}
		return "{\"+hanzi+\":\""+this.getHanzi()+"\", \"created_date\":\"" +this.getCreated_date()+ "\"}";
	}
	
}

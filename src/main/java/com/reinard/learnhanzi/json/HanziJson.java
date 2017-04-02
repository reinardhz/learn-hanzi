package com.reinard.learnhanzi.json;

public class HanziJson {
	
	private String hanzi_id;
	
	private String hanzi;
	
	public HanziJson(){
		super();
	}

	public String getHanzi_id() {
		return hanzi_id;
	}

	public void setHanzi_id(String hanzi_id) {
		this.hanzi_id = hanzi_id;
	}

	public String getHanzi() {
		return hanzi;
	}

	public void setHanzi(String hanzi) {
		this.hanzi = hanzi;
	}
	
	@Override
	public String toString(){
		//Example: {"hanzi_id":"1","hanzi":"我"}
		return "{\"hanzi_id\":\"+1+\",\"+hanzi+\":\"+我+\"}";
	}
	
}

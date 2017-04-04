package com.reinard.learnhanzi.json;

/**
 * Bind the http request body (json) to this class.
 * 
 * @author reinard.santosa
 *
 */
public class SearchHanziJsonRequestObject {
	private String search_hanzi;
	
	public SearchHanziJsonRequestObject(){
		super();
	}

	public String getSearch_hanzi() {
		return search_hanzi;
	}

	public void setSearch_hanzi(String search_hanzi) {
		this.search_hanzi = search_hanzi;
	}

	@Override
	public String toString() {
		return "ClassPojo [search_hanzi = " + search_hanzi + "]";
	}
}

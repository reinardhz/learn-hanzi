package com.reinard.learnhanzi.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.io.Serializable;

public class HanziData implements Serializable{
	
	private BigDecimal id;
	private String hanzi;
	private Timestamp createdDate;
	
	public HanziData(){
		super();
	}
	
	public BigDecimal getId() {
		return id;
	}
	
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	public String getHanzi() {
		return hanzi;
	}
	
	public void setHanzi(String hanzi) {
		this.hanzi = hanzi;
	}
	
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	

}

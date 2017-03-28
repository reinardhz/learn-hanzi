package com.reinard.learnhanzi.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;
import javax.persistence.FetchType;
import java.util.Set;

/**
 * Entity class related with hanzi_data table.
 * 
 * @author Reinard Hizkia Santosa
 *
 */
@Entity
@Table(name="hanzi_data", schema="learnhanzi_schema")
public class HanziData implements Serializable{
	
	@Id
	@Column(name = "hanzi_id", nullable=false)
	private BigDecimal hanzi_id;
	
	@Column(name = "hanzi")
	private String hanzi;
	
	@Column(name = "created_date")
	private Timestamp created_date;
	
	public HanziData(){
		super();
	}

	public BigDecimal getHanzi_id() {
		return hanzi_id;
	}

	public void setHanzi_id(BigDecimal hanzi_id) {
		this.hanzi_id = hanzi_id;
	}

	public String getHanzi() {
		return hanzi;
	}

	public void setHanzi(String hanzi) {
		this.hanzi = hanzi;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
	
	@Override
	public String toString(){
		StringBuilder resultString = new StringBuilder();
		
		resultString.append("hanzi_id: " + this.getHanzi_id().toPlainString() + "\n");
		resultString.append("hanzi: " + this.getHanzi() + "\n");
		resultString.append("created_date: " + this.getCreated_date() + "\n\n");
		return resultString.toString();
	}

}

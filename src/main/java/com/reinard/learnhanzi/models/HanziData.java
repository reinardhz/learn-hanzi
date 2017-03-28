package com.reinard.learnhanzi.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity class related with hanzi_data table.
 * 
 * @author Reinard Hizkia Santosa
 *
 */
@Entity
@Table(name="hanzi_data", schema="learnhanzi_schema", uniqueConstraints= {@UniqueConstraint(columnNames={"hanzi"})} )
public class HanziData implements Serializable{
	
	private static final long serialVersionUID = 5350642211551400788L;

	@Id
	@SequenceGenerator(name="hanzi_sequence", schema ="learnhanzi_schema", sequenceName="sequence_hanzi_data", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="hanzi_sequence")
	@Column(name = "hanzi_id", nullable=false)
	private long hanzi_id;
	
	@Column(name = "hanzi")
	private String hanzi;
	
	@Column(name = "created_date")
	private Timestamp created_date;
	
	//TODO create "one to many" relatioship with entity that represent:
	//* user_and_hanzi table
	//* group_and_hanzi table
	//* hanzi_and_pinyin table
	
	public HanziData(){
		super();
	}

	public long getHanzi_id() {
		return hanzi_id;
	}

	public void setHanzi_id(long hanzi_id) {
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
		
		resultString.append("hanzi_id: " + this.getHanzi_id() + "\n");
		resultString.append("hanzi: " + this.getHanzi() + "\n");
		resultString.append("created_date: " + this.getCreated_date() + "\n\n");
		return resultString.toString();
	}

}

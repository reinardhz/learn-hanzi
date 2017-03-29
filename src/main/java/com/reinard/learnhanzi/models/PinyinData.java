package com.reinard.learnhanzi.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class related with pinyin_data table.
 * 
 * @author Reinard Hizkia Santosa
 *
 */
@Entity
@Table(name="pinyin_data", schema="learnhanzi_schema")
public class PinyinData implements Serializable{
	
	private static final long serialVersionUID = 4846985275732124920L;
	
	@Id
	@SequenceGenerator(name="seqPinyinData", schema ="learnhanzi_schema", sequenceName="sequence_hanzi_data", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqPinyinData")
	@Column(name = "pinyin_id", nullable=false)
	private long pinyin_id;
	
	@Column(name = "pinyin")
	private String pinyin;
	
	//TODO create "one to many" relationship with entity that represent:
	//* hanzi_and_pinyin table
	//* pronunciation_data table
	
	//TODO Create setter and getter
	
	public PinyinData(){
		super();
	}

}

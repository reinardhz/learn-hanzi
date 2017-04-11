package com.reinard.learnhanzi.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity class represents "hanzi_stroke_data" table.
 * 
 * @author Reinard Hizkia Santosa
 *
 */
//TODO finish this model
//TODO test this model
@Entity
@Table(name="hanzi_stroke_data", schema="learnhanzi_schema")
public class HanziStrokeData implements Serializable{

	private static final long serialVersionUID = -5594033397675642789L;
	
	@Id
	@SequenceGenerator(name="seqHanziStroke", schema ="learnhanzi_schema", sequenceName="sequence_hanzi_stroke_data", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqHanziStroke")
	@Column(name = "hanzi_stroke_id", nullable=false)
	private long hanzi_stroke_id;
	
	@Column(name = "hanzi_stroke")
	private String hanzi_stroke;
	
	@Column(name = "created_date")
	private long created_date;
	
	public HanziStrokeData(){
		
	}
	

}

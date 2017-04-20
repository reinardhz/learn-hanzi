package com.reinard.learnhanzi.models;

import java.io.Serializable;
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

/**
 * Entity class represents "hanzi_stroke_data" table. 
 * 
 * @author Reinard Hizkia Santosa
 *
 */
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
	
	//A variable to store Entity "BookAndStroke".
	//One instance of this class ("HanziStrokeData"), that represents one row of table "hanzi_stroke_data", exist in many "BookAndStroke" instance.
	//This is mapped by the variable "private HanziStrokeData hanziStrokeData;", that exist in class "BookAndStroke".
	//Note: FetchType.EAGER indicate that, when hibernate select data from "hanzi_stroke_data" table that mapped to this "HanziStrokeData" entity, 
	//the "Set<BookAndStroke>" instance is always exist in this "HanziStrokeData" instance, even after the session is closed.
	@OneToMany(mappedBy="hanziStrokeData", fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	private Set<BookAndStroke> bookAndStroke;
	
	public HanziStrokeData(){
		super();
	}

	public long getHanzi_stroke_id() {
		return hanzi_stroke_id;
	}

	public void setHanzi_stroke_id(long hanzi_stroke_id) {
		this.hanzi_stroke_id = hanzi_stroke_id;
	}

	public String getHanzi_stroke() {
		return hanzi_stroke;
	}

	public void setHanzi_stroke(String hanzi_stroke) {
		this.hanzi_stroke = hanzi_stroke;
	}

	public long getCreated_date() {
		return created_date;
	}

	public void setCreated_date(long created_date) {
		this.created_date = created_date;
	}

	public Set<BookAndStroke> getBookAndStroke() {
		return bookAndStroke;
	}

	public void setBookAndStroke(Set<BookAndStroke> bookAndStroke) {
		this.bookAndStroke = bookAndStroke;
	}
	
	@Override
	public String toString(){
		StringBuilder resultString = new StringBuilder();
		resultString.append("hanzi_stroke_id: " + this.getHanzi_stroke_id() + "\n");
		resultString.append("hanzi_stroke: " + this.getHanzi_stroke() + "\n");
		resultString.append("created_date: " + this.getCreated_date() + "\n\n");
		
		//comment this, because it could cause "java.lang.StackOverflowError"
		//resultString.append("Set<BookAndStroke>: " + this.getBookAndStroke() + "\n\n");
		return resultString.toString();
	}

}

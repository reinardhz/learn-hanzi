package com.reinard.learnhanzi.models;

import java.io.Serializable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class represents "book_and_stroke" table.
 * 
 * @author Reinard Hizkia Santosa
 *
 */

//TODO test this model
@Entity
@Table(name="book_and_stroke", schema="learnhanzi_schema")
public class BookAndStroke implements Serializable{

	private static final long serialVersionUID = -5259568191211083756L;
	
	@Id
	@SequenceGenerator(name="seqBookAndStroke", schema ="learnhanzi_schema", sequenceName="sequence_book_and_stroke", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqBookAndStroke")
	@Column(name = "book_and_stroke_id", nullable=false)
	private long book_and_stroke_id;
	
	//A variable to store Entity "BookData".
	//One instance of this class ("BookAndStroke") represents one row of table "book_and_stroke". 
	//Many different instance of this class ("BookAndStroke") exist in many "BookData" instance.
	//Note: FetchType.EAGER indicate that, when hibernate select data from "book_and_stroke" table that mapped to this "BookAndStroke" entity, 
	//the "BookData" instance is always exist in this "BookAndStroke" instance, even after the session is closed.
	@ManyToOne(fetch = FetchType.EAGER)
	//to make reference to table "book_data" using using entity "BookData", using foreign key "book_id".
	@JoinColumn(name = "book_id", nullable=false)
	private BookData bookData;
	
	//A variable to store Entity "HanziStrokeData".
	//One instance of this class ("BookAndStroke") represents one row of table "book_and_stroke". 
	//Many different instance of this class ("BookAndStroke") exist in many "HanziStrokeData" instance.
	//Note: FetchType.EAGER indicate that, when hibernate select data from "book_and_stroke" table that mapped to this "HanziStrokeData" entity, 
	//the "HanziStrokeData" instance is always exist in this "BookAndStroke" instance, even after the session is closed.
	@ManyToOne(fetch = FetchType.EAGER)
	//to make reference to table "hanzi_stroke_data" using using entity "BookData", using foreign key "hanzi_stroke_id".
	@JoinColumn(name = "hanzi_stroke_id", nullable=false)
	private HanziStrokeData hanziStrokeData;
	
	public BookAndStroke(){
		super();
	}

	public long getBook_and_stroke_id() {
		return book_and_stroke_id;
	}

	public void setBook_and_stroke_id(long book_and_stroke_id) {
		this.book_and_stroke_id = book_and_stroke_id;
	}

	public BookData getBookData() {
		return bookData;
	}

	public void setBookData(BookData bookData) {
		this.bookData = bookData;
	}

	public HanziStrokeData getHanziStrokeData() {
		return hanziStrokeData;
	}

	public void setHanziStrokeData(HanziStrokeData hanziStrokeData) {
		this.hanziStrokeData = hanziStrokeData;
	}
	
	@Override
	public String toString(){
		StringBuilder resultString = new StringBuilder();
		
		resultString.append("BookData: " + this.getBookData() + "\n");
		resultString.append("HanziStrokeData: " + this.getHanziStrokeData() + "\n\n");
		
		return resultString.toString();
		
	}
	
	
}

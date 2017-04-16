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
 * Entity class represents "book_data" table. This model is already tested (OK).
 * 
 * @author Reinard Hizkia Santosa
 *
 */
@Entity
@Table(name="book_data", schema="learnhanzi_schema", uniqueConstraints= {@UniqueConstraint(columnNames={"book_name"})})
public class BookData implements Serializable{
	
	
	private static final long serialVersionUID = -866504476293272329L;
	
	@Id
	@SequenceGenerator(name="seqBookData", schema ="learnhanzi_schema", sequenceName="sequence_book_data", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqBookData")
	@Column(name = "book_id", nullable=false)
	private long book_id;
	
	@Column(name = "book_name")
	private String book_name;
	
	//A variable to store Entity "BookAndStroke".
	//One instance of this class ("BookData"), that represents one row of table "book_data", exist in many "BookAndStroke" object.
	//This is mapped by the variable "private BookData bookData;", that exist in class "BookAndStroke".
	//Note: FetchType.EAGER indicate that, when hibernate select data from "book_data" table that mapped to this "BookData" entity, 
	//the "Set<BookAndStroke>" instance is always exist in this "BookData" instance, even after the session is closed.
	@OneToMany(mappedBy="bookData", fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	private Set<BookAndStroke> bookAndStroke;
	
	public BookData(){
		super();
	}

	public long getBook_id() {
		return book_id;
	}

	public void setBook_id(long book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
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
		resultString.append("book_id: " + this.getBook_id() + "\n");
		resultString.append("book_name: " + this.getBook_name() + "\n\n");
		
		//comment this, because it could cause "java.lang.StackOverflowError"
		//resultString.append("Set<BookAndStroke>: " + this.getBookAndStroke() + "\n\n");
		return resultString.toString();
	}
	
}

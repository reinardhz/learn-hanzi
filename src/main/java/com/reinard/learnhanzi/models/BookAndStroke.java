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
//TODO finish this entity
//TODO test this model
@Entity
@Table(name="book_and_stroke", schema="learnhanzi_schema")
public class BookAndStroke implements Serializable{

	private static final long serialVersionUID = -5259568191211083756L;
	
	@Id
	@SequenceGenerator(name="seqBookAndStroke", schema ="learnhanzi_schema", sequenceName="sequence_book_and_stroke", allocationSize=1)
	private long book_and_stroke_id;
	
	//to make relations to entity "BookData", using foreign key "book_id"
	
	
	//to make relations to entity "HanziStrokeData", using foreign key "hanzi_stroke_id"

}

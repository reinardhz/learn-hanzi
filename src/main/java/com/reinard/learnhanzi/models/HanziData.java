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
	@SequenceGenerator(name="seqHanziData", schema ="learnhanzi_schema", sequenceName="sequence_hanzi_data", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqHanziData")
	@Column(name = "hanzi_id", nullable=false)
	private long hanzi_id;
	
	@Column(name = "hanzi")
	private String hanzi;
	
	@Column(name = "created_date")
	private Timestamp created_date;
	
	//A variable to store Entity UserAndHanzi.
	//One object of "HanziData" (one row of table "hanzi_data"), exist in many "UserAndHanzi" object.
	//This is mapped by the variable "private HanziData hanziData;", that exist in class "UserAndHanzi".
	@OneToMany(mappedBy="hanziData", fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	private Set<UserAndHanzi> userAndHanzi;
	
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

	public Set<UserAndHanzi> getUserAndHanzi() {
		return userAndHanzi;
	}

	public void setUserAndHanzi(Set<UserAndHanzi> userAndHanzi) {
		this.userAndHanzi = userAndHanzi;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString(){
		StringBuilder resultString = new StringBuilder();
		
		resultString.append("hanzi_id: " + this.getHanzi_id() + "\n");
		resultString.append("hanzi: " + this.getHanzi() + "\n");
		resultString.append("created_date: " + this.getCreated_date() + "\n");
		//resultString.append("Set<UserAndHanzi>: " + this.getUserAndHanzi() + "\n\n");
		return resultString.toString();
	}

}

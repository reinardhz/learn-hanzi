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
 * Entity class represents "user_and_hanzi" table.
 * 
 * @author reinard.santosa
 *
 */
//@Entity
//@Table(name="user_and_hanzi", schema="learnhanzi_schema")
public class UserAndHanzi implements Serializable{

	private static final long serialVersionUID = 256488880461687037L;
	
	//@Id
	//@SequenceGenerator(name="seqUserAndHanzi", schema ="learnhanzi_schema", sequenceName="sequence_user_and_hanzi", allocationSize=1)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqUserAndHanzi")
	//@Column(name = "user_and_hanzi_id", nullable=false)
	private long user_and_hanzi_id;
	
	//to make relations to entity "UserData", using foreign key "user_id".
	//Many different instance of this class (UserAndHanzi), have same "UserData" object.
	//Note: FetchType.EAGER indicate that, when hibernate select data from "user_and_hanzi" table that mapped to this "UserAndHanzi" entity, 
	//the "UserData" object is always exist in UserAndHanzi object, even after the session is closed.
	//@ManyToOne(fetch = FetchType.EAGER)
	//@JoinColumn(name="user_id", nullable=false)
	private UserData userData;
	
	//to make relations to entity "HanziData", using foreign key "hanzi_id".
	//Many different instance of this class (UserAndHanzi), have same "HanziData" object.
	//Note: FetchType.EAGER indicate that, when hibernate select data from "user_and_hanzi" table that mapped to this "UserAndHanzi" entity, 
	//the "HanziData" object is still exist in UserAndHanzi object, even after the session is closed.
	//@ManyToOne(fetch = FetchType.EAGER)
	//@JoinColumn(name="hanzi_id", nullable=false)
	private HanziData hanziData;
	
	public UserAndHanzi(){
		super();
	}

	public long getUser_and_hanzi_id() {
		return user_and_hanzi_id;
	}

	public void setUser_and_hanzi_id(long user_and_hanzi_id) {
		this.user_and_hanzi_id = user_and_hanzi_id;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public HanziData getHanziData() {
		return hanziData;
	}

	public void setHanziData(HanziData hanziData) {
		this.hanziData = hanziData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString(){
		StringBuilder resultString = new StringBuilder();
		resultString.append("user_and_hanzi_id: " + this.getUser_and_hanzi_id() + "\n");
		resultString.append("userData: " + this.getUserData() + "\n");
		resultString.append("hanziData: " + this.getHanziData() + "\n\n");
		return resultString.toString();
	}
	

}

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
import javax.persistence.UniqueConstraint;
/**
 * Entity class related with user_data table.
 * 
 * @author Reinard Hizkia Santosa
 */
//TODO make doc about fetch type eager
@Entity
@Table(name="user_data", schema="learnhanzi_schema", uniqueConstraints= {@UniqueConstraint(columnNames={"username"})} )
public class UserData implements Serializable{

	private static final long serialVersionUID = 4490640466302336521L;

	@Id
	@SequenceGenerator(name="seqUserData", schema ="learnhanzi_schema", sequenceName="sequence_user_data", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqUserData")
	@Column(name = "user_id", nullable=false)
	private long user_id;
	
	@Column(name = "username", length=50)
	private String username;
	
	@Column(name = "passcode", length=100)
	private String passcode;
	
	//A variable to store Entity "UserAndHanzi".
	//One object of "UserData" (one row of table "user_data"), exist in many "UserAndHanzi" object.
	//This is mapped by the variable "private UserData userData;", that exist in class "UserAndHanzi".
	@OneToMany(mappedBy="userData", fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	private Set<UserAndHanzi> userAndHanzi;

	public UserData() {
		super();
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
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
		resultString.append("user_id: " + this.getUser_id() + "\n");
		resultString.append("username: " + this.getUsername() + "\n");
		resultString.append("passcode: " + this.getPasscode() + "\n");
		
		//comment this, because it could cause "java.lang.StackOverflowError"
		//resultString.append("Set<UserAndHanzi>: " + this.getUserAndHanzi() + "\n\n");
		return resultString.toString();
	}
	
	
}

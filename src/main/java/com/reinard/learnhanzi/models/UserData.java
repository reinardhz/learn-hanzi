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
 * Entity class related with user_data table.
 * 
 * @author Reinard Hizkia Santosa
 *
 */
@Entity
@Table(name="user_data", schema="learnhanzi_schema", uniqueConstraints= {@UniqueConstraint(columnNames={"username"})} )
public class UserData implements Serializable{

	@Id
	@SequenceGenerator(name="seqUserData", schema ="learnhanzi_schema", sequenceName="sequence_user_data", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqUserData")
	@Column(name = "user_id", nullable=false)
	private long user_id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "passcode")
	private String passcode;
	
	private Set<UserAndHanzi> 
	
}

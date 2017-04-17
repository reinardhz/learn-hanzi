package com.reinard.learnhanzi.daos;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.reinard.learnhanzi.models.BookAndStroke;

/**
 * A class to provide test to HomeController
 * @author reinard.santosa
 *
 */
public class ExceptionTest {
	
	public int getNumber(){
		int[] arrayInt = {1};
		//java.lang.ArrayIndexOutOfBoundsException here.
		return arrayInt[999];
	}
	
	private String test() throws Exception{
		try{
			getNumber();
		}catch(Exception e){
			throw e;
		}finally {
			return "Bad practice to put \"return\" in \"finally\" block, because the \"throw\" syntax is never executed.";
		}
	}
	
	public static void main1(String[] args){
		ExceptionTest exceptionTest = new ExceptionTest();
		try {
			exceptionTest.test();
			System.out.println("No Exception here, but actually Exception occurs.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String generateUUIDFront(){
		String uuid = UUID.randomUUID().toString();
		//get the first 8 digit:
		String uuid_new = uuid.substring(0, 8);
		return uuid_new;
		
	}
	
	public static String generateUUIDBack(){
		String uuid = UUID.randomUUID().toString();
		//get the last 8 digit:
		String uuid_new = uuid.substring(28);
		return uuid_new;
		
	}

	
	public static void main(String[] args) throws Exception{
		Set<String> setOfUUID = new HashSet<>();
		for(int i=0; i<10000000; ++i){
			String uuid = UUID.randomUUID().toString();
			setOfUUID.add(uuid);
		}
		
		System.out.println(setOfUUID.size());
		
		if(setOfUUID.size()<10000000){
			System.out.println("There is the same generated number of UUID !");
		}
		//System.out.println(setOfUUID.size());
		
		//String uuid;
		//for(int i =0; i<10000; ++i){
		//long ts = UUID.randomUUID().timestamp();
		//System.out.println(ts);
		//}
	}

}

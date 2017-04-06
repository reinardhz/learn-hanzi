package com.reinard.learnhanzi.controllers;


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
	
	public static void main(String[] args){
		ExceptionTest exceptionTest = new ExceptionTest();
		try {
			exceptionTest.test();
			System.out.println("No Exception here, but actually Exception occurs.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

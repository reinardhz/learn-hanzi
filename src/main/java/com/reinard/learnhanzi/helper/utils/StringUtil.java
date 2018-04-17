package com.reinard.learnhanzi.helper.utils;

import java.util.Locale;

/**
 * A class that provides many useful operations for String object.
 * 
 * @author reinard.santosa
 *
 */
public class StringUtil {
	
	/**
	 * To remove all white space from the input String, including: DEL, ESC, TAB, Space character.
	 * 
	 * @param input - The input String.
	 * @return The new String with whitespace removed.
	 */
	public static String removeWhiteSpaceFrom(String input){
		//replace unicode characters from u+0000 to u+0020 with ""
		//replace unicode DEL character u+007F with ""
		String output = input.replaceAll("[\u0000-\u0032\u007F]","");
		return output;
	}
	
	/**
	 * To check whether the input String is null.
	 * 
	 * @param input - The input String.
	 * @return True if the input String is null, otherwise return false.
	 */
	public static boolean isNull(String input){
		boolean isNull = (input == null) ? true : false;
		return isNull;
	}
	
	/**
	 * To check whether the input String is an empty String or not.
	 * 
	 * @param input - The input String.
	 * @return True if the input String is empty, otherwise return false.
	 */
	public static boolean isEmpty(String input){
		String withoutWhiteSpace = removeWhiteSpaceFrom(input);
		boolean isEmpty = (withoutWhiteSpace.length() == 0) ? true : false;
		return isEmpty;
	}
	
	/**
	 * To convert String to lower case String.
	 * 
	 * @param input - The input String, could be alphanumeric.
	 * @return String - The new String, converted to lower case String.
	 */
	public static String toLowerCase(String input){
		String output = input.toLowerCase(Locale.ENGLISH);
		return output;
	}
	

}

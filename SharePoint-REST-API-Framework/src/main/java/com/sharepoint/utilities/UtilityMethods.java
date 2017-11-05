/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since October-2017
 * @version 1.0
 */
package com.sharepoint.utilities;

public class UtilityMethods {

	public static boolean checkNullOrEmpty(Object input) {
		return input != null && !String.valueOf(input).trim().isEmpty();
	}
}

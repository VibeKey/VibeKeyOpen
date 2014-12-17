package core_objects_abstract;

import java.util.HashMap;

/**
 * A core object for songs.
 * 
 * @author Rose Reatherford
 * @date Created: 12/17/2014
 */
public class Song {
	// Constants //
	/** Constant for a file path. */
	public static String FILEPATH = "filepath";
	/** Constant for a song length. */
	public static String LENGTH = "length";
	/** Constant for a song id. */
	public static String ID = "id";
	
	// Local Information //
	/** Stores other song information locally. */
	private HashMap<String, String> information;
	
	/**
	 * Gets the file path of a song.
	 * @param The parameter to get, should be a constant.
	 * @return The file path of a song.
	 */
	public String getParameter(String parameter) {
		return information.get(parameter);
	}
	
	public void addParameter(String parameter, String value) {
		
	}
}

package core_objects_abstract;

import java.io.BufferedInputStream;
import java.util.HashMap;

/**
 * A core object for songs.
 * 
 * @author Rose Reatherford
 * @date Created: 12/17/2014
 */
public class Song implements Comparable<Song> {
	// Constants //
	/** Constant for a file path. */
	public static String FILEPATH = "filepath";
	/** Constant for a song id. */
	public static String ID = "id";
	/** Constant for a song id. */
	public static String GENRE = "genre";
	/** Constant for a song id. */
	public static String ARTIST = "artist";
	/** Constant for a song id. */
	public static String ALBUM = "album";
	
	// Local Information //
	/** Stores other song information locally. */
	private HashMap<String, String> information;
	/** Size of the song, in bytes. */
	private int size;
	/** The length of the song, in second. */
	private int length;
	/** The song buffer. */
	private byte[] buffer;
	/** The priority of this song to buffer. */
	private int priority;
	
	/**
	 * A constructor that creates a basic song.
	 * @param filepath The file path of the song.
	 * @param length The length of the song in total seconds.
	 * @param id The id of the song.
	 */
	public Song (String filepath, int length, String id, int size) {
		this.information.put(FILEPATH, filepath);
		this.information.put(ID, id);
		this.length = length;
		this.size = size;
	}
	
	public void beginBuffer() {
		buffer = new byte[size];
		
		// Request for the PrimaryManager to buffer it.
	}
	
	/**
	 * Gets the database id of this song.
	 * @return The id.
	 */
	public String getId() {
		return information.get(ID);
	}
	
	/**
	 * Gets the file path for this song.
	 * @return The file path.
	 */
	public String getFilePath() {
		return information.get(FILEPATH);
	}
	
	/**
	 * Returns the length of this song.
	 * @return Returns the total seconds of this song.
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Gets the file path of a song.
	 * @param parameter The parameter to get, should be a constant.
	 * @return The file path of a song, returns null if the parameter
	 * 				does not exist.
	 */
	public String getParameter(String parameter) {
		return information.get(parameter);
	}
	
	/**
	 * Adds a new parameter.
	 * @param parameter The name of the parameter, should be a constant.
	 * @param value The value for this parameter, for this song.
	 */
	public void addParameter(String parameter, String value) {
		information.put(parameter, value);
	}

	@Override
	public int compareTo(Song song) {
		return this.priority - song.priority;
	}
}

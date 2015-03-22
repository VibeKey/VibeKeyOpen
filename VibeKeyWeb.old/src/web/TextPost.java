package web;

import java.util.Date;

/**
 * This class stores information for a message that has been posted to a chat room
 * @author robinsat
 *
 */
public class TextPost {

	/* The username of the poster */
	private String posterName;
	/* The time that the post was made */
	private long postTime;
	/* The content of the text post */
	private String postContent;
	
	/**
	 * Constructor
	 * @param user The username of the poster
	 * @param content The content of the post
	 */
	public TextPost(String user, String content) {
		this(user, new Date().getTime(), content);
	}
	
	/**
	 * Constructor
	 * @param user The username of the poster
	 * @param time The timestamp of the post
	 * @param content The content of the post
	 */
	public TextPost(String user, long time, String content) {
		posterName = user;
		postTime = time;
		postContent = content;
	}
	
	/**
	 * Gets the username of the poster
	 * @return the username of the poster
	 */
	public String getPosterName() { return posterName; }
	
	/**
	 * Gets the timestamp of the post
	 * @return the timestamp of the post
	 */
	public long getPostTime() { return postTime; }
	
	/**
	 * Gets the content of the post
	 * @return the content of the post
	 */
	public String getPostContent() { return postContent; }
	
	/**
	 * Sets the username of the poster
	 * @param name the username of the poster
	 */
	public void setPosterName(String name) { posterName = name; }
	
	/**
	 * Sets the timestamp of the post
	 * @param name the timestamp of the post
	 */
	public void setPostTime(long time) { postTime = time; }
	
	/**
	 * Sets content of the post
	 * @param content the content 
	 */
	public void setPostContent(String content) { postContent = content; }
}


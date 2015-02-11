package web;

import java.util.LinkedList;
import java.util.Queue;

public class ChatRoom {
	
	/* By default, the chatroom will only keep track of the most recent 50 messages */
	private final int DEFAULT_SIZE = 50;
	
	/* The number of messages that this chatroom will store */
	private int messagesToKeep;
	/* The posts that have been made to this chatroom */
	private Queue<TextPost> posts;
	
	/**
	 * Default Constructor
	 */
	public ChatRoom() {
		messagesToKeep = DEFAULT_SIZE;
		posts = new LinkedList<TextPost>();
	}
	
	/**
	 * Constructor
	 * @param numToKeep The number of posts that this chatbox will hold
	 */
	public ChatRoom(int numToKeep) {
		messagesToKeep = numToKeep;
		posts = new LinkedList<TextPost>();
	}
	
	/**
	 * Gets all of the posts stored in this chatroom
	 * @return all of the posts stored in this chatroom
	 */
	public Iterable<TextPost> getPosts() {
		return posts;
	}
	
	/**
	 * Adds a new post to the chatbox
	 * @param user The username of the poster
	 * @param postContent The content of the post
	 */
	public void addPost(String user, String postContent) {
		addPost(new TextPost(user, postContent));
	}
	
	/**
	 * Adds a new post to the chatbox
	 * @param user The username of the poster
	 * @param time The time that the post was created
	 * @param postContent The content of the post
	 */
	public void addPost(String user, long time, String postContent) {
		addPost(new TextPost(user, time, postContent));
	}
	
	/**
	 * Adds a new post to the chatbox
	 * @param newPost the post to add
	 */
	public void addPost(TextPost newPost) {
		posts.offer(newPost);
		
		//Clear out old posts
		if(posts.size() > messagesToKeep) {
			posts.poll();
		}
	}

}

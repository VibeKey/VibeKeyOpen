package web;

public class Tab {
	/* Holds the ID of this tab */
	private String tabId;
	/* Holds the text for the webpage to display on this tab */
	private String displayText;
	/* Holds the image for the webpage to display on this tab, if applicable */
	private String displayImage;
	/* Holds the URL for the content this tab displays */
	private String contentUrl; 
	/* By default, the tab's content is displayed in the tab pane. If fullPage == true, then the
	 * content should display in the page content area */
	private boolean fullPage;
	
	/**
	 * Constructor
	 * @param id = the id to set for this tab
	 * @param text = the display text to set for this tab
	 * @param url = the content url to set for this tab
	 */
	public Tab(String id, String text, String url)
	{
		this(id, text, null, url, false);
	}
	
	/**
	 * Constructor
	 * @param id = the id to set for this tab
	 * @param text = the display text to set for this tab
	 * @param image = the display image to set for this tab
	 * @param url = the content url to set for this tab
	 */
	public Tab(String id, String text, String image, String url)
	{
		this(id, text, image, url, false);
	}
	
	/**
	 * Constructor
	 * @param id = the id to set for this tab
	 * @param text = the display text to set for this tab
	 * @param url = the display image to set for this tab
	 * @param full = whether the content should be displayed as a full page rather than just a tab
	 */
	public Tab(String id, String text, String url, boolean full)
	{
		this(id, text, null, url, full);
	}
	
	/**
	 * Constructor
	 * @param id = the id to set for this tab
	 * @param text = the display text to set for this tab
	 * @param image = the display image to set for this tab
	 * @param url = the content url to set for this tab
	 * @param full = whether the content should be displayed as a full page rather than just a tab
	 */
	public Tab(String id, String text, String image, String url, boolean full)
	{
		displayText = text;
		displayImage = image;
		contentUrl = url;
		fullPage = full;
	}
	
	/**
	 * Getter method for the tab ID
	 * @return the tab ID
	 */
	public String getId() { return tabId; }
	
	/**
	 * Setter method for the tab ID
	 * @param  id = the id to set
	 */
	public void setId(String id) { tabId = id; }
	
	/**
	 * Getter method for the display text
	 * @return the display text
	 */
	public String getDisplayText() { return displayText; }
	
	/**
	 * Setter method for the display text
	 * @param text = the display text to set
	 */
	public void setDisplayText(String text) { displayText = text; }
	
	/**
	 * Getter method for the display image
	 * @return the display image
	 */
	public String getDisplayTextImage() { return displayImage; }
	
	/**
	 * Setter method for the display image
	 * @param text = the display image to set
	 */
	public void setDisplayImage(String image) { displayImage = image; }
	
	/**
	 * Getter method for the content URL
	 * @return the content URL
	 */
	public String getContentUrl() { return contentUrl; }
	
	/**
	 * Setter method for the content URL
	 * @param text = the URL to set
	 */
	public void setContentUrl(String url) { contentUrl = url; }
	
	/**
	 * Getter method for the fullPage field
	 * @return whether the content is displayed as a full page
	 */
	public boolean isFullPage() { return fullPage; }
	
	/**
	 * Setter method for the fullPage field
	 * @param full = whether the content is displayed as a full page
	 */
	public void setFullPage(boolean full) { fullPage = full; }
	
}


package vibekey.schedule;
import java.util.ArrayList;
import java.util.Date;

import vibekey.filter.Filter;
import vibekey.picker.NoPickException;
import vibekey.picker.Picker;
import vibekey.song.Song;

public class ScheduleItem{
	
	
	public static final int PLAYMODE_NONE = 0;
	public static final int PLAYMODE_STRICT = 1;
	public static final int PLAYMODE_LIVEDJ = 2;
	private int playMode;
	
	
	public static final int REPEAT_NONE = 0;
	public static final int REPEAT_DAILY = 1;
	public static final int REPEAT_WEEKLY = 2;
	public static final int REPEAT_MONTHLY = 3;
	private int repeatMode;
	
	private Date startTime;
	private Date endTime;
	private String DJName;
	private String eventName;
	private String description;
	
	private Filter filter;
	
	public ScheduleItem(){
		
	}

	public ScheduleItem(int playMode, int repeatMode, Date startTime, Date endTime, String DJName, Filter filter) {
		super();
		this.playMode = playMode;
		this.repeatMode = repeatMode;
		this.startTime = startTime;
		this.endTime = endTime;
		this.DJName = DJName;
		this.filter=filter;
	}
	
	/* with eventName and description for further development
		
	public ScheduleItem(int playMode, int repeatMode, Date startTime, Date endTime, String DJName, String eventName, String description, String genre, String playlist) {
		super();
		this.playMode = playMode;
		this.repeatMode = repeatMode;
		this.startTime = startTime;
		this.endTime = endTime;
		this.DJName = DJName;
		this.eventName = eventName;
		this.description = description;
		this.genre = genre;
		this.playlist = playlist;
	}
	*/


	public int getPlayMode() {
		return playMode;
	}
	
	public boolean isActive() {
		Date now = new Date(System.currentTimeMillis());
		return now.before(endTime) && now.after(startTime);
	}

	public void setPlayMode(int playMode) {
		this.playMode = playMode;
	}

	public int getRepeatMode() {
		return repeatMode;
	}

	public void setRepeatMode(int repeatMode) {
		this.repeatMode = repeatMode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDJName() {
		return DJName;
	}
	
	//I think we should delete a event instead of allowed to setDJName
	public void setDJName(String DJName) {
		this.DJName = DJName;
	}
	
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Filter getFilter(){
		return filter;
	}
	
	public void setFilter(Filter f){
		this.filter = f;
	}
	
	public Song getSong(ArrayList<Song> allSongs, Picker picker) throws NoPickException {
		ArrayList<Song> filteredSongs = this.filter.filter(allSongs);
		return picker.getSong(filteredSongs);
	}
}

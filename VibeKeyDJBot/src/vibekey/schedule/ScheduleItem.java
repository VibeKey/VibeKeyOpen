package vibekey.schedule;
import java.util.Date;

import vibekey.song.SongList;

public class ScheduleItem extends SongList{
	
	
	public static final int PLAYMODE_NONE = 0;
	public static final int PLAYMODE_GENRE = 1;
	public static final int PLAYMODE_PLAYLIST = 2;
	public static final int PLAYMODE_LIVEDJ = 3;
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
	private String genre; //ignored if not in genre play mode
	private String playlist; //ignored if not in playlist play mode
	
	public ScheduleItem(){
		
	}

	public ScheduleItem(int playMode, int repeatMode, Date startTime, Date endTime, String DJName, String genre, String playlist) {
		super();
		this.playMode = playMode;
		this.repeatMode = repeatMode;
		this.startTime = startTime;
		this.endTime = endTime;
		this.DJName = DJName;
		this.genre = genre;
		this.playlist = playlist;
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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPlaylist() {
		return playlist;
	}

	public void setPlaylist(String playlist) {
		this.playlist = playlist;
	}
	
}

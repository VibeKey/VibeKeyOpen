import java.util.ArrayList;
import java.util.Date;

public class PlaySchedule {
	ArrayList<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();
	
	public PlaySchedule(){
		FirebaseCommunicator.loadSchedule(scheduleItems);
	}
	
	
	
	public void addToSchedule(ScheduleItem newScheduleItem){
		for(ScheduleItem scheduleItem : scheduleItems){
			if(!((newScheduleItem.getStartTime().after(scheduleItem.getStartTime()) &&
					newScheduleItem.getStartTime().before(scheduleItem.getEndTime())) ||
					(newScheduleItem.getEndTime().after(scheduleItem.getStartTime()) &&
					newScheduleItem.getEndTime().before(scheduleItem.getEndTime())))){ //if times are not overlapping
				scheduleItems.add(newScheduleItem);
				break;
			}
		}
	}
	

	public ScheduleItem getCurScheduleItem(){
		Date curTime = new Date();
		for(ScheduleItem scheduleItem : scheduleItems){
			if(scheduleItem.getStartTime().before(curTime) && scheduleItem.getEndTime().after(curTime)){ //if this schedule item is happening now
				return scheduleItem;
			}
		}
		return null;
	}
	
	public int getCurPlayMode(){
		ScheduleItem curScheduleItem = getCurScheduleItem();
		if(curScheduleItem == null){
			return 0;
		}else{
			return curScheduleItem.getPlayMode();
		}
	}
	
	public String getCurGenre(){
		ScheduleItem curScheduleItem = getCurScheduleItem();
		if(curScheduleItem == null){
			return "";
		}else{
			return curScheduleItem.getGenre();
		}
	}
	
	public String getCurPlaylist(){
		ScheduleItem curScheduleItem = getCurScheduleItem();
		if(curScheduleItem == null){
			return "";
		}else{
			return curScheduleItem.getPlaylist();
		}
	}
}

package vibekey.schedule;
import java.util.ArrayList;

import vibekey.firebase.FirebaseCommunicator;
import vibekey.picker.NoPickException;
import vibekey.picker.Picker;
import vibekey.picker.VotePicker;
import vibekey.song.Song;

public class PlaySchedule{
	public ArrayList<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();
	
	Picker curPicker;
	
	public PlaySchedule(){
		FirebaseCommunicator.loadSchedule(scheduleItems);
		curPicker = new VotePicker();
	}
	
	
	
	public void addToSchedule(ScheduleItem newScheduleItem){
		for(ScheduleItem scheduleItem : scheduleItems){
			if(((newScheduleItem.getStartTime().after(scheduleItem.getStartTime()) &&
					newScheduleItem.getStartTime().before(scheduleItem.getEndTime())) ||
					(newScheduleItem.getEndTime().after(scheduleItem.getStartTime()) &&
					newScheduleItem.getEndTime().before(scheduleItem.getEndTime())))){ //if times are overlapping
				break;
			}
		}
		scheduleItems.add(newScheduleItem);
		this.pushToFirebase();
	}
	
	public void clearSchedule(){
		this.scheduleItems.clear();
	}
	
	public Song getSong(ArrayList<Song> allSongs) throws NoPickException{
		for(ScheduleItem scheduleItem : scheduleItems){
			if(scheduleItem.isActive()){
				try {
					return scheduleItem.getSong(allSongs, curPicker);
				} catch (NoPickException e) {}
			}
		}
		
		return getDefaultScheduleItem().getSong(allSongs, curPicker);
	}
	
	public ScheduleItem getDefaultScheduleItem(){
		 return new DefaultScheduleItem();//TODO: Add real code
	}
	
	public void pushToFirebase(){
		FirebaseCommunicator.syncScheduleWithFirebase(this);
	}


}

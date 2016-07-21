package vibekey.schedule;
import java.util.ArrayList;
import java.util.Date;

import com.firebase.client.Firebase;

import vibekey.firebase.FirebaseCommunicator;
import vibekey.picker.NoPickException;
import vibekey.picker.Picker;
import vibekey.picker.VotePicker;
import vibekey.song.Song;
import vibekey.syncable.SyncableContainer;

public class PlaySchedule extends SyncableContainer{
	public ArrayList<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();
	
	Picker curPicker;
	
	public PlaySchedule(Firebase ref, Picker picker){
		super(ref.child("schedule"));
		FirebaseCommunicator.loadSchedule(scheduleItems);
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
		this.setChanged(true);
	}
	
	public void clearSchedule(){
		this.scheduleItems.clear();
		this.setChanged(true);
	}
	
	public Song getSongNow(ArrayList<Song> allSongs) throws NoPickException{
		for(ScheduleItem scheduleItem : scheduleItems){
			if(scheduleItem.isActiveNow()){
				try {
					return scheduleItem.getSong(allSongs, curPicker);
				} catch (NoPickException e) {}
			}
		}
		
		return getDefaultScheduleItem().getSong(allSongs, curPicker);
	}
	public Song getSongAtTime(ArrayList<Song> allSongs, Date time) throws NoPickException{
		for(ScheduleItem scheduleItem : scheduleItems){
			if(scheduleItem.isActiveAtTime(time)){
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


}

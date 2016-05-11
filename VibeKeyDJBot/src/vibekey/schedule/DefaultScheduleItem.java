package vibekey.schedule;

import java.util.Date;

import vibekey.filter.Filter;
import vibekey.filter.NoFilter;

public class DefaultScheduleItem extends ScheduleItem {
	
	static int playmode = 0;
	static int repeatMode = 0;
	static Date startTime = new Date(0);
	static Date endTime = new Date(Long.MAX_VALUE);
	static String DJName = "DJBot";
	static Filter filter = new NoFilter();
	
	public DefaultScheduleItem(){
		super(playmode, repeatMode, startTime, endTime, DJName, filter);
	}

}

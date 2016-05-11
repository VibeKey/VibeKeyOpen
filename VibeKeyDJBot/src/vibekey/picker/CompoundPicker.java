package vibekey.picker;

import java.util.ArrayList;

import vibekey.song.Song;

public class CompoundPicker extends Picker {
	
	public ArrayList<Picker> pickers = new ArrayList<Picker>();
	
	public void addPicker(Picker p){
		this.pickers.add(p);
	}
	
	public void clearPickers(){
		this.pickers.clear();
	}

	@Override
	public Song getSong(ArrayList<Song> availableSongs) throws NoPickException {
		for(Picker p : pickers){
			try{
				return p.getSong(availableSongs);
			}catch(NoPickException e){}
		}
		throw new NoPickException();
	}
}

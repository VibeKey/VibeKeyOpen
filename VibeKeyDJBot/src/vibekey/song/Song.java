package vibekey.song;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.gmail.kunicins.olegs.libshout.Libshout;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import vibekey.firebase.FirebaseCommunicator;

public class Song {
	File songFile;
	Mp3File songMetaDataFile;
	ID3v2 metadatav2;
	ID3v1 metadatav1;
	boolean hasv2Data;
	boolean hasv1Data;
	public int netVotes = 0;
	public int totalVotes = 0;
	public String UUID; //used to access the song in the Firebase song list
	public boolean playing = false; //used to force it to stop
	
	public Song(File songFile){
		this.songFile = songFile;
//		this.netVotes = RandomWrapper.random.nextInt(6)-3; //TODO: Load these from firebase instead of random
//		this.totalVotes = RandomWrapper.random.nextInt(10)+5;
		loadMetaData();
	}
	
	public boolean loadMetaData(){
		try {
			songMetaDataFile = new Mp3File(songFile.getAbsolutePath());
			hasv2Data = songMetaDataFile.hasId3v2Tag();
			hasv1Data = songMetaDataFile.hasId3v1Tag();
			if(hasv2Data){
				metadatav2 = songMetaDataFile.getId3v2Tag();
			}else if(hasv1Data){
				metadatav1 = songMetaDataFile.getId3v1Tag();
			}
		} catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasv2Data || hasv1Data;
	}
	
	public SimplifiedSong getSimplifiedSong(){
		return new SimplifiedSong(getTitle(), getArtist(), getAlbum(), getGenre(), getPath(), netVotes, totalVotes, getLength());
	}
	
	public ID3v2 getv2Metadata(){
		if(hasv2Data){
			return metadatav2;
		}
		return null;
	}
	
	public ID3v1 getv1Metadata(){
		if(hasv1Data){
			return metadatav1;
		}
		return null;
	}
	
	public String getGenre(){
		if(hasv2Data){
			return metadatav2.getGenreDescription();
		}else if(hasv1Data){
			return metadatav1.getGenreDescription();
		}
		return "";
	}
	
	public String getTitle(){
		if(hasv2Data){
			return metadatav2.getTitle();
		}else if(hasv1Data){
			return metadatav1.getTitle();
		}
		return "";
	}
	
	public String getArtist(){
		if(hasv2Data){
			return metadatav2.getArtist();
		}else if(hasv1Data){
			return metadatav1.getArtist();
		}
		return "";
	}
	
	public String getAlbum(){
		if(hasv2Data){
			return metadatav2.getAlbum();
		}else if(hasv1Data){
			return metadatav1.getAlbum();
		}
		return "";
	}
	

	public long getLength(){
		return songMetaDataFile.getLengthInSeconds();
	}
	
	public String getPath(){
		return songFile.getAbsolutePath();
	}
	
	public boolean streamSong(Libshout cast){
		try {
			InputStream mp3 = new BufferedInputStream(new FileInputStream(this.songFile));
			byte[] buffer = new byte[1024];
			playing = true;
			int read = mp3.read(buffer);
			while (read > 0 && playing) {
			    cast.send(buffer, read);
			    read = mp3.read(buffer);
			}
			mp3.close();
			if(playing){ //if the song finished normally
				playing=false;
				return true;
			}else{
				return false; //if playing was set to false to stop song playback
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playing=false;
		return false;
	}
	
	public void upvote(){
		netVotes++;
		totalVotes++;
		FirebaseCommunicator.updateSong(this);
	}
	
	public void downvote(){
		netVotes--;
		totalVotes++;
		FirebaseCommunicator.updateSong(this);
	}
	
	public void stop(){
		this.playing = false;
	}
}

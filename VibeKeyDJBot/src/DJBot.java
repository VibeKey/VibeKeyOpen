import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import com.firebase.client.Firebase;
import com.gmail.kunicins.olegs.libshout.Libshout;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class DJBot {
	Firebase firebaseRef;
	byte[] buffer = new byte[1024];
	Libshout icecast;
	ArrayList<File> files;
	
	public DJBot() {
		icecast = initializeIcecast();
		files = new ArrayList<File>();
		listMp3s("/home/radio3/Music",files);
		firebaseRef = new Firebase("https://vibekey-open.firebaseio.com/");
		
		StringBuilder sb = new StringBuilder (String.valueOf ("Songs loaded: "));
		sb.append (files.size());
		System.out.println(sb.toString());
	}
	
	public void playRandom(){
		InputStream mp3;
		Random rand = new Random();
		for(int i=0;i<10000;i++){
			int songNum = rand.nextInt(files.size());
			File song = files.get(songNum);
			try {
				mp3 = new BufferedInputStream(new FileInputStream(song));
				Mp3File songMetaDataFile = new Mp3File(song.getAbsolutePath());
				if (songMetaDataFile.hasId3v2Tag()) {
					ID3v2 id3v2Tag = songMetaDataFile.getId3v2Tag();
					String nowPlaying = "Now playing:  \"" + id3v2Tag.getTitle() + "\" by " + id3v2Tag.getArtist() + "   (" + id3v2Tag.getGenreDescription() + ")";
					System.out.println(nowPlaying);
					firebaseRef.child("nowPlaying").setValue(nowPlaying);
				}else if (songMetaDataFile.hasId3v1Tag()) {
					ID3v1 id3v1Tag = songMetaDataFile.getId3v1Tag();
					String nowPlaying = "Now playing:  \"" + id3v1Tag.getTitle() + "\" by " + id3v1Tag.getArtist() + "   (" + id3v1Tag.getGenreDescription() + ")";
					System.out.println(nowPlaying);
					firebaseRef.child("nowPlaying").setValue(nowPlaying);
				}
				
				int read = mp3.read(buffer);
				while (read > 0) {
				    icecast.send(buffer, read);
				    read = mp3.read(buffer);
				}
				mp3.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			

		}
	}
	
	public void closeIcecast(){
		icecast.close();
	}
	
	Libshout initializeIcecast(){
		try {
			Libshout icecast;
			icecast = new Libshout();
			icecast.setHost("localhost");
			icecast.setPort(8000);
			icecast.setProtocol(Libshout.PROTOCOL_HTTP);
			icecast.setPassword("ImASource!");
			icecast.setMount("/stream");
			icecast.setFormat(Libshout.FORMAT_MP3);
			icecast.open();
			return icecast;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void listMp3s(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile() && getFileExtension(file).equals("mp3")) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	        	listMp3s(file.getAbsolutePath(), files);
	        }
	    }
	}
	
	private String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
}

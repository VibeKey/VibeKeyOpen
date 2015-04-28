package djbot;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import mysql.Query;
import primary_manager.PrimaryManager;
import primary_manager.VibeKey;
import core_objects_abstract.Song;

/**
 * AI that intelligently selects songs based on event and
 * preference handlers.
 * 
 * @author Rose Reatherford
 * @date Created: 12/17/2014; Last Updated: 12/31/2014
 */
public class DJBot implements Runnable {
    
    /** Constants **/
    private static int               MIN_SIZE = 10;
    
    /** Handlers **/
    // The list of preference handlers.
    private List<BotPlugin>          independentPlugins;
    // The list of events that override other behavior.
    private List<DependentBotPlugin> dependentPlugins;
    
    /** Song Planning **/
    // The queue of song ids that DJ Bot will play.
    private List<Song>               songList;
    // A HashMap of media search terms that DJ-Bot will use.
    private HashMap<String, String>  mediaTerms;
    
    /** Song Holding */
    // Buffer of extra songs so they do not clog the queue.
    private List<Song>               buffer;
    
    public DJBot() {
    
        this.independentPlugins = new ArrayList<BotPlugin>();
        this.dependentPlugins = new ArrayList<DependentBotPlugin>();
        this.buffer = new ArrayList<Song>();
        this.songList = new ArrayList<Song>();
    }
    
    /**
     * Gets the songlist.
     * 
     * @return
     */
    public List<Song> getSongList() {
    
        return new ArrayList<Song>(songList);
    }
    
    /**
     * Gets the next song in queue.
     * 
     * @return
     */
    public Song getNextSong() {
    
        System.out.println(songList.size());
        run();
        return songList.remove(0);
    }
    
    /**
     * Updates the rank
     */
    private void changeRankings() {
    
        for (int i = 0; i < songList.size(); i++)
            songList.get(i).changeRank(i);
    }
    
    private void addSongs() {
    
        while (songList.size() < MIN_SIZE) {
            if (buffer.size() <= 0)
                runSearch();
            Song nextSong = buffer.remove(0);
            VibeKey.manager.bufferSong(nextSong);
            songList.add(nextSong);
        }
    }
    
    private void runSearch() {
    	String query = "SELECT * FROM Media WHERE ";
    	
    	String[] keys = (String[]) mediaTerms.keySet().toArray();
    	for(int i = 0; i < keys.length; i++) {
    		String key = keys[i];
    		
    		if (i != 0) query += " && ";
    		query += key + " = " + mediaTerms.get(key);
    	}
    	query += " LIMIT 20";
    	
    	try {
			ResultSet results = PrimaryManager.CONN.execQuery(query);
			while (results.next()) {
				Song song = new Song(results.getString(Song.FILEPATH_KEY), results.getInt(Song.LENGTH_KEY), 
						results.getString(Song.TITLE_KEY), results.getString(Song.GENRE_KEY), 
						results.getString(Song.ARTIST_KEY), results.getInt(Song.SIZE_KEY));
				buffer.add(song);
				
				int id = results.getInt("Id");
				String name = results.getString("Name");
				System.out.println("ID: " + id + ", NAME: " + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Runs all the plugins.
     */
    private void runPlugins() {
    
        boolean changed = false;
        
        for (BotPlugin plugin : this.dependentPlugins)
            if (plugin.modifyTerms(mediaTerms, songList) && !changed)
                changed = true;
        for (BotPlugin plugin : this.independentPlugins)
            plugin.modifyTerms(mediaTerms, songList);
        
        if (changed || buffer.size() < MIN_SIZE)
            runSearch();
    }
    
    @Override
    /**
     * Creates a thread to add new songs, run plugins, and searches.
     */
    public void run() {
    
        runPlugins();
        if (songList.size() < MIN_SIZE)
            addSongs();
        changeRankings();
    }
}

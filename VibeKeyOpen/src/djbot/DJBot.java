package djbot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    
        System.out.println("I am running a search, song list is size: " + songList.size());
        // for (int i = 0; i < MIN_SIZE * 5; i++) {
        // buffer.add(new Song("", 6000, "What's new?", 684596498));
        // }
        
        // temp song file to buffer for testing
        File songFile = new File("Songs/LoseMyself.mp3");
        
        Song song = new Song(songFile.getAbsolutePath(), 201,
                "Lose Myself", null, null, null, (int) songFile.length());
        buffer.add(song);
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

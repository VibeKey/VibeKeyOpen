package core_objects_abstract;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A core object for songs.
 * 
 * @author Rose Reatherford, Benedict Wong
 * @date Created: 12/17/2014, Last modified: 4/3/2015
 */
public class Song implements Comparable<Song> {
    
    /**
     * Constants
     * 
     * Keys to hashmap
     */
    /** Constant for a file path. */
    public static String            FILEPATH_KEY = "filepath";
    /** Constant for a song id. */
    public static String            TITLE_KEY    = "title";
    /** Constant for a song genre. */
    public static String            GENRE_KEY    = "genre";
    /** Constant for a song artist. */
    public static String            ARTIST_KEY   = "artist";
    /** Constant for a song album. */
    public static String            ALBUM_KEY    = "album";
    /** The size to buffer the song. */
    public static int               BUFFER_SIZE  = 1024;
    
    // Local Information //
    /** Stores other song information locally. */
    private HashMap<String, String> information;
    /** Size of the song, in bytes. */
    private long                    size;
    private AtomicLong              bufferedSize = new AtomicLong(0);
    /** The length of the song, in second. */
    private int                     length;
    /** The song buffer. */
    private BlockingQueue<byte[]>   buffer;
    private int                     rank;
    private long                    time;
    
    /**
     * A constructor that creates a basic song.
     * 
     * @param filepath
     *            The file path of the song.
     * @param length
     *            The length of the song in total seconds.
     * @param title
     *            The title of the song.
     */
    public Song(String filepath, int length, String title, String genre, String artist, String album, int size) {
    
        this.information = new HashMap<String, String>();
        this.information.put(FILEPATH_KEY, filepath);
        this.information.put(TITLE_KEY, title);
        this.information.put(GENRE_KEY, genre);
        this.information.put(ARTIST_KEY, artist);
        this.information.put(ALBUM_KEY, album);
        this.length = length;
        this.size = size;
        this.buffer = new LinkedBlockingQueue<byte[]>();
        this.time = 0;
        this.rank = 10;
    }
    
    /**
     * Returns the pointer to the buffer.
     * 
     * @return A list of all currently buffered parts of the song, broken into
     *         BUFFER_SIZE arrays
     */
    @Deprecated
    public BlockingQueue<byte[]> getBuffer() {
    
        return buffer;
    }
    
    /**
     * Checks if at end of song (no items left in buffer, buffering is already
     * completed.
     * 
     * @return true if finished giving song data, false otherwise
     */
    public boolean atEndOfSong() {
    
        return buffer.isEmpty() && finishedBuffering();
    }
    
    /**
     * Checks if there is a next buffer item in the queue
     * 
     * @return true if there is a next item in the queue
     */
    public boolean hasNextBufferItem() {
    
        return !buffer.isEmpty();
    }
    
    /**
     * Gets next byte array
     * 
     * @return Next byte array in queue.
     */
    public byte[] getNextBufferItem() {
    
        return buffer.poll();
    }
    
    /**
     * Calculates if file is expected size.
     * 
     * @return Returns true if the thread is finished buffering, else false.
     */
    public boolean finishedBuffering() {
    
        return bufferedSize.get() >= Math.ceil(size / BUFFER_SIZE);
    }
    
    /**
     * Buffers the BUFFER_SIZE of one part of the song, notifies when done
     */
    public void buffer() {
    
        byte[] buff = new byte[BUFFER_SIZE];
        
        FileInputStream in = null;
        
        try {
            File file = new File(this.getFilePath());
            in = new FileInputStream(file);
            
            in.skip(buffer.size() * 1024);
            in.read(buff);
            
            buffer.add(buff);
            bufferedSize.incrementAndGet();
            
            synchronized (this) {
                this.notifyAll();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Gets the file path for this song.
     * 
     * @return The file path.
     */
    public String getFilePath() {
    
        return information.get(FILEPATH_KEY);
    }
    
    /**
     * Returns the length of this song.
     * 
     * @return Returns the total seconds of this song.
     */
    public int getLength() {
    
        return length;
    }
    
    /**
     * Gets the file path of a song.
     * 
     * @param parameter
     *            The parameter to get, should be a constant.
     * @return The file path of a song, returns null if the parameter does not
     *         exist.
     */
    public String getParameter(String parameter) {
    
        return information.get(parameter);
    }
    
    /**
     * Adds a new parameter.
     * 
     * @param parameter
     *            The name of the parameter, should be a constant.
     * @param value
     *            The value for this parameter, for this song.
     */
    public void addParameter(String parameter, String value) {
    
        information.put(parameter, value);
    }
    
    public void changeRank(int newRank) {
    
        this.rank = newRank;
    }
    
    /**
     * Computes the priority of the song proportional to the rank and amount
     * already buffer minus the time it has been in the queue.
     * 
     * @return The priority of this song.
     */
    private long computePriority() {
    
        return rank
                * ((BUFFER_SIZE * buffer.size()) - (System.currentTimeMillis() - time));
    }
    
    @Override
    public int compareTo(Song song) {
    
        return (int) (computePriority() - song.computePriority());
    }
    
    public class SongSerializer implements JsonSerializer<Song> {
        
        @Override
        public JsonElement serialize(Song song, Type typeOfSrc, JsonSerializationContext context) {
        
            // This method gets involved whenever the parser encounters the Song
            // object (for which this serializer is registered)
            JsonObject object = new JsonObject();
            object.addProperty("title", song.getParameter(TITLE_KEY));
            object.addProperty("genre", song.getParameter(GENRE_KEY));
            object.addProperty("artist", song.getParameter(ARTIST_KEY));
            object.addProperty("album", song.getParameter(ALBUM_KEY));
            object.addProperty("title", song.getParameter(ALBUM_KEY));
            // we create the json object for the song and send it back to the
            // Gson serializer
            return object;
        }
    }
}

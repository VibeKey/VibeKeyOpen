package primary_manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import mysql.SQLConnection;
import runnables.BufferSongRunnable;
import channel.Channel;
import core_objects_abstract.Song;

/**
 * Primary handler for streaming music and managing multiple streams.
 * 
 * @author Bill Mader
 */
public final class PrimaryManager {
    
    // See Software design thread pool example.
    // Using normal runnables
    // Takes a text file that can manage all of the plugins
    public static SQLConnection   CONN = new SQLConnection("wmhd-test.csse.rose-hulman.edu", "wmhd-test", "jungckjp", "");
    /** ExecutorServices */
    // Cached Thread Executor for buffering of songs
    private ExecutorService       bufferExecutor;
    
    // Cached Thread Executor for channels
    private ExecutorService       channelExecutor;
    
    // List of streams to play music from.
    private Map<Integer, Channel> channel;
    
    public PrimaryManager() {
    
        bufferExecutor = VibeKey.getNewExecutor(5);
        channelExecutor = VibeKey.getNewExecutor(Integer.MAX_VALUE);
        
        // Creates a new list of streams.
        this.channel = new HashMap<Integer, Channel>();
        
    }
    
    public Future<?> bufferSong(Song song) {
    
        return bufferExecutor.submit(new BufferSongRunnable(song));
    }
    
    // TODO: Question, do we want them to pass in a stream to add or give them a
    // new stream to add and return it? Also how to we want to go about removing
    // streams?
    
    public void addStream(int streamID, String name) throws InterruptedException {
    
        Channel c = new Channel(streamID, name);
        this.channel.put(streamID, c);
        c.setState(Channel.CLOSED_STATE);
        channelExecutor.submit(c.getPlayerRunnable());
    }
    
    public void removeStream(int streamID) {
    
        this.channel.remove(streamID);
    }
    
    public Channel getChannel(Integer id) {
    
        return this.channel.get(id);
    }
    
    public Map<Integer, Channel> getChannels() {
    
        return this.channel;
    }
    
    public Map<Integer, String> getChannelList() {
    
        Map<Integer, String> channelList = new HashMap<Integer, String>();
        
        for (Entry<Integer, Channel> c : channel.entrySet()) {
            channelList.put(c.getKey(), c.getValue().getName());
        }
        
        return channelList;
    }
    
    public ExecutorService getBufferExecutor() {
    
        return bufferExecutor;
    }
    
    /**
     * Sets up all the things so that the whole of the program can begin to run.
     */
    public void run() {
    
    }
    
    public void display() {
    
    }
    
}

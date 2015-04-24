package channel;

import runnables.PlayerRunnable;

import com.gmail.kunicins.olegs.libshout.Libshout;

import djbot.DJBot;

public class Channel {
    
    public static final State PAUSE_STATE  = new PauseState();
    public static final State STOP_STATE   = new IdleState();
    public static final State PLAY_STATE   = new PlayState();
    public static final State CLOSED_STATE = new ClosedState();
    public static final State OPEN_STATE   = new IdleState();
    private String            cName;
    
    private State             state        = STOP_STATE;
    
    private Libshout          icecast;
    private PlayerRunnable    player;
    private int               channelID;
    private DJBot             bot;
    
    public DJBot getDJBot() {
    
        return bot;
    }
    
    /**
     * Constructor that sets the name of the channel.
     * 
     * @throws InterruptedException
     */
    public Channel(int channelID, String name) throws InterruptedException {
    
        this(channelID, name, new DJBot());
    }
    
    public Channel(int channelID, String name, DJBot bot) throws InterruptedException {
    
        this.channelID = channelID;
        this.bot = bot;
        this.cName = name;
        
        this.player = new PlayerRunnable(this);
    }
    
    public PlayerRunnable getPlayerRunnable() {
    
        return player;
    }
    
    public int getChannelID() {
    
        return channelID;
    }
    
    // Demo code to play a song through icecast
    // @Override
    // public void play() {
    // String filename = this.bot.getSong().getFilePath();
    // byte[] buffer = new byte[1024];
    // InputStream mp3 = null;
    // try {
    // mp3 = new BufferedInputStream(new FileInputStream(new File(filename)));
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // Libshout icecast;
    // try {
    // icecast = new Libshout();
    //
    // icecast.setHost("localhost");
    // icecast.setPort(8000);
    // icecast.setProtocol(Libshout.PROTOCOL_HTTP);
    // icecast.setPassword("MotherDJBot");
    // icecast.setMount("/" + this.cName);
    // icecast.setFormat(Libshout.FORMAT_MP3);
    // icecast.open();
    // int read = mp3.read(buffer);
    // while (read > 0) {
    // icecast.send(buffer, read);
    // read = mp3.read(buffer);
    // }
    //
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // try {
    // mp3.close();
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // }
    /*
     * For testing
     * public static void main(String[] args) {
     * Channel.addSong(args[0]);
     * }
     */
    /**
     * Gets the state for the channel.
     * 
     * @param state
     */
    public State getState() {
    
        return this.state;
    }
    
    /**
     * Sets the state for the channel.
     * 
     * @param state
     */
    public void setState(State state) {
    
        this.state = state;
        this.state.function(this);
    }
    
    /**
     * Gets the name of the channel.
     * 
     * @return
     */
    public String getName() {
    
        return this.cName;
    }
    
    /**
     * Sets the Icecast connection for the channel.
     * 
     * @param icecast
     */
    public void setIcecast(Libshout icecast) {
    
        this.icecast = icecast;
    }
    
    /**
     * Gets the Icecast connection for the channel.
     * 
     * @return
     */
    public Libshout getIcecast() {
    
        return this.icecast;
    }
    
    /**
     * Gets the thread for this channel.
     * 
     * @return
     */
    public PlayerRunnable getThread() {
    
        return this.player;
    }
}

package primary_manager;

//import com.example.e4.rcp.todo.model.*;
//import com.example.e4.rcp.todo.service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class VibeKey {
    
    public static boolean         isStarted      = false;
    public static PrimaryManager  manager        = new PrimaryManager();
    public static ExecutorService threadExecutor = Executors.newCachedThreadPool();
    
    public static void start() throws SQLException, ClassNotFoundException, IOException, InterruptedException {
    
        try {
            System.load((VibeKey.class.getClassLoader().getResource("../../").toString() + "WEB-INF/lib/libshout-java.so").replace("file:", ""));
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Libshout-java.so already loaded - moving on...");
        }
        System.out.println("Done loading Libshout-java.so");
        // SQLConnection conn = new SQLConnection("wmhd-test.csse.rose-hulman.edu", "wmhd-test", "jungckjp", "");
        // conn.execQuery("SELECT * FROM Calendar_Item");
        
        // Todo todo = new Todo();
        // todo.setDescription("hooray for plugin");
        // System.out.println(todo.getDescription());
        
        /*
         * CallableStatement cStmt = conn.prepareCall("{call Calendar_GET(?,?,?,?)}");
         * cStmt.setInt(1,03);
         * cStmt.setInt(2, 2015);
         * cStmt.registerOutParameter(3, java.sql.Types.INTEGER);
         * cStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
         * boolean results2 = cStmt.execute();
         * ResultSet rs = null;
         * while(results2){
         * rs = cStmt.getResultSet();
         * 
         * String name2 = cStmt.getString(4);
         * int id = cStmt.getInt(3);
         * System.out.println("called procedure name: " + name2 + " and the ID is: " + id);
         * //results2 = cStmt.getMoreResults();
         * }
         * String name = cStmt.getString(1);
         * System.out.println(name);
         */
        
        manager.addStream(1, "TEST");
        
        // DJBot bot = new DJBot();
        // System.out.println(bot.getSong().getId());
        // System.out.println(bot.getSong().getId());
        // System.out.println(bot.getSong().getId());
        // System.out.println(bot.getSong().getId());
        isStarted = true;
    }
    
    public static ExecutorService getNewExecutor(int maxSize) {
    
        return new ThreadPoolExecutor(0, maxSize,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()); // queue with a size
    }
    
}

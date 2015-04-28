package mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnection {
    
    protected Connection conn;
    
    public SQLConnection(String address, String dbName, String user, String password) {
    
    	try {
        Class.forName("com.mysql.jdbc.Driver");
        
        this.conn = DriverManager.getConnection(
                "jdbc:mysql://" + address + "/" + dbName, user, password);
    	} catch (Exception e){
    		System.out.println(e);
    	}
    }
    
    public ResultSet execQuery(String query) throws SQLException {
    
        Query q = new Query(query, this.conn);
        return q.execute();
    }
    
    public void execCalendar_GET() throws SQLException {
    
        CallableStatement cStmt = conn.prepareCall("{call Calendar_GET(?,?,?)}");
        cStmt.setInt(1, 03);
        cStmt.setInt(2, 2015);
        cStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
        cStmt.executeQuery();
        
        String name = cStmt.getString(3);
        System.out.println(name);
    }
    
    public void execSong_Select(int numberOfSongs, String genre) throws SQLException {
    
        CallableStatement cStmt = conn.prepareCall("{call Song_Select(?,?,?)}");
        cStmt.setInt(1, numberOfSongs);
        cStmt.setString(2, genre);
        cStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
        cStmt.executeQuery();
        
        String name = cStmt.getString(3);
        System.out.println(name);
    }
    
}

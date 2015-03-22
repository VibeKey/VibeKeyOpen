package mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnection {
	protected Connection conn;

	public SQLConnection(String address, String dbName, String user, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		
		
		this.conn = DriverManager.getConnection(
                "jdbc:mysql://" + address + "/" + dbName, user, password);
	}
	
	public void execQuery(String query) throws SQLException {
		java.sql.Statement stmt = conn.createStatement();
		ResultSet results = stmt.executeQuery(query);
		while (results.next()) {
			int id = results.getInt("id");
			String name = results.getString("name");
			System.out.println("ID: " + id + ", NAME: " + name);
		}
	}
	
	
	public void execStoredProc() throws SQLException{
		CallableStatement cStmt = conn.prepareCall("{call Calendar_GET(?,?,?)}");
		cStmt.setInt(1,03);
		cStmt.setInt(2, 2015);
		cStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		cStmt.executeQuery();
		
		String name = cStmt.getString(3);
		System.out.println(name);
	}
	
}

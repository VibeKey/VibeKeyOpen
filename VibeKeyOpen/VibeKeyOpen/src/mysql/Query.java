package mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {
	protected String query;
	protected Connection conn;

	public Query(String query, Connection conn) {
		this.query = query;
		this.conn = conn;
	}
	
	public ResultSet execute() throws SQLException {
		java.sql.Statement stmt = conn.createStatement();
		ResultSet results = stmt.executeQuery(query);
		return results;
//		while (results.next()) {
//			int id = results.getInt("id");
//			String name = results.getString("name");
//			System.out.println("ID: " + id + ", NAME: " + name);
//		}
	}
}

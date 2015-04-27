package web.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import mysql.SQLManager;
import web.BCrypt;
import web.Response;
import web.Response.FailResponse;
import web.Response.SuccessResponse;

public class UsersRequestHandler {
    
    public static Response handleRegisterUserRequest(HttpServletRequest request) {
    
        try {
            
            String user = request.getHeader("authUser");
            String pass = request.getHeader("authPass");
            
            // check to make sure username does not already exist
            // PreparedStatement check = SQLManager.getConn("Users").prepareStatement("SELECT COUNT(id) FROM Users WHERE username = '" + user + "';");
            // ResultSet users = check.executeQuery();
            // users.next();
            // if (users.getInt(1) != 0) {
            // return new FailResponse("Username already exists");
            // }
            
            // Permissions levels:
            // 1 - Users (Edit saved companies, visit list)
            // 10 - Admin (Edit user permssions, edit company/category list)
            // Always add as users, require admin access to elevate
            PreparedStatement statement =
                    SQLManager.getConn("Users").prepareStatement("INSERT INTO Users (username, hashedPw, permissions) VALUES (?, ?, 1);");
            statement.setString(1, user);
            statement.setString(2, BCrypt.hashpw(pass, BCrypt.gensalt()));
            Integer insertResult = statement.executeUpdate();
            if (insertResult == 0) {
                return new FailResponse("Username already exists");
            }
            
            return new SuccessResponse("Rows changed: " + insertResult);
        } catch (SQLException e) {
            
            return new FailResponse(e.toString());
        }
        
    }
    
    public static Response handleLoginRequest(HttpServletRequest request) {
    
        try {
            
            String user = request.getHeader("authUser");
            String pass = request.getHeader("authPass");
            
            PreparedStatement prepStatement =
                    SQLManager.getConn("Users").prepareStatement("SELECT hashedPw FROM Users WHERE username = '" + user + "';");
            
            ResultSet result = prepStatement.executeQuery();
            result.next();
            
            if (BCrypt.checkpw(pass, result.getString("hashedPw"))) {
                
            }
            
            return new SuccessResponse("success");
        } catch (SQLException e) {
            
            return new FailResponse(e.toString());
        }
        
    }
    
}

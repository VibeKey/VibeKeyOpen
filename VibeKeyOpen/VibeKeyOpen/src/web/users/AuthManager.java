package web.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import mysql.SQLManager;
import web.BCrypt;
import web.Response;
import web.Response.FailResponse;
import web.Response.SuccessResponse;
import web.ServletLog;
import web.ServletLog.LogEvent;

public class AuthManager {
    
    private static final int         SESSION_VALID_DAYS   = 15;
    
    private static PreparedStatement getHashedPWStatement = null;
    private static PreparedStatement check                = null;
    private static PreparedStatement newSession           = null;
    private static PreparedStatement getAuthToken         = null;
    
    private static boolean           isSetup              = false;
    
    public static void setupAuthManager() {
    
        if (!isSetup) {
            
            try {
                getHashedPWStatement = SQLManager.getConn("Users").prepareStatement("SELECT hashedPw FROM Users WHERE username = ?;");
                check = SQLManager.getConn("Users").prepareStatement("SELECT COUNT(username) FROM Users WHERE username = ?;");
                newSession =
                        SQLManager.getConn("Users").prepareStatement("INSERT INTO Sessions "
                                + "VALUES(?, ?, ?, ?);");
                getAuthToken =
                        SQLManager.getConn("Users").prepareStatement(
                                "SELECT sessionKey, sessionValidDate FROM Sessions WHERE username = ? AND sessionClient = ?;");
            } catch (SQLException e) {
                LogEvent event = new LogEvent();
                event.setDetail("Type", "Exception");
                event.setDetail("Exception", e.getStackTrace());
                ServletLog.logEvent(event);
            }
        }
    }
    
    public static Response addUser(HttpServletRequest request) {
    
        try {
            setupAuthManager();
            
            String userName = request.getHeader("authUser");
            String password = request.getHeader("authPass");
            
            // check to make sure username does not already exist
            check.setString(1, userName);
            ResultSet users = check.executeQuery();
            users.next();
            if (users.getInt(1) != 0) {
                return new FailResponse("Username already exists");
            }
            
            // Permissions levels:
            // 1 - Users (Edit saved companies, visit list)
            // 10 - Admin (Edit user permssions, edit company/category list)
            // Always add as users, require admin access to elevate
            PreparedStatement statement =
                    SQLManager.getConn("Users").prepareStatement("INSERT INTO Users (username, hashedPw, permissions) VALUES (?, ?, 1);");
            statement.setString(1, userName);
            statement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            Integer insertResult = statement.executeUpdate();
            
            return new SuccessResponse("Rows changed: " + insertResult);
        } catch (SQLException e) {
            LogEvent event = new LogEvent();
            event.setDetail("Type", "Exception");
            event.setDetail("Exception", e.getStackTrace());
            ServletLog.logEvent(event);
            
            return new FailResponse(e);
        }
    }
    
    public static Response authenticateUser(HttpServletRequest request) {
    
        try {
            setupAuthManager();
            
            // Response checkTokenResponse;
            // if ((checkTokenResponse = AuthManager.checkToken(request)).success) {
            // return checkTokenResponse;
            // }
            
            String userName = request.getHeader("authUser");
            String password = request.getHeader("authPass");
            
            if (userName == null || password == null) {
                return new FailResponse("Invalid Username/Password provided");
            }
            
            getHashedPWStatement.setString(1, userName);
            
            ResultSet result = getHashedPWStatement.executeQuery();
            result.next();
            
            if (!BCrypt.checkpw(password, result.getString("hashedPw"))) {
                return new FailResponse("Invalid Username/Password Combination");
            }
            
            String sessionKey = BCrypt.hashpw(userName + System.currentTimeMillis(), BCrypt.gensalt());
            Timestamp sessionValidDate = new Timestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(SESSION_VALID_DAYS));
            String sessionClient = request.getHeader("User-Agent") == null ? "NO USER-AGENT PROVIDED" : request.getHeader("User-Agent");
            
            newSession.setString(1, userName);
            newSession.setString(2, sessionKey);
            newSession.setTimestamp(3, sessionValidDate);
            newSession.setString(4, sessionClient);
            
            newSession.executeUpdate();
            
            SuccessResponse response = new SuccessResponse();
            
            response.addToReturnData("token", sessionKey);
            response.addCookie("authUser", userName);
            response.addCookie("authToken", sessionKey);
            
            return response;
        } catch (SQLException e) {
            LogEvent event = new LogEvent();
            event.setDetail("Type", "Exception");
            event.setDetail("Exception", e.getStackTrace());
            ServletLog.logEvent(event);
            
            return new FailResponse(e);
        }
    }
    
    public static Response checkToken(HttpServletRequest request) {
    
        try {
            setupAuthManager();
            
            String userName = request.getHeader("authUser");
            String token = request.getHeader("authToken");
            
            if (request.getCookies() != null) {
                for (Cookie c : request.getCookies()) {
                    if (c.getName().equals("authUser")) {
                        userName = c.getValue();
                    }
                    if (c.getName().equals("authToken")) {
                        token = c.getValue();
                    }
                }
            }
            
            if (userName == null) {
                return new FailResponse("Username not provided");
            }
            
            if (token == null) {
                return new FailResponse("Token not provided");
            }
            
            getAuthToken.setString(1, userName);
            getAuthToken.setString(2, request.getHeader("User-Agent") == null ? "NO USER-AGENT PROVIDED" : request.getHeader("User-Agent"));
            ResultSet result = getAuthToken.executeQuery();
            
            boolean hasNextResult;
            while (hasNextResult = result.next()) {
                
                if (token.equals(result.getString("sessionKey"))
                        && result.getTimestamp("sessionValidDate").after(new Timestamp(System.currentTimeMillis()))) {
                    break;
                }
            }
            if (!hasNextResult) {
                return new FailResponse("Invalid Token");
            }
            
            result.close();
            return new SuccessResponse();
        } catch (SQLException e) {
            LogEvent event = new LogEvent();
            event.setDetail("Type", "Exception");
            event.setDetail("Exception", e.getStackTrace());
            ServletLog.logEvent(event);
            
            return new FailResponse(e);
        }
    }
}

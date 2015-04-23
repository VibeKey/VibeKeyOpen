package web;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

import com.google.gson.Gson;

public abstract class Response {
    
    public final boolean             success;
    public Cookie                    cookie     = null;
    public final Map<String, Object> returnData = new HashMap<String, Object>();
    
    public Response(boolean success) {
    
        this.success = success;
    }
    
    public Response addToReturnData(String key, Object value) {
    
        returnData.put(key, value);
        return this;
    }
    
    public String toJson() {
    
        return this.toString();
    }
    
    @Override
    public String toString() {
    
        return new Gson().toJson(returnData);
    }
    
    public static class SuccessResponse extends Response {
        
        public SuccessResponse() {
        
            super(true);
            addToReturnData("success", 1);
            addToReturnData("timestamp", System.currentTimeMillis());
        }
        
        public SuccessResponse(String message) {
        
            super(true);
            addToReturnData("success", 1);
            addToReturnData("message", message);
            addToReturnData("timestamp", System.currentTimeMillis());
        }
        
        public Response setAuthCookie(String authToken) {
        
            cookie = new Cookie("authToken", authToken);
            cookie.setSecure(true);
            cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(30));
            
            return this;
        }
    }
    
    public static class FailResponse extends Response {
        
        public FailResponse(String error) {
        
            super(false);
            addToReturnData("success", 0);
            addToReturnData("error", error);
            addToReturnData("timestamp", System.currentTimeMillis());
        }
        
        public FailResponse(Exception exception) {
        
            super(false);
            addToReturnData("success", 0);
            
            addToReturnData("exception message", exception.getLocalizedMessage());
            addToReturnData("exception stack trace", exception.getStackTrace());
            addToReturnData("timestamp", System.currentTimeMillis());
        }
    }
}

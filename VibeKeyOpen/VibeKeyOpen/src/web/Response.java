package web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

import com.google.gson.Gson;

public abstract class Response {
    
    public final boolean             success;
    public ArrayList<Cookie>         cookies    = null;
    public final Map<String, Object> returnData = new HashMap<String, Object>();
    
    public Response(boolean success) {
    
        this.success = success;
    }
    
    public Response addToReturnData(String key, Object value) {
    
        returnData.put(key, value);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getFromReturnData(String key, Class<T> returnClass) {
    
        Object returnItem = returnData.get(key);
        if (returnItem != null) {
            
            if (returnItem.getClass().equals(returnClass)) {
                return (T) returnItem;
            }
            else if (returnClass.equals(Integer.class)) {
                return (T) Integer.valueOf(String.valueOf(returnItem));
            }
            else if (returnClass.equals(Long.class)) {
                return (T) Long.valueOf(String.valueOf(returnItem));
            }
            else if (returnClass.equals(Double.class)) {
                return (T) Double.valueOf(String.valueOf(returnItem));
            }
            else if (returnClass.equals(BigDecimal.class)) {
                return (T) new BigDecimal(String.valueOf(returnItem));
            }
            else if (returnClass.equals(Boolean.class)) {
                return (T) Boolean.valueOf(String.valueOf(returnItem));
            }
            else if (returnClass.equals(String.class)) {
                return (T) String.valueOf(returnItem);
            }
            else {
                return returnClass.cast(returnItem);
            }
        }
        return null;
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
        
        public Response addCookie(String key, String value) {
        
            if (cookies == null) {
                cookies = new ArrayList<Cookie>();
            }
            
            Cookie newCookie = new Cookie(key, value);
            newCookie.setSecure(true);
            newCookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(30));
            
            cookies.add(newCookie);
            
            return this;
        }
    }
    
    public static class FailResponse extends Response {
        
        public FailResponse(int errorCode) {
        
            super(false);
            addToReturnData("success", 0);
            addToReturnData("errorCode", errorCode);
            addToReturnData("timestamp", System.currentTimeMillis());
        }
        
        public FailResponse(String description) {
        
            super(false);
            addToReturnData("success", 0);
            addToReturnData("errorCode", -1);
            addToReturnData("description", description);
            addToReturnData("timestamp", System.currentTimeMillis());
        }
        
        public FailResponse(int errorCode, String description) {
        
            super(false);
            addToReturnData("success", 0);
            addToReturnData("errorCode", errorCode);
            addToReturnData("description", description);
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

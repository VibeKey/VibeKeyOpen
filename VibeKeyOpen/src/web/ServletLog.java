package web;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

public class ServletLog {
    
    private static final int           MAX_LOG_SIZE = 5000;
    
    private static final Set<LogEvent> log          = Collections.newSetFromMap(new LinkedHashMap<LogEvent, Boolean>() {
                                                        
                                                        private static final long serialVersionUID = -2642465950081345680L;
                                                        
                                                        @Override
                                                        protected boolean removeEldestEntry(Map.Entry<LogEvent, Boolean> eldest) {
                                                        
                                                            return size() > MAX_LOG_SIZE;
                                                        }
                                                    });     ;
    
    public static void logEvent(LogEvent event) {
    
        log.add(event);
    }
    
    public static String getLogJson() {
    
        return new Gson().toJson(log);
    }
    
    public static class LogEvent {
        
        private final HashMap<String, Object> details   = new HashMap<String, Object>();
        private final long                    timestamp = System.currentTimeMillis();
        
        public void setDetail(String key, Object value) {
        
            details.put(key, value);
        }
        
        public long getTimestamp() {
        
            return timestamp;
        }
    }
    
    public static <T> LinkedHashSet<T> getSizeLimitedLinkedHashSet(final int size, Class<T> type) {
    
        Collections.newSetFromMap(new LinkedHashMap<T, Boolean>() {
            
            /**
                 * 
                 */
            private static final long serialVersionUID = 3774547165048604458L;
            
            @Override
            protected boolean removeEldestEntry(Map.Entry<T, Boolean> eldest) {
            
                return size() > size;
            }
        });
        return null;
    }
}

package vibekey.firebase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.firebase.client.Firebase;

import vibekey.stream.StreamController;

public class CompoundCommandParser extends FirebaseCommandParser {
	public CompoundCommandParser(StreamController streamController) {
		super(streamController, "");
		// TODO Auto-generated constructor stub
	}

	public Map<String, FirebaseCommandParser> parsers = new HashMap<String, FirebaseCommandParser>();
	
	public void addParser(FirebaseCommandParser newParser){
		this.parsers.put(newParser.commandAccess, newParser);
	}
	
	public Collection<FirebaseCommandParser> getParsers(){
		return parsers.values();
	}
	
	@Override
	public void setupFirebaseListeners(Firebase ref){
		Firebase commandRef;
		if(this.commandAccess.equals("")){
			commandRef = ref;
		}else{
			commandRef = ref.child(this.commandAccess);
		}
		for(FirebaseCommandParser parser : this.getParsers()){
			parser.setupFirebaseListeners(commandRef);
		}
	}
	
	
}

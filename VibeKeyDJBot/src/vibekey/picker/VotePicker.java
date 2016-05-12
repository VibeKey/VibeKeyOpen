package vibekey.picker;

import java.util.ArrayList;

import vibekey.song.Song;
import vibekey.util.RandomWrapper;

public class VotePicker extends Picker {
	public Song getSong(ArrayList<Song> availableSongs){
		int netVotes = 0;
		int maxNetVotes = Integer.MIN_VALUE;
		int minNetVotes = Integer.MAX_VALUE;
		int songCount = 0;
		
		for(Song song : availableSongs){
			songCount++;
			netVotes += song.netVotes;
			maxNetVotes = song.netVotes > maxNetVotes ? song.netVotes : maxNetVotes;
			minNetVotes = song.netVotes < minNetVotes ? song.netVotes : maxNetVotes;
		}
		
		//Offset for each song's delegates
		int songVoteOffset = maxNetVotes - minNetVotes+1;

		//Using "delegates" as a term for how many "votes" each song gets in the final selection
		int totalVoteDelegates = netVotes + songCount*songVoteOffset;
		
		
		int winner = RandomWrapper.nextInt(totalVoteDelegates);
		
		//Now, loop back through and find which one is the winner!
		for(Song song : availableSongs){
			winner -= song.netVotes+songVoteOffset;
			if(winner < 0){
				return song;
			}
		}
		
		//TODO: Replace this with proper error handling
		System.out.println("ERROR when trying to use votes to influence song selection");
		return null;
	}
}

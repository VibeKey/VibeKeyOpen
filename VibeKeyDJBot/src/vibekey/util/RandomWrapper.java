package vibekey.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import vibekey.song.Song;

public class RandomWrapper {
	static Random random = new Random(System.currentTimeMillis());
	
	public static int nextInt(int upper){
		return random.nextInt(upper);
	}
	
	public static double nextDouble(){
		return random.nextDouble();
	}
	
	public static Song nextSongWeighted(ArrayList<Song> songs){
		if (songs.size() == 0) {
			return null;
		}
		int minNetVote = Integer.MAX_VALUE;
		int maxNetVote = Integer.MIN_VALUE;
		for (Song song : songs){
			minNetVote = Math.min(minNetVote, song.netVotes);
			maxNetVote = Math.max(maxNetVote, song.netVotes);
		}
		int totalVoteCount = 0;
		HashMap<Song, Integer> songVoteMap = new HashMap<Song, Integer>();
		for (Song song : songs){
			int voteCount = song.netVotes - 2*minNetVote + maxNetVote + 1;
			songVoteMap.put(song, totalVoteCount);
			totalVoteCount += voteCount;
		}
		
		

		int voteNum = random.nextInt(totalVoteCount);
		
		Song lastSong = null;
		for (Song song : songs){
			int songVoteCount = songVoteMap.get(song);
			if (songVoteCount > voteNum)
				break;
			lastSong = song;
			
		}
		return lastSong;
	}
}

package vibekey.song;

import vibekey.prioritizer.SongPrioritizer;

public interface Playable {
	public Playable getPlayable(SongPrioritizer prioritizer);
}

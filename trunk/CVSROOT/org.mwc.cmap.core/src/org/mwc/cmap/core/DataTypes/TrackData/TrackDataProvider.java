/**
 * 
 */
package org.mwc.cmap.core.DataTypes.TrackData;

import Debrief.Tools.Tote.WatchableList;
import Debrief.Wrappers.TrackWrapper;

/**
 * @author ian.mayo
 */
public interface TrackDataProvider
{
	public static interface TrackDataListener
	{
		/**
		 * find out that the primary has changed
		 * 
		 * @param primary         the primary track
		 */
		public void tracksUpdated(WatchableList primary, WatchableList[] secondaries);
	}
	
	/** people who want to find out about tracks moving
	 * 
	 * @author ian.mayo
	 *
	 */
	public static interface TrackShiftListener
	{
		/** 
		 * 
		 */
		public void trackShifted(TrackWrapper subject);
	}

	/**
	 * declare that we want to be informed about changes in selected tracks
	 */
	public void addTrackDataListener(TrackDataListener listener);

	/**
	 * forget that somebody wants to know about track changes
	 */
	public void removeTrackDataListener(TrackDataListener listener);
	
	/**
	 * declare that we want to be informed about changes in selected tracks
	 */
	public void addTrackShiftListener(TrackShiftListener listener);

	/**
	 * forget that somebody wants to know about track changes
	 */
	public void removeTrackShiftListener(TrackShiftListener listener);
	
	/** ok - tell anybody that wants to know about our movement
	 * 
	 * @param target what's being dragged
	 */
	public void fireTrackShift(TrackWrapper target);
	

	/**
	 * find out what the primary track is
	 */
	public WatchableList getPrimaryTrack();

	/**
	 * find out what the secondary track is
	 */
	public WatchableList[] getSecondaryTracks();

}

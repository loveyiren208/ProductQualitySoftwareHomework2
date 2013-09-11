package edu.nyu.pqs.stopwatch.impl;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 * @author xiaonanwang changes it
 */
public class StopwatchFactory {
	
  private static List<IStopwatch> Stopwatches;
  private static ConcurrentHashMap<String,IStopwatch> stopwatchMap = 
	  new ConcurrentHashMap<String,IStopwatch>();
  private static Object locker = new Object();
  
  /**
   * Creates and returns a new IStopwatch object
   * @param id The identifier of the new object
   * @return The new IStopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, 
   * 			 or already taken
   */
  public static IStopwatch getStopwatch(String id) {
	synchronized(locker) {
	  if (id == "" || id == null || stopwatchMap.containsKey(id)) {
		throw new IllegalArgumentException("The id is illegal");
	  } else {
		IStopwatch stopwatch = new Stopwatch(id);
		stopwatchMap.put(id,stopwatch);
		return stopwatch;
	  }
	}
  }

  /**
   * Returns a list of all created stopwatches
   * @return a List of al creates IStopwatch objects.  Returns an empty
   * list oi no IStopwatches have been created.
   */
  public static List<IStopwatch> getStopwatches() {
	synchronized (locker) {
	  Stopwatches = new Vector<IStopwatch>(stopwatchMap.values());
	  return Stopwatches;
	}
  }
}

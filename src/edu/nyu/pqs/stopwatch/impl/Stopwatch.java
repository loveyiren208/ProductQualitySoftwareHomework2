package edu.nyu.pqs.stopwatch.impl;

import java.util.List;
import java.util.Vector;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 *
 * A thread-safe object that can be used for timing laps.  The stopwatch
 * objects are created in the StopwatchFactory.  Different threads can
 * share a single stopwatch object and safely call any of the stopwatch 
 * methods.
 * Use <code>Stopwatch(id)</code> to create an instance.
 * @author xiaonanwang
 *
 */
public class Stopwatch implements IStopwatch {
	
  private String id;
  private long startTime = 0;
  private boolean ifRunning = false;
  private final List<Long> timeLaps = new Vector<Long>();
  private long lapTime = 0;
  private long stopTime = 0;
  //store the time between last lap and stop
  private long lastLapTimeForStop = 0; 
  
  //Adjust nanoseconds to milliseconds
  private static final int ONE_MILLION = 1000000;
	
  /**
   * Constructor of Stopwatch. Create a new Stopwatch object with a String 
   * id.
   * @param id
   */
  public Stopwatch(String id) {
    if (id == "" || id == null) {
      throw new IllegalArgumentException("id is illegal");
    } else {
   	  this.id = id;
	}
  }
	
  /**
   * Properly synchronized the ifRunning mark.
   * In order to allow methods can set the synchronized mark to false
   */
  private synchronized void stopRun() {
    this.ifRunning = false;
  }
	
  /**
   * Properly synchronized the ifRunning mark.
   * In order to allow methods can set the synchronized mark to true
   */
  private synchronized void startRun() {
    this.ifRunning = true;
  }
  /**
   * Properly synchronized the ifRunning mark.
   * In order to allow methods can get the synchronized this mark
   */
  private synchronized boolean getIfRunning() {
    return this.ifRunning;
  }
	
  /**
   * Returns the Id of this stopwatch
   * @return the Id of this stopwatch.  Will never be empty or null.
   */
  @Override
  public String getId() {
    return this.id;
  }
                                                                                //
  /**
   * Starts the stopwatch.
   * @throws IllegalStateException if called when the stopwatch is already 
   * running
   */
  @Override
  public void start() {
    synchronized(this) {
      if (this.getIfRunning()) {
  	    throw new IllegalStateException("The stopwatch is already running");
	  } else {
	    this.startRun();
	    this.startTime = System.nanoTime() / ONE_MILLION;
	    
	    if (this.stopTime == 0) {
	      this.lapTime = this.startTime;
	    } else {
	      this.timeLaps.remove(this.timeLaps.size() - 1);
	    }
	  }
	}	
  }

  /**
   * Stores the time elapsed since the last time lap() was called
   * or since start() was called if this is the first lap.
   * @throws IllegalStateException if called when the stopwatch isn't running
   */
  @Override
  public void lap() {
    synchronized (this) {
  	  if (!this.getIfRunning()) {
        throw new IllegalStateException("The stopwatch isn't running");
      } else {
        long tempLapTime = System.nanoTime() / ONE_MILLION;
        if (this.startTime > this.lapTime) {
          this.timeLaps.add(tempLapTime - this.startTime + this.lastLapTimeForStop);
        } else {
          this.timeLaps.add(tempLapTime - this.lapTime);
        }
        this.lapTime = tempLapTime;
      }
    }
  }

  /**
   * Stops the stopwatch (and records one final lap).
   * @throws IllegalStateException if called when the stopwatch isn't running
   */
  @Override
  public void stop() {
    synchronized (this) {
  	  if (!this.getIfRunning()) {
  	    throw new IllegalStateException("The stopwatch isn't running");
	  } else {
		this.stopRun();
		long tempLapTime = System.nanoTime() / ONE_MILLION;
		if (this.lapTime > this.startTime) {
		  this.timeLaps.add(tempLapTime - this.lapTime);
		} else {
		  this.timeLaps.add(tempLapTime - this.startTime + this.lastLapTimeForStop);
		}
		this.stopTime = tempLapTime;
		this.lastLapTimeForStop = this.timeLaps.get(this.timeLaps.size() - 1);
	  }
	}
  }

  /**
   * Resets the stopwatch.  If the stopwatch is running, this method stops the
   * watch and resets it.  This also clears all recorded laps.
   */
  @Override
  public void reset() {	  
	synchronized(this) {
  	  if (this.getIfRunning()) {
	    this.stopRun();
	  }
	  this.startTime = 0;
	  this.stopTime = 0;
	  this.lapTime = 0;
	  this.lastLapTimeForStop = 0;
	  this.timeLaps.clear();	
	}
 
  }

  /**
   * Returns a list of lap times (in milliseconds).  This method can be called at
   * any time and will not throw an exception.
   * @return a list of recorded lap times or an empty list if no times are 
   * recorded.
   */
  @Override
  public List<Long> getLapTimes() {
	return this.timeLaps;
  }

  /**
   * 
   * @return A String including all info of stopwatch
   */
  @Override
  public String toString() {
	return "Stopwatch [id=" + id + ", startTime=" + startTime
	    + ", ifRunning=" + ifRunning + ", timeLaps=" + timeLaps
	    + ", lapTime=" + lapTime + ", stopTime=" + stopTime + "]";
  }

  /**
   * override hashCode
   * @return hashCode
   */
  @Override
  public int hashCode() {
 	final int prime = 31;
 	int result = 1;
 	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + (ifRunning ? 1231 : 1237);
	result = prime * result + (int) (lapTime ^ (lapTime >>> 32));
	result = prime * result + (int) (startTime ^ (startTime >>> 32));
	result = prime * result + (int) (stopTime ^ (stopTime >>> 32));
	result = prime * result
	    + ((timeLaps == null) ? 0 : timeLaps.hashCode());
	return result;
  }

  /**
   * Override the equal functions
   * @return boolean ifEquals
   */
  @Override
  public boolean equals(Object obj) {
 	if (this == obj) {
      return true;
 	}
	if (obj == null) {
	  return false;
	}
	if (getClass() != obj.getClass()) {
	  return false;
	}
	Stopwatch other = (Stopwatch) obj;
	if (id == null) {
      if (other.id != null) {
    	return false;
      }
    } else if (!id.equals(other.id)) {
	  return false;
    }
	if (ifRunning != other.ifRunning) {
	  return false;
	}
   	if (lapTime != other.lapTime) {
	  return false;
   	}
 	if (startTime != other.startTime) {
	  return false;
 	}
	if (stopTime != other.stopTime) {
	  return false;
	}
	if (timeLaps == null) {
	  if (other.timeLaps != null) {
		  return false;
	  }
	} else if (!timeLaps.equals(other.timeLaps)) {
		  return false;
	}
		return true;
  }

	
}

package edu.nyu.pqs.stopwatch.demo;

import java.util.List;
import java.util.logging.Logger;

import edu.nyu.pqs.stopwatch.api.IStopwatch;
import edu.nyu.pqs.stopwatch.impl.StopwatchFactory;

/**
 * This is a simple program that demonstrates just some of
 * the functionality of the IStopwatch interface and StopwatchFactory class.
 * Just because this class runs successfully does not mean that the assignment is
 * complete.  It is up to you to implement the methods of IStopwatch and StopwatchFactory
 *
 */
public class SlowThinker {

	/** use a logger instead of System.out.println */
	private static final Logger logger = Logger
			.getLogger("edu.nyu.pqs.ps4.demo.SlowThinker");

	/**
	 * Run the SlowThinker demo application
	 * 
	 * @param args
	 *            a single argument specifying the number of threads
	 */
	public static void main(String[] args) {
		SlowThinker thinker = new SlowThinker();
		thinker.go();
	}

	/**
	 * Starts the slowthinker object It will get a stopwatch, set a number of
	 * lap times, stop the watch and then print out all the lap times
	 * 
	 */
	private void go() {
		final IStopwatch stopwatch = StopwatchFactory.getStopwatch("ID ");

		Runnable runnable = new Runnable() {
			public void run() {
				List<Long> times = stopwatch.getLapTimes();
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.start();
				logger.info(times.toString());
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.stop();
				logger.info(times.toString());
				try {
					Thread.sleep(600);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.start();
				logger.info(times.toString());
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.stop();
				logger.info(times.toString());
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.start();
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.lap();
				logger.info(times.toString());
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.lap();

				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.lap();
				logger.info(times.toString());
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.lap();
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.stop();
				logger.info(times.toString());
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.start();
				logger.info(times.toString());
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { /* safely ignore this */
				}
				stopwatch.stop();
				
				/* test reset */
				/*
				stopwatch.reset();
				stopwatch.start();
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) { 
				}
				stopwatch.stop();
				*/
				
				logger.info(times.toString());
			}
		};
		Thread thinkerThread = new Thread(runnable);
		thinkerThread.start();

	}
}

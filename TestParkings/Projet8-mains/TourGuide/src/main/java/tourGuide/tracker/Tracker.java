package tourGuide.tracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.Data;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tourGuide.service.TourGuideService;
import tourGuide.user.User;

@Data
public class Tracker extends Thread {
	private final Logger logger = LoggerFactory.getLogger(Tracker.class);
	private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);
	//private static final long trackingPollingInterval = TimeUnit.SECONDS.toSeconds(1);
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final TourGuideService tourGuideService;
	private boolean stop = false;
	private List<User> userTreatement = new ArrayList<>();

	public void setUserTreatement(List<User> sublistUserTreatement) {
		this.userTreatement.addAll(new ArrayList<User>(sublistUserTreatement));
	}

	public Tracker(TourGuideService tourGuideService) {
		this.tourGuideService = tourGuideService;

		executorService.submit(this);
	}
	
	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {
		logger.debug("Tracker stopping (0)");
		stop = true;
		executorService.shutdownNow();
	}
	
	@Override
	public void run() {
		StopWatch stopWatch = new StopWatch();
		logger.info("Nombre de User dans la liste = " +userTreatement.size());
		while(true) {
			if (Thread.currentThread().isInterrupted() || stop) {
				logger.debug("Tracker stopping (1)");
				break;
			}


			logger.debug("Begin Tracker. Tracking " + userTreatement.size() + " users.");
			stopWatch.start();
			userTreatement.forEach(u -> tourGuideService.trackUserLocation(u));
			stopWatch.stop();
			logger.debug("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
			stopWatch.reset();
			break;
			/*
			try {
				logger.debug("Tracker sleeping");
				TimeUnit.SECONDS.sleep(trackingPollingInterval);
			} catch (InterruptedException e) {
				logger.debug("Tracker stopping (2)");
				break;
			}
			 */
		}
	}
}

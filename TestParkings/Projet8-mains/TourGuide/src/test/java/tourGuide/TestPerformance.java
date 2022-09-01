package tourGuide;

import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.tracker.Tracker;
import tourGuide.tracker.TrackerHighVolumeGetRewards;
import tourGuide.user.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPerformance {
	private final Logger logger = LoggerFactory.getLogger(Tracker.class);
	static final int numberTotalOfThreads = 40;
	static final int internalUserNumber = 100000;

	/*
	 * A note on performance improvements:
	 *     
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *     
	 *     		InternalTestHelper.setInternalUserNumber(100000);
	 *     
	 *     
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent. 
	 * 
	 *     These are performance metrics that we are trying to hit:
	 *     
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */
	
	@Test
	public void highVolumeTrackLocation() throws InterruptedException {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		InternalTestHelper.setInternalUserNumber(internalUserNumber);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

		List<Tracker> numberThreads = new ArrayList<>(numberTotalOfThreads);
		System.out.println("Début boucle new");
		for (int i = 0; i < numberTotalOfThreads; i ++) {
			Tracker tracker = new Tracker(tourGuideService);
			tracker.setName("Thibault-" + i);
			numberThreads.add(tracker);
		}
		logger.info("Nombre de thread = "+ numberThreads.size());

		int userInterval = internalUserNumber / numberTotalOfThreads;
		if (internalUserNumber % numberTotalOfThreads != 0) {
			userInterval++;
		}

		List<User> allUsers = Collections.synchronizedList(tourGuideService.getAllUsers());
		int b = 1;
		for (User user : allUsers){
			user.setName("Prénom User : " + b);
			b++;
		}

		logger.info("Début boucle start");
	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		int threadIdx = 0;
		int tailleListe = 0;
		int threadNb = 0;

		for (Tracker numberTrack : numberThreads) {
			if (threadIdx == numberTotalOfThreads - 1){
				numberTrack.setUserTreatement(allUsers.subList(threadNb, allUsers.size()));
			}else {
				numberTrack.setUserTreatement(allUsers.subList(threadNb, userInterval + threadNb));
			}

			for (User user : numberTrack.getUserTreatement()) {
				logger.info(user.getName());
			}
			numberTrack.start();
			threadNb = threadNb + userInterval;
			tailleListe = tailleListe + numberThreads.get(threadIdx++).getUserTreatement().size();
		}

		logger.info("Début boucle join");
		for (int i =0; i < numberThreads.size(); i++){
			logger.info("Attente fin thread " + i);
			numberThreads.get(i).join();
		}

		stopWatch.stop();
		logger.info("Fin boucle");
		tourGuideService.tracker.stopTracking();
		logger.info("Nombre de UserTreatement passé à la moulinette " + tailleListe);
		logger.info("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
	@Test
	public void highVolumeGetRewards() throws InterruptedException {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());

		// Users should be incremented up to 100,000, and test finishes within 20 minutes
		InternalTestHelper.setInternalUserNumber(internalUserNumber);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

		List<TrackerHighVolumeGetRewards> numberThreads = new ArrayList<>(numberTotalOfThreads);
		System.out.println("Début boucle new");
		for (int i = 0; i < numberTotalOfThreads; i ++) {
			TrackerHighVolumeGetRewards trackerHighVolumeGetRewards = new TrackerHighVolumeGetRewards(tourGuideService, rewardsService);
			trackerHighVolumeGetRewards.setName("Thibault-" + i);
			numberThreads.add(trackerHighVolumeGetRewards);
		}
		logger.info("Nombre de thread = "+ numberThreads.size());

		int userInterval = internalUserNumber / numberTotalOfThreads;
		if (internalUserNumber % numberTotalOfThreads != 0) {
			userInterval++;
		}

		List<User> allUsers = Collections.synchronizedList(tourGuideService.getAllUsers());
		int b = 1;
		for (User user : allUsers){
			user.setName("Prénom User : " + b);
			b++;
		}

		int threadIdx = 0;
		int tailleListe = 0;
		int threadNb = 0;

		for (TrackerHighVolumeGetRewards trackerHighVolumeGetRewards : numberThreads) {
			if (threadIdx == numberTotalOfThreads - 1){
				trackerHighVolumeGetRewards.setUserTreatement(allUsers.subList(threadNb, allUsers.size()));
			}else {
				trackerHighVolumeGetRewards.setUserTreatement(allUsers.subList(threadNb, userInterval + threadNb));
			}

			for (User user : trackerHighVolumeGetRewards.getUserTreatement()) {
				logger.info(user.getName());
			}
			trackerHighVolumeGetRewards.start();
			threadNb = threadNb + userInterval;
			tailleListe = tailleListe + numberThreads.get(threadIdx++).getUserTreatement().size();
		}

		logger.info("Début boucle join");
		for (int i =0; i < numberThreads.size(); i++){
			logger.info("Attente fin thread " + i);
			numberThreads.get(i).join();
		}

		for(User user : allUsers) {
			assertTrue(user.getUserRewards().size() > 0);
		}
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		logger.info("Nombre de UserTreatement passé à la moulinette " + tailleListe);
		logger.info("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
}

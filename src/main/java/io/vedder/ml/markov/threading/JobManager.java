package io.vedder.ml.markov.threading;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import io.vedder.ml.markov.ExampleMain;
import io.vedder.ml.markov.tokenizer.Tokenizer;

public class JobManager {

	static Logger log = Logger.getLogger(ExampleMain.class.getName());

	private List<Runnable> jobs = null;

	public JobManager() {
		jobs = new LinkedList<Runnable>();
	}

	public void addTokenizer(Tokenizer t) {
		this.addJob(new TokenizerJob(t));
	}

	public void addJob(Runnable t) {
		jobs.add(t);
	}

	public void runAll() {
		List<Thread> threads = new LinkedList<Thread>();
		for (Runnable r : jobs) {
			Thread th = new Thread(r);
			threads.add(th);
			th.start();
		}

		try {
			log.info("Awating all job completions...\n");
			for (Thread th : threads) {
				th.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("All jobs complete!\n");
	}
}

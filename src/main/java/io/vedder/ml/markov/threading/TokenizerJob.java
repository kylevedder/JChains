package io.vedder.ml.markov.threading;

import org.apache.log4j.Logger;

import io.vedder.ml.markov.tokenizer.Tokenizer;

public class TokenizerJob implements Runnable {

	static Logger log = Logger.getLogger(TokenizerJob.class);
	
	private Tokenizer tokenizer;

	public TokenizerJob(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public void run() {
		tokenizer.tokenize();
		log.info("Tokenizer job complete!\n");
		return;
	}
}

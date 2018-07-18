package io.vedder.ml.markov;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import io.vedder.ml.markov.consumer.TokenConsumer;
import io.vedder.ml.markov.consumer.file.FileTokenConsumer;
import io.vedder.ml.markov.generator.Generator;
import io.vedder.ml.markov.generator.file.FileGenerator;
import io.vedder.ml.markov.holder.MapTokenHolder;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.threading.JobManager;
import io.vedder.ml.markov.tokenizer.file.FileTokenizer;
import io.vedder.ml.markov.tokens.Token;

public class ExampleMain {

	static Logger log = Logger.getLogger(ExampleMain.class);
	public static boolean verbose = false;

	private static int lookback = 1;
	private static int mapInitialSize = 10000;
	private static int numSent = 1;
	private static List<String> filePaths = null;

	/**
	 * Example use of the Markov Chain library.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			args = new String[] {"-v", "-i", "50000", "3", "10", "-f", "inputs/shakespear.txt", "inputs/AllsWellThatEndsWell.txt"};
		}

		parseArgs(Arrays.asList(args));

		if (!verbose) {
			log.setLevel(Level.WARN);
		}

		// Data structure to hold tokens
		TokenHolder tokenHolder = new MapTokenHolder(mapInitialSize);
		
		JobManager jm = new JobManager();

		// Fills the TokenHolder with tokens
		for (String filePath : filePaths) {
			FileTokenizer fileTokenizer = new FileTokenizer(tokenHolder, lookback, filePath);
			jm.addTokenizer(fileTokenizer);
		}
		
		jm.runAll();

		// Uses the TokenHolder to generate Collections of tokens.
		Generator g = new FileGenerator(tokenHolder, lookback);

		// Takes Collections of tokens and consumes them
		TokenConsumer tc = new FileTokenConsumer();

		// Kicks off the tokenization process

		List<Collection<Token>> tokensCollections = new LinkedList<Collection<Token>>();

		// Creates Lists of tokens
		for (int i = 0; i < numSent / 2; i++) {
			tokensCollections.add(g.generateTokenList());
		}

		// Creates lazy collections of tokens
		for (int i = 0; i < (numSent / 2 + numSent % 2); i++) {
			tokensCollections.add(g.generateLazyTokenList());
		}

		// Consumer consumes both types of collections
		log.info("Printing Tokens...\n" + "===============\n");
		
		for(Collection<Token> l: tokensCollections) {
			tc.consume(l);
		}

	}

	/**
	 * Parses CL args and configures the application for launch.
	 * 
	 * @param args
	 */
	private static void parseArgs(List<String> args) {
		if (args.size() < 3 || !args.contains("-f")) {
			printUsageAndExit();
		}
		
		for(String a : args) {
			a = a.trim();
		}

		if (args.contains("-v")) {
			verbose = true;
		}

		if (args.contains("-i")) {
			int index = args.indexOf("-i");
			mapInitialSize = Integer.parseInt(args.get(index + 1));
		}

		int dashFIndex = args.indexOf("-f");

		String lookbackString = args.get(dashFIndex - 2);
		String numSentString = args.get(dashFIndex - 1);
		List<String> filePathStrings = args.subList(dashFIndex+1, args.size());

		lookback = Integer.parseInt(lookbackString);
		numSent = Integer.parseInt(numSentString);
		filePaths = filePathStrings;
	}

	private static void printUsageAndExit() {
		System.out.println("ARGS: [-v] [-i (map initial size)] <lookback> <number of sentences> [-f <input files>]");
		System.exit(-1);
	}
}

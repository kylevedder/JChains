package io.vedder.ml.markov;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import io.vedder.ml.markov.collection.TokenCollection;
import io.vedder.ml.markov.consumer.FileTokenConsumer;
import io.vedder.ml.markov.consumer.TokenConsumer;
import io.vedder.ml.markov.generator.Generator;
import io.vedder.ml.markov.generator.file.FileGenerator;
import io.vedder.ml.markov.holder.MapTokenHolder;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokenizer.file.FileTokenizer;
import io.vedder.ml.markov.tokens.Token;

public class ExampleMain {

	static Logger log = Logger.getLogger(ExampleMain.class.getName());
	public static boolean verbose = false;

	private static int lookback = 1;
	private static int mapInitialSize = 10000;
	private static int numSent = 1;
	private static String filePath = "";

	/**
	 * Example use of the Markov Chain library.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		args = new String[] { "-v", "-i", "50000", "3", "10", "inputs/shakespear.txt" };

		parseArgs(Arrays.asList(args));

		if (!verbose) {
			log.setLevel(Level.WARN);
		}

		log.info("Starting TokenHolder creation...\n");
		TokenHolder tokenHolder = new MapTokenHolder(mapInitialSize);

		log.info("Starting FileTokenizer creation...\n");
		FileTokenizer fileTokenizer = new FileTokenizer(tokenHolder, lookback, filePath);
		
		Generator g = new FileGenerator(tokenHolder, lookback);
		
		TokenConsumer tc = new FileTokenConsumer();		
		
		fileTokenizer.tokenize();

		log.info("Generating Token Lists...\n");
		List<Collection<Token>> tokensCollections = new LinkedList<>();		

		
		for (int i = 0; i < numSent; i++) {
			tokensCollections.add(g.generateTokenList());
		}
		
		
		for (int i = 0; i < numSent; i++) {
			tokensCollections.add( g.generateLazyTokenList());
		}
		

		log.info("Printing Tokens...\n" + "===============\n");
		tokensCollections.forEach(l -> tc.consume(l));

	}

	/**
	 * Parses CL args and configures the application for launch.
	 * 
	 * @param args
	 */
	private static void parseArgs(List<String> args) {
		if (args.size() < 3) {
			printUsageAndExit();
		}

		args = args.stream().map(a -> a.trim()).collect(Collectors.toList());

		if (args.contains("-v")) {
			verbose = true;
		}

		if (args.contains("-i")) {
			int index = args.indexOf("-i");
			mapInitialSize = Integer.parseInt(args.get(index + 1));
		}

		String lookbackString = args.get(args.size() - 3);
		String numSentString = args.get(args.size() - 2);
		String filepathString = args.get(args.size() - 1);

		lookback = Integer.parseInt(lookbackString);
		numSent = Integer.parseInt(numSentString);
		filePath = filepathString;
	}

	private static void printUsageAndExit() {
		System.out.println("ARGS: [-v] [-i (map initial size)] <lookback> <number of sentences> <input file>");
		System.exit(-1);
	}
}

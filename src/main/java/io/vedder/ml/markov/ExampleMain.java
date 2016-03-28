package io.vedder.ml.markov;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import io.vedder.ml.markov.consumer.TokenConsumer;
import io.vedder.ml.markov.consumer.file.FileTokenConsumer;
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

		//Data structure to hold tokens
		TokenHolder tokenHolder = new MapTokenHolder(mapInitialSize);

		//Fills the TokenHolder with tokens
		FileTokenizer fileTokenizer = new FileTokenizer(tokenHolder, lookback, filePath);
		
		//Uses the TokenHolder to generate Collections of tokens.
		Generator g = new FileGenerator(tokenHolder, lookback);
		
		//Takes Collections of tokens and consumes them
		TokenConsumer tc = new FileTokenConsumer();		
		
		//Kicks off the tokenization process
		fileTokenizer.tokenize();
		
		List<Collection<Token>> tokensCollections = new LinkedList<>();		
		
		//Creates Lists of tokens
		for (int i = 0; i < numSent/2; i++) {
			tokensCollections.add(g.generateTokenList());
		}
		
		//Creates lazy collections of tokens
		for (int i = 0; i < (numSent/2 + numSent % 2); i++) {
			tokensCollections.add( g.generateLazyTokenList());
		}
		
		//Consumer consumes both types of collections
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

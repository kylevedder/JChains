package io.vedder.ml.markov;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import io.vedder.ml.markov.tokenizer.FileTokenizer;
import io.vedder.ml.markov.tokens.Token;

public class Main {

	static Logger log = Logger.getLogger(Main.class.getName());
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
//		args = new String[] { "-v", "-i", "50000", "2", "10", "inputs/AllsWellThatEndsWell.txt" };

		parseArgs(Arrays.asList(args));

		if (!verbose) {
			log.setLevel(Level.WARN);
		}

		log.info("Starting TokenHolder creation...\n");
		TokenHolder<String> tokenHolder = new TokenHolder<>(lookback, mapInitialSize);
		
		log.info("Starting FileTokenizer creation...\n");
		FileTokenizer fileTokenizer = new FileTokenizer(tokenHolder, filePath);
		
		log.info("Starting token adds...\n");
		fileTokenizer.addTokensToHolder();

		log.info("Generating Token Lists...\n");
		List<List<Token>> tokensLists = fileTokenizer.generateTokenLists(numSent);
		
		log.info("Printing Tokens...\n" + "===============\n");
		tokensLists.forEach(l -> fileTokenizer.outputTokens(l));

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

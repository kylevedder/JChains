package io.vedder.ml.markov;

import java.util.List;

import io.vedder.ml.markov.tokenizer.FileTokenizer;
import io.vedder.ml.markov.tokens.Token;

public class Main {

	public static int lookback = 1;
	public static int numSent = 1;
	public static String filePath = "";

	public static void main(String[] args) {

//		args = new String[] { "3", "1", "AliceInWonderland.txt" };

		parseArgs(args);

		TokenHolder h = new TokenHolder(lookback);
		FileTokenizer ft = new FileTokenizer(h, filePath);

		ft.addTokens();

		List<List<Token>> tokensLists = ft.generateTokenLists(numSent);
		tokensLists.forEach(l -> ft.printTokens(l));

	}

	private static void parseArgs(String args[]) {
		if (args.length != 3) {
			printUsageAndExit();
		}

		String lookbackString = args[0];
		String numSentString = args[1];
		String filepathString = args[2];

		lookback = Integer.parseInt(lookbackString);
		numSent = Integer.parseInt(numSentString);
		filePath = filepathString;
	}

	private static void printUsageAndExit() {
		System.out.println("ARGS: <lookback> <number of sentences> <input file>");
		System.exit(-1);
	}
}

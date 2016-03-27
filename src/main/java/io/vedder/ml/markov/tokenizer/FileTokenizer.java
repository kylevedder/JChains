package io.vedder.ml.markov.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.Main;
import io.vedder.ml.markov.TokenHolder;
import io.vedder.ml.markov.tokens.DelimitToken;
import io.vedder.ml.markov.tokens.StringToken;
import io.vedder.ml.markov.tokens.Token;
import io.vedder.ml.markov.utils.Utils;

public class FileTokenizer extends Tokenizer<String> {

	static Logger log = Logger.getLogger(Main.class.getName());

	private final int LOOKBACK;
	private final Set<String> END_MARKS;
	private final DelimitToken DELIMIT_TOKEN = DelimitToken.getInstance();

	private final List<String> listStrings;

	
	public FileTokenizer(TokenHolder<String> th, int lookback, String filePath) {
		super(th);
		END_MARKS = new HashSet<>(Arrays.asList(".", "?", "!"));
		LOOKBACK = lookback;
		this.listStrings = splitStrings(Utils.readFile(filePath));
	}

	@Override
	public void addTokensToHolder() {
		List<Token> l = getTokens(this.listStrings);
		if (Main.verbose)
			System.out.println("Adding Tokens...");
		addTokenList(l);
	}

	private void addTokenList(List<Token> tokens) {
		log.info("Chunking " + tokens.size() + " tokens...\n");
		for (int wordIndex = LOOKBACK; wordIndex < tokens.size() - 1; wordIndex++) {

			if (wordIndex % 1000 == 0) {
				log.info(".");
			}
			if (wordIndex % 10000 == 0) {
				log.info(String.format("|%7d\n", wordIndex));
			}
			// List for the lookback
			List<Token> lookBackList = new ArrayList<>(this.LOOKBACK);

			Token t = null;

			// loop adds lists to ensure that lookback lists of size 1 to size
			// "lookBack" are added to the lookbackList
			chunkLoop: for (int lookBackCount = 0; lookBackCount < this.LOOKBACK; lookBackCount++) {
				t = tokens.get(wordIndex - lookBackCount);
				lookBackList.add(0, t);

				// constructor call is to copy lookBackList
				th.addToken(new LookbackContainer<>(lookBackList), tokens.get(wordIndex + 1));

				// if lookback hits delimiter token, stop
				if (t == DELIMIT_TOKEN) {
					break chunkLoop;
				}
			}
		}
		log.info("\n");
	}

	private List<Token> getTokens(List<String> listStrings) {
		List<Token> tokenList = new LinkedList<>();
		tokenList.add(DELIMIT_TOKEN);
		listStrings.forEach(s -> {
			tokenList.add(new StringToken(s));
			if (END_MARKS.contains(s)) {
				tokenList.add(DELIMIT_TOKEN);
			}
		});

		// check to see if ends with delimiter token
		if (tokenList.get(tokenList.size() - 1) != DELIMIT_TOKEN) {
			tokenList.add(DELIMIT_TOKEN);
		}
		return tokenList;
	}

	private List<String> splitStrings(List<String> lines) {
		// Regex from:
		// http://stackoverflow.com/questions/24222730/split-a-string-and-separate-by-punctuation-and-whitespace
		// Sorry about the functional mess...
		List<String> splits = lines.parallelStream()
				.map(l -> Arrays.asList(l.replaceAll("  ", " ")
						.split("\\s+|(?=\\W\\p{Punct}|\\p{Punct}\\W)|(?<=\\W\\p{Punct}|\\p{Punct}\\W})")))
				.flatMap(l -> l.stream()).filter(w -> !w.isEmpty() && !w.equals("")).collect(Collectors.toList());
		return splits;
	}

	@Override
	public List<List<Token>> generateTokenLists(int numLists) {
		List<List<Token>> lines = new LinkedList<>();
		for (int i = 0; i < numLists; i++) {
			List<Token> line = new ArrayList<>(100);

			LookbackContainer<String> c = new LookbackContainer<>(DELIMIT_TOKEN);
			Token t = null;
			while ((t = th.getNext(c)) != DELIMIT_TOKEN) {
				line.add(t);
				c.addToken(t, LOOKBACK);
			}
			lines.add(line);
		}
		return lines;
	}

	@Override
	public void outputTokens(List<Token> tokens) {
		List<String> punctuation = Arrays.asList(",", ";", ":", ".", "?", "!", "-");
		tokens.forEach(w -> {
			if (!punctuation.contains(w.toString())) {
				System.out.print(" ");
			}
			System.out.print(w.toString());
		});
		System.out.print("\n");
	}

}

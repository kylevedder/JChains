package io.vedder.ml.markov.tokenizer.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.ExampleMain;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokenizer.Tokenizer;
import io.vedder.ml.markov.tokens.Token;
import io.vedder.ml.markov.tokens.file.DelimitToken;
import io.vedder.ml.markov.tokens.file.StringToken;
import io.vedder.ml.markov.utils.Utils;

/**
 * Implementation of the {@link Tokenizer} for text files.
 * 
 * @author kyle
 *
 */
public class FileTokenizer extends Tokenizer {

	static Logger log = Logger.getLogger(ExampleMain.class.getName());

	private final int LOOKBACK;
	private final Set<String> END_MARKS;
	private final Token DELIMIT_TOKEN = DelimitToken.getInstance();
	private final String filePath;

	private List<String> listStrings = null;

	public FileTokenizer(TokenHolder th, int lookback, String filePath) {
		super(th);
		END_MARKS = new HashSet<>(Arrays.asList(".", "?", "!"));
		LOOKBACK = lookback;
		this.filePath = filePath;
	}

	@Override
	public void tokenize() {
		this.listStrings = splitStrings(Utils.readFile(filePath));
		addTokensToHolder();
	}

	private void addTokensToHolder() {
		List<Token> l = getTokens(this.listStrings);
		addTokenList(l);
	}

	private void addTokenList(List<Token> tokens) {
		log.info("Chunking " + tokens.size() + " tokens for file \""+ filePath +"\"...\n");
		for (int wordIndex = LOOKBACK; wordIndex < tokens.size() - 1; wordIndex++) {

			// List for the lookback
			List<Token> lookBackList = new ArrayList<>(this.LOOKBACK);

			Token t = null;

			// loop adds lists to ensure that lookback lists of size 1 to size
			// "lookBack" are added to the lookbackList
			chunkLoop: for (int lookBackCount = 0; lookBackCount < this.LOOKBACK; lookBackCount++) {
				t = tokens.get(wordIndex - lookBackCount);
				lookBackList.add(0, t);

				// constructor call is to copy lookBackList
				th.addToken(new LookbackContainer(this.LOOKBACK, lookBackList), tokens.get(wordIndex + 1));

				// if lookback hits delimiter token, stop
				if (t == DELIMIT_TOKEN) {
					break chunkLoop;
				}
			}
		}
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
}

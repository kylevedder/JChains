package io.vedder.ml.markov.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.Main;
import io.vedder.ml.markov.TokenHolder;
import io.vedder.ml.markov.tokens.StringToken;
import io.vedder.ml.markov.tokens.Token;
import io.vedder.ml.markov.utils.Utils;

public class FileTokenizer extends Tokenizer<String> {

	private final Set<String> END_MARKS;
	private final List<String> listStrings;

	public FileTokenizer(TokenHolder<String> th, String filePath) {
		super(th);
		END_MARKS = new HashSet<>(Arrays.asList(".", "?", "!"));
		this.listStrings = splitStrings(Utils.readFile(filePath));
	}

	@Override
	public void addTokensToHolder() {
		List<Token> l = getTokens(this.listStrings);
		if (Main.verbose)
			System.out.println("Adding Tokens...");
		th.addTokenList(l);
	}

	private List<Token> getTokens(List<String> listStrings) {
		List<Token> tokenList = new LinkedList<>();
		tokenList.add(th.getDelimitToken());
		listStrings.forEach(s -> {
			tokenList.add(new StringToken(s));
			if (END_MARKS.contains(s)) {
				tokenList.add(th.getDelimitToken());
			}
		});

		// check to see if ends with delimiter token
		if (tokenList.get(tokenList.size() - 1) != th.getDelimitToken()) {
			tokenList.add(th.getDelimitToken());
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

			LookbackContainer<String> c = new LookbackContainer<>(th.getDelimitToken());
			Token t = null;
			while ((t = th.getNext(c)) != th.getDelimitToken()) {
				line.add(t);
				c.addToken(t, th.getLookback());
			}
			lines.add(line);
		}
		return lines;
	}

	@Override
	public void printTokens(List<Token> tokens) {
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

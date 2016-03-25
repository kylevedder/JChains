package io.vedder.ml.markov.tokenizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.TokenHolder;
import io.vedder.ml.markov.tokens.StringToken;
import io.vedder.ml.markov.tokens.Token;

public class FileTokenizer extends Tokenizer {

	private final List<String> END_MARKS;
	private final String filePath;

	public FileTokenizer(TokenHolder th, String filePath) {
		super(th);
		END_MARKS = Arrays.asList(".", "?", "!");
		this.filePath = filePath;
		new File(filePath).toPath();// checks to ensure that file can be
									// accessed, i.e. will throw error if fails
	}

	@Override
	public void addTokens() {
		th.add(getTokens());
	}

	private List<Token> getTokens() {
		List<String> listStrings = listStrings(filePath);
		List<Token> tokenList = new LinkedList<>();
		tokenList.add(th.getDelimitToken());
		listStrings.forEach(s -> {
			tokenList.add(new StringToken(s));
			if (END_MARKS.contains(s)) {
				tokenList.add(th.getDelimitToken());
			}
		});
		if (tokenList.get(tokenList.size() - 1) != th.getDelimitToken()) {
			tokenList.add(th.getDelimitToken());
		}
		return tokenList;
	}

	private List<String> listStrings(String filePath) {
		return tokenize(readFile(filePath));
	}

	private List<String> tokenize(List<String> lines) {
		// Regex from:
		// http://stackoverflow.com/questions/24222730/split-a-string-and-separate-by-punctuation-and-whitespace
		List<String> tokens = lines.parallelStream()
				.map(l -> Arrays.asList(l.replaceAll("  ", " ")
						.split("\\s+|(?=\\W\\p{Punct}|\\p{Punct}\\W)|(?<=\\W\\p{Punct}|\\p{Punct}\\W})")))// ("\\s+|(?=\\p{Punct})|(?<=\\p{Punct})")))
				.flatMap(l -> l.stream()).filter(w -> !w.isEmpty() && !w.equals("")).collect(Collectors.toList());
		return tokens;
	}

	private List<String> readFile(String filePath) {
		List<String> lines = new LinkedList<>();
		try {
			Files.lines(new File(filePath).toPath()).forEach(l -> lines.add(l + "\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	@Override
	public List<List<Token>> generateTokenLists(int numLists) {
		List<List<Token>> lines = new LinkedList<>();
		for (int i = 0; i < numLists; i++) {
			List<Token> line = new ArrayList<>(1000);

			LookbackContainer c = new LookbackContainer(th.getDelimitToken());
			Token t = null;
			while ((t = th.getNext(c)) != th.getDelimitToken()) {
				line.add(t);
				c.add(t, th.getLookback());
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

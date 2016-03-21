package io.vedder.ml.markov.tokenizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import io.vedder.ml.markov.TokenHolder;
import io.vedder.ml.markov.tokens.StringToken;
import io.vedder.ml.markov.tokens.Token;

public class FileTokenizer {

	private final List<String> END_MARKS;
	private TokenHolder th;

	public FileTokenizer(TokenHolder th) {
		END_MARKS = Arrays.asList(".", "?", "!");
		this.th = th;
	}

	public List<Token> getTokens(String filePath) {
		List<String> listStrings = listStrings(filePath);
		List<Token> tokenList = new ArrayList<>(1000);
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

}

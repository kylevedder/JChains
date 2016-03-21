package io.vedder.ml.markov;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.vedder.ml.markov.tokenizer.FileTokenizer;
import io.vedder.ml.markov.tokens.Token;
import io.vedder.ml.markov.utils.Utils;

public class Main {

	public static final int LOOKBACK = 3;

	static TokenHolder h;
	static FileTokenizer ft;
	static List<Token> tokens;

	public static void main(String[] args) {
		h = new TokenHolder(LOOKBACK);
		ft = new FileTokenizer(h);
		tokens = ft.getTokens("shakespear.txt");
		h.add(tokens);

		List<List<String>> lines = generate(10);
		lines.forEach(l -> l.forEach(w -> System.out.print(w)));

	}

	public static List<List<String>> generate(int numSent) {

		List<String> punctuation = Arrays.asList(",", ";", ":", ".", "?", "!", "-");

		List<List<String>> lines = new LinkedList<>();

		Utils.writeToFile("log.txt", h.toString());
		for (int i = 0; i < numSent; i++) {
			List<String> line = new LinkedList<>();

			// line.add(String.valueOf(i));
			LookbackContainer c = new LookbackContainer(h.getDelimitToken());
			Token t = null;
			while ((t = h.getNext(c)) != h.getDelimitToken()) {
				if (!punctuation.contains(t.toString()))
					line.add(" ");
				line.add(t.toString());

				c.add(t, LOOKBACK);
			}
			line.add("\n");
			lines.add(line);
		}
		return lines;
	}

}

package io.vedder.ml.markov.consumer;

import io.vedder.ml.markov.tokens.Token;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StringBuilderTokenConsumer extends TokenConsumer {
	private static final List<String> PUNCTUATION = Arrays.asList(",", ";", ":", ".", "?", "!", "-");

	private final StringBuilder builder = new StringBuilder();

	@Override
	public void consume(Collection<Token> collection) {
		collection.forEach((w) -> {
			if (!PUNCTUATION.contains(w.toString())) {
				builder.append(" ");
			}

			builder.append(w.toString());
		});

		builder.append("\n");
	}

	public StringBuilder getBuilder() {
		return builder;
	}
}

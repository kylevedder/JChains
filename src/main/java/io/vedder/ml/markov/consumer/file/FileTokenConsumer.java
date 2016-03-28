package io.vedder.ml.markov.consumer.file;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.vedder.ml.markov.consumer.TokenConsumer;
import io.vedder.ml.markov.tokens.Token;

public class FileTokenConsumer extends TokenConsumer {

	@Override
	public void consume(Collection<Token> collection) {
		List<String> punctuation = Arrays.asList(",", ";", ":", ".", "?", "!", "-");
		collection.forEach(w -> {
			if (!punctuation.contains(w.toString())) {
				System.out.print(" ");
			}
			System.out.print(w.toString());
		});
		System.out.print("\n");
	}

}

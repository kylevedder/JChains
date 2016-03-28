package io.vedder.ml.markov.consumer;

import java.util.Collection;

import io.vedder.ml.markov.tokens.Token;

public abstract class TokenConsumer {
	public TokenConsumer() {
	}

	public abstract void consume(Collection<Token> collection);

}

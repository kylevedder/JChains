package io.vedder.ml.markov.tokenizer;

import java.util.List;

import io.vedder.ml.markov.TokenHolder;
import io.vedder.ml.markov.tokens.Token;

public abstract class Tokenizer<T> {

	protected final TokenHolder<T> th;

	public Tokenizer(TokenHolder<T> th) {
		this.th = th;
	}

	public abstract void addTokensToHolder();

	public abstract List<List<Token>> generateTokenLists(int numLists);

	public abstract void printTokens(List<Token> tokens);
}

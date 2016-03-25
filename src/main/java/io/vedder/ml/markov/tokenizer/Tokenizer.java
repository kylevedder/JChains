package io.vedder.ml.markov.tokenizer;

import java.util.List;

import io.vedder.ml.markov.TokenHolder;
import io.vedder.ml.markov.tokens.Token;

public abstract class Tokenizer {
	
	protected final TokenHolder th;

	public Tokenizer(TokenHolder th) {
		this.th = th;
	}

	public abstract void addTokens();
	
	public abstract List<List<Token>> generateTokenLists(int numLists);
	
	public abstract void printTokens(List<Token> tokens);
}

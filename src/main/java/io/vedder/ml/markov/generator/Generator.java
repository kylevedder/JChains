package io.vedder.ml.markov.generator;

import java.util.List;

import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;

public abstract class Generator {
	private TokenHolder th;

	public Generator(TokenHolder th) {
		this.th = th;
	}
	
	public abstract List<Token> generateTokenList();

}

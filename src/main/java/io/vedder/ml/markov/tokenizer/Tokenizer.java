package io.vedder.ml.markov.tokenizer;

import io.vedder.ml.markov.holder.TokenHolder;

/**
 * Base class for all Tokenizers. Provides basic access to TokenHolders.
 * 
 * @author kyle
 *
 * @param <T>
 */
public abstract class Tokenizer {

	protected final TokenHolder th;

	public Tokenizer(TokenHolder th) {
		this.th = th;
	}
	
	public abstract void tokenize();

//	public abstract List<Token> generateTokenList();
//
//	public abstract void outputTokens(List<Token> tokens);
}

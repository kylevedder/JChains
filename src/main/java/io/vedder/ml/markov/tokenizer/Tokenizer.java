package io.vedder.ml.markov.tokenizer;

import java.util.List;

import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;

/**
 * Base class for all Tokenizers. Provides basic access to TokenHolders.
 * 
 * @author kyle
 *
 * @param <T>
 */
public abstract class Tokenizer<T> {

	protected final TokenHolder<T> th;

	public Tokenizer(TokenHolder<T> th) {
		this.th = th;
	}

	public abstract List<Token> generateTokenList();

	public abstract void outputTokens(List<Token> tokens);
}

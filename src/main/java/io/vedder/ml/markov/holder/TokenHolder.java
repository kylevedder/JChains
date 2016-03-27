package io.vedder.ml.markov.holder;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.tokens.Token;

/**
 * Data structure for storing and retrieving {@link LookbackContainer}s and Tokens.
 * @param mapInitialSize.
 * 
 * Different TokenHolders have different performance characteristics.
 */
public interface TokenHolder<T> {
	
	/**
	 * Adds an edge between the given {@link LookbackContainer} and the given {@link Token}.
	 * @param lbc
	 * @param next
	 */
	public void addToken(LookbackContainer<T> lbc, Token next);
	
	/**
	 * Selects the next best {@link Token} from the given {@link LookbackContainer}.
	 * @param look
	 * @return
	 */
	public Token getNext(LookbackContainer<T> look);
}

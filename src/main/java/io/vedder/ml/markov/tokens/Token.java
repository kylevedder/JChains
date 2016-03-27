package io.vedder.ml.markov.tokens;

/**
 * Base class for all Tokens.
 * 
 * Requires an implementation of hashCode() and equals()
 * 
 * @author kyle
 *
 */
public abstract class Token {

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

}

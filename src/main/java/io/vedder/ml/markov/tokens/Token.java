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

	/**
	 * Two {@link Token}s with the same internal data will have the same hash code. 
	 */
	public abstract int hashCode();

	/**
	 * Two {@link Token}s with the same internal data will be equal. 
	 */
	public abstract boolean equals(Object obj);

}

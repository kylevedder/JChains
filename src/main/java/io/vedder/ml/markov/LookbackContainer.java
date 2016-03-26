package io.vedder.ml.markov;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.vedder.ml.markov.tokens.Token;

public class LookbackContainer<T> {
	private List<Token> tokenList = null;

	public LookbackContainer(Token ts) {
		tokenList = new LinkedList<>(Arrays.asList(ts));
	}

	public LookbackContainer(List<Token> ts) {
		tokenList = ts;
	}

	/**
	 * Adds a token while ensuring that the maximum size property is maintained.
	 * 
	 * @param token
	 * @param maxSize
	 */
	public void addToken(Token token, int maxSize) {
		if (tokenList == null) {
			tokenList = new LinkedList<>();
		}

		if (tokenList.size() >= maxSize) {
			tokenList = tokenList.subList(tokenList.size() - maxSize + 1, tokenList.size());
		}
		tokenList.add(token);
	}

	/**
	 * Returns if this container is empty.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return this.tokenList.isEmpty();
	}

	public LookbackContainer<T> shrinkContainer() {
		List<Token> lst = new LinkedList<>(tokenList);
		if (!lst.isEmpty()) {
			lst.remove(0);
		}
		return new LookbackContainer<T>(lst);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tokenList == null) ? 0 : tokenList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LookbackContainer other = (LookbackContainer) obj;
		if (tokenList == null) {
			if (other.tokenList != null)
				return false;
		} else if (!tokenList.equals(other.tokenList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LookbackContainer [tokenList=" + tokenList + "]";
	}

}

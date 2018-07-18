package io.vedder.ml.markov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.vedder.ml.markov.tokens.Token;

public class LookbackContainer {
	private LinkedList<Token> tokenList = null;

	private final int MAX_SIZE;

	public LookbackContainer(int maxSize, Token ts) {
		this(maxSize, Arrays.asList(ts));
	}

	public LookbackContainer(int maxSize, List<Token> ts) {
		MAX_SIZE = maxSize;
		tokenList = new ArrayList<Token>(ts);
	}

	/**
	 * Adds a token while ensuring that the maximum size property is maintained.
	 * 
	 * @param token
	 * @param maxSize
	 */
	public void addToken(Token token) {
		if (tokenList == null) {
			tokenList = new LinkedList<Token>();
		}

		while (tokenList.size() > MAX_SIZE)
			tokenList.removeFirst();

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

	public LookbackContainer shrinkContainer() {
		List<Token> lst = new LinkedList<Token>(tokenList);
		if (!lst.isEmpty()) {
			lst.remove(0);
		}
		return new LookbackContainer(MAX_SIZE - 1, lst);
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

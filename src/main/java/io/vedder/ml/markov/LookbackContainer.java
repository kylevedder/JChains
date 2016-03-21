package io.vedder.ml.markov;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.vedder.ml.markov.tokens.Token;

public class LookbackContainer {
	List<Token> tokenList = null;

	public LookbackContainer(Token... ts) {
		tokenList = Arrays.asList(ts);
	}

	public LookbackContainer(List<Token> ts) {
		tokenList = ts;
	}

	public void add(Token t, int maxSize) {
		List<Token> tl = new LinkedList<>();
		if (tokenList.size() >= maxSize) {
			Iterator<Token> itr = tokenList.listIterator(tokenList.size() - maxSize);

			while (itr.hasNext()) {
				tl.add(itr.next());
			}
		}
		tl.add(t);
		tokenList = tl;
	}

	public boolean isEmpty() {
		return this.tokenList.isEmpty();
	}

	public LookbackContainer shrinkContainer() {
		List<Token> lst = new LinkedList<>(tokenList);
		if (!lst.isEmpty())
			lst.remove(0);
		return new LookbackContainer(lst);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int count = 1;
		for (Token t : tokenList)
			result *= count++ * prime * result + ((tokenList == null) ? 0 : t.hashCode());
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

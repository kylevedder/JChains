package io.vedder.ml.markov.collection;

import java.util.Collection;

import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;

public abstract class TokenCollection implements Collection<Token> {

	protected TokenHolder th;

	public TokenCollection(TokenHolder th) {
		this.th = th;
	}

	@Override
	public boolean isEmpty() {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public int size() {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public boolean contains(Object o) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public Object[] toArray() {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public boolean add(Token e) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public boolean remove(Object o) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public boolean addAll(Collection<? extends Token> c) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	@Override
	public void clear() {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

}

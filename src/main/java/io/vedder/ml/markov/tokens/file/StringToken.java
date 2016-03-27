package io.vedder.ml.markov.tokens.file;

import io.vedder.ml.markov.tokens.Token;

/**
 * Token for representing a single word.
 * 
 * @author kyle
 *
 */
public class StringToken extends Token {

	private final String data;

	public StringToken(String data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		StringToken other = (StringToken) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.data;
	}

}

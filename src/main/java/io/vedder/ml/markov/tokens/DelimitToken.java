package io.vedder.ml.markov.tokens;

public class DelimitToken extends Token {
	private static final String TOKEN_STRING = "<DELIMIT TOKEN>";

	private static DelimitToken t = null;

	public static DelimitToken getInstance() {
		if (t == null) {
			t = new DelimitToken();
		}
		return t;
	}

	private DelimitToken() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TOKEN_STRING == null) ? 0 : TOKEN_STRING.hashCode());
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
		DelimitToken other = (DelimitToken) obj;
		if (TOKEN_STRING == null) {
			if (other.TOKEN_STRING != null)
				return false;
		} else if (!TOKEN_STRING.equals(other.TOKEN_STRING))
			return false;
		return true;
	}

}

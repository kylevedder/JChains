package io.vedder.ml.markov.tokens;

public class DelimitToken extends Token<String> {
	private static final String TOKEN_STRING = "<DELIMIT TOKEN>";

	private static DelimitToken t = null;

	public static DelimitToken getInstance() {
		if (t == null) {
			t = new DelimitToken();
		}
		return t;
	}

	private DelimitToken() {
		super(TOKEN_STRING);
	}

}

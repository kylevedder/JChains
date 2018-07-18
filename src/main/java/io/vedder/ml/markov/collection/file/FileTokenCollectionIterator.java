package io.vedder.ml.markov.collection.file;

import java.util.Iterator;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;
import io.vedder.ml.markov.tokens.file.DelimitToken;

public class FileTokenCollectionIterator implements Iterator<Token> {

	private TokenHolder th;
	private final Token DELIMIT_TOKEN = DelimitToken.getInstance();

	private Token currentToken;

	private final LookbackContainer c;

	public FileTokenCollectionIterator(TokenHolder th, int lookBack) {
		super();
		this.th = th;
		c = new LookbackContainer(lookBack, DELIMIT_TOKEN);
		this.currentToken = nextToken();
	}

	private Token nextToken() {
		Token t = th.getNext(c);
		c.addToken(t);
		return t;
	}

	public boolean hasNext() {
		return (currentToken != null && currentToken != DELIMIT_TOKEN);
	}

	public Token next() {
		Token t = currentToken;
		currentToken = nextToken();
		return t;
	}

}

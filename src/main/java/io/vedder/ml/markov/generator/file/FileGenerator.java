package io.vedder.ml.markov.generator.file;

import java.util.LinkedList;
import java.util.List;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.collection.TokenCollection;
import io.vedder.ml.markov.collection.file.FileTokenCollection;
import io.vedder.ml.markov.generator.Generator;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;
import io.vedder.ml.markov.tokens.file.DelimitToken;

public class FileGenerator extends Generator {

	private final int LOOKBACK;
	private final Token DELIMIT_TOKEN = DelimitToken.getInstance();

	public FileGenerator(TokenHolder th, int lookback) {
		super(th);
		this.LOOKBACK = lookback;
	}

	@Override
	public List<Token> generateTokenList() {
		List<Token> line = new LinkedList<>();

		LookbackContainer c = new LookbackContainer(LOOKBACK, DELIMIT_TOKEN);
		Token t = null;
		while ((t = th.getNext(c)) != DELIMIT_TOKEN && t != null) {
			line.add(t);
			c.addToken(t);
		}
		return line;
	}

	@Override
	public TokenCollection generateLazyTokenList() {
		return new FileTokenCollection(th, LOOKBACK);
	}

}

package io.vedder.ml.markov.generator.file;

import java.util.List;

import io.vedder.ml.markov.generator.Generator;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;

public class FileGenerator extends Generator {

	public FileGenerator(TokenHolder th) {
		super(th);
	}

	@Override
	public List<Token> generateTokenList() {
		return null;
	}

}

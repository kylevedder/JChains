package io.vedder.ml.markov;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import io.vedder.ml.markov.tokens.DelimitToken;
import io.vedder.ml.markov.tokens.Token;

public class TokenHolder {
	private Map<LookbackContainer, List<NextWordContainer<Token, Integer>>> tokenMap;
	private Random r = null;

	private Token delimitToken;

	private int lookBack = 0;

	public TokenHolder(int lookBack) {
		this.lookBack = lookBack;
		tokenMap = new HashMap<>();
		r = new Random();
		this.delimitToken = new DelimitToken();

		// tokenMap.put(new LookbackContainer(endToken),
		// Arrays.asList(new NextWordContainer<Token, Integer>(startToken, 1)));
		// tokenMap.put(new LookbackContainer(startToken), new LinkedList<>());
		tokenMap.put(new LookbackContainer(delimitToken), new LinkedList<>());
	}

	public void add(List<Token> tokens) {
		for (int wordIndex = lookBack; wordIndex < tokens.size() - 1; wordIndex++) {
			List<List<Token>> lookBackQueueList = new LinkedList<>();
			List<Token> lookBackQueue = new LinkedList<>();
			Token t;
			for (int lookBackCount = 0; lookBackCount < lookBack; lookBackCount++) {
				t = tokens.get(wordIndex - lookBackCount);
				lookBackQueue.add(0, t);
				lookBackQueueList.add(new LinkedList<>(lookBackQueue));
				if (t == delimitToken) {
					break;
				}
			}
			for (List<Token> l : lookBackQueueList) {
				this.add(l, tokens.get(wordIndex + 1));
			}
		}
	}

	public void add(List<Token> prev, Token next) {
		List<NextWordContainer<Token, Integer>> nextElementList = null;
		if (tokenMap.containsKey(new LookbackContainer(prev))) {
			nextElementList = tokenMap.get(new LookbackContainer(prev));
		} else {
			nextElementList = new LinkedList<>();
		}

		boolean foundPrevEntry = false;

		if (!nextElementList.isEmpty()) {
			for (NextWordContainer<Token, Integer> container : nextElementList) {
				if (container.getElem1().equals(next)) {
					container.setElem2(container.getElem2() + 1);
					foundPrevEntry = true;
					break;
				}
			}
		}

		if (!foundPrevEntry) {
			nextElementList.add(new NextWordContainer<Token, Integer>(next, 1));
		}

		tokenMap.put(new LookbackContainer(prev), nextElementList);

	}

	public Token getDelimitToken() {
		return delimitToken;
	}

	public Token getNext(LookbackContainer look) {
		List<NextWordContainer<Token, Integer>> nextElementList = null;// tokenMap.get(look);

		while (!look.isEmpty() && (nextElementList = tokenMap.get(look)) == null) {
			look = look.shrinkContainer();
		}

		if (nextElementList == null)
			return null;

		int sum = 0;
		for (NextWordContainer<Token, Integer> container : nextElementList) {
			sum += container.getElem2();
		}

		int randInt = r.nextInt(sum) + 1;
		for (NextWordContainer<Token, Integer> container : nextElementList) {
			if (randInt <= container.getElem2()) {
				return container.getElem1();
			}
			randInt -= container.getElem2();
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("TokenHolder [tokenMap=");
		Set<Entry<LookbackContainer, List<NextWordContainer<Token, Integer>>>> s = tokenMap.entrySet();
		for (Entry<LookbackContainer, List<NextWordContainer<Token, Integer>>> e : s) {
			sb.append(e.toString() + "\n");
		}
		sb.append("r=" + r + ", lookBack=" + lookBack + "]");

		return sb.toString();
	}

}

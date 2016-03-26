package io.vedder.ml.markov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.Random;

import io.vedder.ml.markov.tokens.DelimitToken;
import io.vedder.ml.markov.tokens.Token;

public class TokenHolder<T> {
	static Logger log = Logger.getLogger(Main.class.getName());

	private Map<LookbackContainer<T>, Map<Token<T>, Integer>> tokenMap;
	private Random r = null;

	private DelimitToken delimitToken = DelimitToken.getInstance();

	private int lookBack = 0;

	public TokenHolder(int lookBack, int mapInitialSize) {
		r = new Random();
		this.lookBack = lookBack;
		tokenMap = new HashMap<>(mapInitialSize);
		tokenMap.put(new LookbackContainer(delimitToken), new HashMap<>());
	}

	public int getLookback() {
		return lookBack;
	}

	public void addTokenList(List<Token<T>> tokens) {
		log.info("Chunking " + tokens.size() + " tokens...\n");
		for (int wordIndex = lookBack; wordIndex < tokens.size() - 1; wordIndex++) {

			if (wordIndex % 1000 == 0) {
				log.info(".");
			}
			if (wordIndex % 10000 == 0) {
				log.info(String.format("|%7d\n", wordIndex));
			}
			// List for the lookback
			List<Token<T>> lookBackList = new ArrayList<>(lookBack);

			Token<T> t = null;

			// loop adds lists to ensure that lookback lists of size 1 to size
			// "lookBack" are added to the lookbackList
			chunkLoop: for (int lookBackCount = 0; lookBackCount < lookBack; lookBackCount++) {
				t = tokens.get(wordIndex - lookBackCount);
				lookBackList.add(0, t);

				// constructor call is to copy lookBackList
				this.addToken(new ArrayList<>(lookBackList), tokens.get(wordIndex + 1));

				// if lookback hits delimiter token, stop
				if (t == delimitToken) {
					break chunkLoop;
				}
			}
		}
		log.info("\n");
	}

	public void addToken(List<Token<T>> prev, Token<T> next) {
		Map<Token<T>, Integer> nextElementMap = null;
		LookbackContainer<T> lbc = new LookbackContainer<T>(prev);
		if (tokenMap.containsKey(lbc)) {
			nextElementMap = tokenMap.get(lbc);
		} else {
			nextElementMap = new HashMap<>();
		}

		if (!nextElementMap.isEmpty() && nextElementMap.containsKey(next)) {
			nextElementMap.put(next, nextElementMap.get(next) + 1);

		} else {
			nextElementMap.put(next, 1);
		}
		tokenMap.put(lbc, nextElementMap);

	}

	public DelimitToken getDelimitToken() {
		return delimitToken;
	}

	/**
	 * Returns the next token given the LookbackContainer
	 * 
	 * @param look
	 * @return
	 */
	public Token<T> getNext(LookbackContainer<T> look) {
		Map<Token<T>, Integer> nextElementList = null;

		// Look for the largest lookback container which has a match. May be
		// empty.
		while (!look.isEmpty() && (nextElementList = tokenMap.get(look)) == null) {
			look = look.shrinkContainer();
		}

		if (nextElementList == null)
			return null;

		int sum = 0;
		// calculate sum
		for (Entry<Token<T>, Integer> entry : nextElementList.entrySet()) {
			sum += entry.getValue();
		}

		int randInt = r.nextInt(sum) + 1;
		for (Entry<Token<T>, Integer> entry : nextElementList.entrySet()) {
			if (randInt <= entry.getValue()) {
				return entry.getKey();
			}
			randInt -= entry.getValue();
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		List<String> lst = new LinkedList<>();
		for (Entry<LookbackContainer<T>, Map<Token<T>, Integer>> e : tokenMap.entrySet()) {
			lst.add(e.toString() + "\n");

		}
		Collections.sort(lst);

		lst.forEach(l -> sb.append(l));

		return sb.toString();
	}

}

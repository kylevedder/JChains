package io.vedder.ml.markov;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import io.vedder.ml.markov.tokens.Token;

public class TokenHolder<T> {
	

	private Map<LookbackContainer<T>, Map<Token, Integer>> tokenMap;
	private Random r = null;

	/**
	 * Data structure for storing and retrieving {@link LookbackContainer}s and Tokens.
	 * @param mapInitialSize
	 */
	public TokenHolder(int mapInitialSize) {
		r = new Random();
		tokenMap = new HashMap<>(mapInitialSize);
	}


	/**
	 * Adds a reference between the {@link LookbackContainer} and the
	 * {@link Token}
	 * 
	 * @param lbc
	 * @param next
	 */
	public void addToken(LookbackContainer<T> lbc, Token next) {
		Map<Token, Integer> nextElementMap = null;
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

	/**
	 * Returns the next token given the LookbackContainer.
	 * 
	 * @param look
	 * @return
	 */
	public Token getNext(LookbackContainer<T> look) {
		Map<Token, Integer> nextElementList = null;

		// Look for the largest lookback container which has a match. May be
		// empty.
		while (!look.isEmpty() && (nextElementList = tokenMap.get(look)) == null) {
			look = look.shrinkContainer();
		}

		if (nextElementList == null) {
			throw new RuntimeException("Unable to find match to given input");
		}

		int sum = 0;
		// calculate sum
		for (Entry<Token, Integer> entry : nextElementList.entrySet()) {
			sum += entry.getValue();
		}

		int randInt = r.nextInt(sum) + 1;
		for (Entry<Token, Integer> entry : nextElementList.entrySet()) {
			if (randInt <= entry.getValue()) {
				return entry.getKey();
			}
			randInt -= entry.getValue();
		}

		throw new RuntimeException("Failed to get next token");
	}

	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		List<String> lst = new LinkedList<>();
		for (Entry<LookbackContainer<T>, Map<Token, Integer>> e : tokenMap.entrySet()) {
			lst.add(e.toString() + "\n");

		}
		Collections.sort(lst);

		lst.forEach(l -> sb.append(l));

		return sb.toString();
	}

}

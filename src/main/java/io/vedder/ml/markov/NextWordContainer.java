package io.vedder.ml.markov;

public class NextWordContainer<T, U> {
	private T elem1;
	private U elem2;

	public NextWordContainer(T elem1, U elem2) {
		this.elem1 = elem1;
		this.elem2 = elem2;
	}

	public T getElem1() {
		return elem1;
	}

	public U getElem2() {
		return elem2;
	}

	public void setElem1(T elem1) {
		this.elem1 = elem1;
	}

	public void setElem2(U elem2) {
		this.elem2 = elem2;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elem1 == null) ? 0 : elem1.hashCode());
		result = prime * result + ((elem2 == null) ? 0 : elem2.hashCode());
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
		NextWordContainer other = (NextWordContainer) obj;
		if (elem1 == null) {
			if (other.elem1 != null)
				return false;
		} else if (!elem1.equals(other.elem1))
			return false;
		if (elem2 == null) {
			if (other.elem2 != null)
				return false;
		} else if (!elem2.equals(other.elem2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NextWordContainer [elem1=" + elem1 + ", elem2=" + elem2 + "]";
	}

}

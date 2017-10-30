package jseqa.util;

public class Pair<K, V> implements Comparable<Pair<K, V>> {

	private K key;
	private V value;
	
	public Pair(K key, V val) {
		this.key = key;
		this.value = val;
	}
	
	public K first() {
		return this.key;
	}
	
	public K key() {
		return this.key;
	}
	
	public V second() {
		return this.value;
	}
	
	public V value() {
		return this.value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
    public boolean equals(final Object obj) {
		if(obj == this) {
			return true;
		}
		if(obj instanceof Pair<?,?>) {
			Pair<K,V> other = (Pair<K,V>)obj;
			return (this.key.equals(other.key) && 
					this.value.equals(other.value));
		}
		return false;
    }

	@Override
	public int compareTo(Pair<K, V> o) {
		return 0;
	}
	
	@Override
	public String toString() {
		return "[" + this.key + ":" + this.value + "]";
	}
}

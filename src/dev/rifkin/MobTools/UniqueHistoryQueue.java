package dev.rifkin.MobTools;

import java.util.HashSet;
import java.util.LinkedList;

/*
 * Utility data structure for BFS
 * Ensures no duplicate queue entries as well as remembers queue element history
 */
class UniqueHistoryQueue<T> {
	private final LinkedList<T> queue = new LinkedList<T>();
	private final HashSet<T> set = new HashSet<T>();
	public void push(T item) {
		if(set.add(item)) {
			queue.add(item);
		}
	}
	public T pop() {
		return queue.remove();
	}
	public void clear() {
		queue.clear();
		set.clear();
	}
	public boolean empty() {
		return queue.size() == 0;
	}
}

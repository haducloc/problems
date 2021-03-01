package com.problems.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class PreTagsModel implements Serializable {
	private static final long serialVersionUID = 1L;

	final List<String> tags;
	private Map<String, Integer> indexes;

	public PreTagsModel() {
		this(Collections.emptyList());
	}

	public PreTagsModel(List<String> tags) {
		this.tags = Collections.unmodifiableList(tags);
		this.indexes = Collections.unmodifiableMap(buildIndexes(tags));
	}

	public List<String> getTags() {
		return tags;
	}

	public int getIndex(String tag, int defaultValue) {
		return this.indexes.getOrDefault(tag, defaultValue);
	}

	static Map<String, Integer> buildIndexes(List<String> tags) {
		Map<String, Integer> m = new HashMap<>();
		int idx = 0;
		for (String tag : tags) {
			m.put(tag, idx++);
		}
		return m;
	}
}

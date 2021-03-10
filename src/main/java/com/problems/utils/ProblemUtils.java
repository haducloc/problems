package com.problems.utils;

import java.util.Set;

import com.appslandia.common.utils.CollectionUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ProblemUtils {

	static final Set<String> LANGUAGES = CollectionUtils.unmodifiableSet("java", "c", "cpp", "r", "julia", "python", "javascript", "clike", "sql");
	public static final String DEFAULT_LANGUAGE = "java";

	public static String parseLanguage(String impls) {
		if (impls == null)
			return DEFAULT_LANGUAGE;

		for (String language : LANGUAGES) {
			String marker = "///// " + language;
			if (impls.startsWith(marker))
				return language;
		}

		return DEFAULT_LANGUAGE;
	}
}

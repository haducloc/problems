package com.problems.formatters;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.appslandia.common.base.FormatProvider;
import com.appslandia.common.base.Out;
import com.appslandia.common.formatters.DbTagsFormatter;
import com.appslandia.common.formatters.Formatter;
import com.appslandia.common.formatters.FormatterException;
import com.appslandia.common.utils.CollectionUtils;
import com.appslandia.common.utils.StringUtils;
import com.appslandia.common.utils.TagUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class AlgDbTagsFormatter implements Formatter {

	public static final String ERROR_MSG_KEY = DbTagsFormatter.class.getName() + ".message";

	@Override
	public String getErrorMsgKey() {
		return ERROR_MSG_KEY;
	}

	@Override
	public Class<?> getArgType() {
		return String.class;
	}

	@Override
	public String format(Object obj, FormatProvider formatProvider) {
		if (obj == null) {
			return null;
		}
		return TagUtils.toDispTags((String) obj);
	}

	static final Map<String, Integer> ORDERS = CollectionUtils.unmodifiableMap("#tbd", 0, "#must-review", 1, "#easy", 2, "#medium", 3, "#hard", 4);

	@Override
	public String parse(String str, FormatProvider formatProvider) throws FormatterException {
		str = StringUtils.trimToNull(str);
		if (str == null) {
			return null;
		}

		Out<Boolean> isValid = new Out<Boolean>();
		List<String> tags = TagUtils.toTags(str, isValid);

		if (!isValid.val()) {
			throw toParsingError(str, "DbTags");
		}

		// SORT TAGS
		Collections.sort(tags, (t1, t2) -> {

			int o1 = ORDERS.getOrDefault(t1, 100_000);
			int o2 = ORDERS.getOrDefault(t2, 100_000);

			return Integer.compare(o1, o2);
		});

		return !tags.isEmpty() ? TagUtils.toDbTags(tags) : null;
	}
}

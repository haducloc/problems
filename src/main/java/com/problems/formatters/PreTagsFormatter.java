package com.problems.formatters;

import java.util.Set;

import com.appslandia.common.base.FormatProvider;
import com.appslandia.common.base.Out;
import com.appslandia.common.formatters.Formatter;
import com.appslandia.common.formatters.FormatterException;
import com.appslandia.common.utils.StringUtils;
import com.problems.utils.PreTagUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class PreTagsFormatter implements Formatter {

	public static final String ERROR_MSG_KEY = PreTagsFormatter.class.getName() + ".message";

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
		if (obj == null)
			return null;

		return PreTagUtils.toDispPreTags((String) obj);
	}

	@Override
	public String parse(String str, FormatProvider formatProvider) throws FormatterException {
		str = StringUtils.trimToNull(str);
		if (str == null) {
			return null;
		}

		Out<Boolean> isValid = new Out<Boolean>();
		Set<String> tags = PreTagUtils.toPreTags(str, isValid);

		if (!isValid.val()) {
			throw toParsingError(str, "PreTags");
		}
		return !tags.isEmpty() ? PreTagUtils.toDbPreTags(tags) : null;
	}
}

package com.problems.utils;

import java.util.LinkedHashSet;
import java.util.Set;

import com.appslandia.common.base.Out;
import com.appslandia.common.base.TextBuilder;
import com.appslandia.common.utils.SplitUtils;
import com.appslandia.common.utils.StringUtils;
import com.appslandia.common.utils.TagUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class PreTagUtils {

	public static Set<String> toPreTags(String tags, Out<Boolean> isValid) {
		Set<String> res = new LinkedHashSet<>();
		isValid.value = true;

		if (tags == null) {
			return res;
		}

		// Parse tags
		final Out<Boolean> valid = new Out<>();

		for (String tag : SplitUtils.split(tags, ',')) {
			tag = TagUtils.toTag(tag, valid);

			if (tag != null) {
				res.add(tag);
			}
		}

		if (res.isEmpty()) {
			isValid.value = false;
		}
		return res;
	}

	public static String toDispPreTags(String dbPreTags) {
		if (dbPreTags == null)
			return null;

		String[] tags = toPreTags(dbPreTags);
		TextBuilder sb = new TextBuilder(tags.length * 16);

		final int itemsPerLine = 5;

		for (int i = 0; i < tags.length; i++) {
			sb.append(tags[i]).append(",");

			if ((i > 0) && (i % (itemsPerLine - 1) == 0)) {
				sb.appendln();
			}
		}

		return sb.toString();
	}

	public static String[] toPreTags(String dbPreTags) {
		if (dbPreTags == null)
			return StringUtils.EMPTY_ARRAY;

		return SplitUtils.split(dbPreTags, ',');
	}

	public static String toDbPreTags(Set<String> preTags) {
		return String.join(",", preTags);
	}
}

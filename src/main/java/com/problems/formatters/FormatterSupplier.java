package com.problems.formatters;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.appslandia.common.cdi.CDISupplier;
import com.appslandia.common.cdi.Supplier;
import com.appslandia.common.formatters.Formatter;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Supplier(Formatter.class)
public class FormatterSupplier implements CDISupplier {

	@Override
	public Map<String, Formatter> get() {
		Map<String, Formatter> map = new HashMap<>();

		map.put(Formatters.PRE_TAGS, new PreTagsFormatter());
		map.put(Formatters.ALG_DB_TAGS, new AlgDbTagsFormatter());

		return map;
	}
}

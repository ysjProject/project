package com.ysj.project.common.component;

import org.apache.commons.lang3.StringUtils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class QueryLogFilter extends Filter<ILoggingEvent> {

	@Override
	public FilterReply decide(ILoggingEvent event) {
		String log = event.getMessage();

		if(StringUtils.contains(log, "/* NoLog */")) {
			return FilterReply.DENY;
		} else {
			return FilterReply.ACCEPT;
		}

	}

}

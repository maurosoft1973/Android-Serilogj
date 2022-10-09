package com.maurosoft.serilogj.core.enrichers;

import com.maurosoft.serilogj.context.LogContext;
import com.maurosoft.serilogj.core.ILogEventEnricher;
import com.maurosoft.serilogj.core.ILogEventPropertyFactory;
import com.maurosoft.serilogj.events.LogEvent;

public class LogContextEnricher implements ILogEventEnricher {
	@Override
	public void enrich(LogEvent logEvent, ILogEventPropertyFactory propertyFactory) {
		for(ILogEventEnricher enricher : LogContext.getEnrichers()) {
			enricher.enrich(logEvent, propertyFactory);
		}
	}
}

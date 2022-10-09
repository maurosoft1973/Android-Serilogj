package com.maurosoft.serilogj.formatting.display;

import com.maurosoft.serilogj.events.LogEvent;
import com.maurosoft.serilogj.events.LogEventPropertyValue;
import com.maurosoft.serilogj.events.MessageTemplate;
import com.maurosoft.serilogj.events.ScalarValue;
import com.maurosoft.serilogj.formatting.ITextFormatter;
import com.maurosoft.serilogj.parsing.MessageTemplateParser;
import com.maurosoft.serilogj.parsing.MessageTemplateToken;
import com.maurosoft.serilogj.parsing.PropertyToken;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MessageTemplateTextFormatter implements ITextFormatter {
	private MessageTemplate outputTemplate;
	private Locale locale;

	public MessageTemplateTextFormatter(String outputTemplate, Locale locale) {
		if (outputTemplate == null) {
			throw new IllegalArgumentException("outputTemplate");
		}

		this.outputTemplate = new MessageTemplateParser().parse(outputTemplate);
		this.locale = locale;
	}

	@Override
	public void format(LogEvent logEvent, Writer output) throws IOException {
		if (logEvent == null) {
			throw new IllegalArgumentException("logEvent");
		}
		if (output == null) {
			throw new IllegalArgumentException("output");
		}

		Map<String, LogEventPropertyValue> properties = OutputProperties.GetOutputProperties(logEvent);
		for (MessageTemplateToken token : outputTemplate.getTokens()) {
			if (!(token instanceof PropertyToken)) {
				token.render(properties, output, locale);
				continue;
			}
			PropertyToken pt = (PropertyToken) token;

			// First variation from normal rendering - if a property is missing,
			// don't render anything (message templates render the raw token
			// here).
			if (!properties.containsKey(pt.getPropertyName())) {
				continue;
			}

			LogEventPropertyValue value = properties.get(pt.getPropertyName());
			ScalarValue sv = value instanceof ScalarValue ? (ScalarValue) value : null;

			// Second variation; if the value is a scalar string, use literal
			// rendering and support some additional formats: 'u' for uppercase
			// and 'w' for lowercase.
			if (sv != null && sv.getValue() instanceof String) {
				Map<String, LogEventPropertyValue> overridden = new HashMap<String, LogEventPropertyValue>();
				overridden.put(pt.getPropertyName(), new LiteralStringValue((String) sv.getValue()));
				token.render(overridden, output, locale);
			} else if (value instanceof LogEventLevelValue) {
				((LogEventLevelValue) value).render(output, pt.getAlignment(), pt.getFormat());
			} else {
				token.render(properties, output, locale);
			}

		}
	}

}

package com.maurosoft.serilogj.events;

import android.os.Build;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DictionaryValue extends LogEventPropertyValue {
	private Map<ScalarValue, LogEventPropertyValue> elements;

	public DictionaryValue(Iterable<Map.Entry<ScalarValue, LogEventPropertyValue>> elements) {
		if (elements == null) {
			throw new IllegalArgumentException("elements");
		}

		this.elements = new HashMap<ScalarValue, LogEventPropertyValue>();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			elements.forEach(e -> this.elements.put(e.getKey(), e.getValue()));
		} else {
			for (Map.Entry<ScalarValue, LogEventPropertyValue> entry : elements) {
				this.elements.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public DictionaryValue(Map<ScalarValue, LogEventPropertyValue> elements) {
		if (elements == null) {
			throw new IllegalArgumentException("elements");
		}

		this.elements = elements;
	}

	public Map<ScalarValue, LogEventPropertyValue> getElements() {
		return this.elements;
	}

	@Override
	public void render(Writer output, String format, Locale locale) throws IOException {
		if (output == null) {
			throw new IllegalArgumentException("output");
		}

		output.write('[');
		String delim = "(";
		for (Map.Entry<ScalarValue, LogEventPropertyValue> entry : this.elements.entrySet()) {
			output.write(delim);
			delim = ", (";
			entry.getKey().render(output, null, locale);
			output.write(": ");
			entry.getValue().render(output, null, locale);
			output.write(")");
		}
		output.write(']');
	}

}

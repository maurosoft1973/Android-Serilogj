package com.maurosoft.serilogj.events;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Locale;

public class SequenceValue extends LogEventPropertyValue {
	private ArrayList<LogEventPropertyValue> elements;

	public SequenceValue(ArrayList<LogEventPropertyValue> elements) {
		if (elements == null) {
			throw new IllegalArgumentException("elements");
		}

		this.elements = elements;
	}

	public ArrayList<LogEventPropertyValue> getElements() {
		return elements;
	}

	@Override
	public void render(Writer output, String format, Locale locale) throws IOException {
		if (output == null) {
			throw new IllegalArgumentException("output");
		}

		output.write('[');

		String delim = "";
		for (int i = 0; i < elements.size(); i++) {
			output.write(delim);
			delim = ", ";

			elements.get(i).render(output, format, locale);
		}

		output.write(']');
	}

}

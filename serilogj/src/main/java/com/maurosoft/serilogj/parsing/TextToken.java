package com.maurosoft.serilogj.parsing;

import com.maurosoft.serilogj.events.LogEventPropertyValue;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

public class TextToken extends MessageTemplateToken {
	private String text;

	public TextToken(String text) {
		this(text, -1);
	}

	public TextToken(String text, int startIndex) {
		super(startIndex);

		if (text == null) {
			throw new IllegalArgumentException("text");
		}

		this.text = text;
	}

	// TODO: Add compare-methods?

	@Override
	public int getLength() {
		return text.length();
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public void render(Map<String, LogEventPropertyValue> properties, Writer output, Locale locale) throws IOException {
		if (output == null) {
			throw new IllegalArgumentException("output");
		}
		output.write(text);
	}

	@Override
	public boolean equals(Object obj) {
		TextToken sv = (TextToken) ((obj instanceof TextToken) ? obj : null);
		return sv != null && text.equals(sv.text);
	}

	@Override
	public int hashCode() {
		return text.hashCode();
	}
}

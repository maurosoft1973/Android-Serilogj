package com.maurosoft.serilogj.parsing;

import com.maurosoft.serilogj.events.LogEventPropertyValue;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

public abstract class MessageTemplateToken {
	private int startIndex;

	protected MessageTemplateToken(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public abstract int getLength();

	/**
	 * Render the token to the output.
	 * 
	 * @param properties
	 *            Properties that may be represented by the token.
	 * @param output
	 *            Output for the rendered string.
	 * @param
	 *
	 */
	public abstract void render(Map<String, LogEventPropertyValue> properties, Writer output, Locale locale)
			throws IOException;

	public abstract String toString();
}

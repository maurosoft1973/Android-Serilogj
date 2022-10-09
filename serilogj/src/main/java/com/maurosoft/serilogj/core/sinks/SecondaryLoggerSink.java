package com.maurosoft.serilogj.core.sinks;

import android.os.Build;

import com.maurosoft.serilogj.ILogger;
import com.maurosoft.serilogj.core.ILogEventSink;
import com.maurosoft.serilogj.events.LogEvent;
import com.maurosoft.serilogj.events.LogEventProperty;
import com.maurosoft.serilogj.events.LogEventPropertyValue;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

// Copyright 2013-2015 Serilog Contributors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

public class SecondaryLoggerSink implements ILogEventSink, Closeable {
	private ILogger logger;
	private boolean attemptDispose;

	public SecondaryLoggerSink(ILogger logger, boolean attemptDispose) {
		if (logger == null) {
			throw new IllegalArgumentException("logger");
		}

		this.logger = logger;
		this.attemptDispose = attemptDispose;
	}

	public void emit(LogEvent logEvent) {
		if (logEvent == null) {
			throw new IllegalArgumentException("logEvent");
		}

		ArrayList<LogEventProperty> properties = new ArrayList<LogEventProperty>();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			logEvent.getProperties().forEach((k, v) -> properties.add(new LogEventProperty(k, v)));
		} else {
			for (Map.Entry<String,LogEventPropertyValue> entry : logEvent.getProperties().entrySet()) {
				properties.add(new LogEventProperty(entry.getKey(), entry.getValue()));
			}
		}
		LogEvent copy = new LogEvent(logEvent.getTimestamp(), logEvent.getLevel(), logEvent.getException(),
				logEvent.getMessageTemplate(), null);

		logger.write(copy);
	}

	public void close() throws IOException {
		if (!attemptDispose || !(logger instanceof Closeable)) {
			return;
		}

		((Closeable) logger).close();
	}
}

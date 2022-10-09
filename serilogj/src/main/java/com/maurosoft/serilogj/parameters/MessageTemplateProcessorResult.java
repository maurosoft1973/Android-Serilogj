package com.maurosoft.serilogj.parameters;

import com.maurosoft.serilogj.events.LogEventProperty;
import com.maurosoft.serilogj.events.MessageTemplate;

import java.util.ArrayList;

public class MessageTemplateProcessorResult {
	public MessageTemplate template;
	public ArrayList<LogEventProperty> properties;
}

package com.puppetlabs.geppetto.module.dsl;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * Converters for metadata.json parser/serializer
 */
public class ModuleConverters extends DefaultTerminalConverters {
	private static IValueConverter<Object> NULL_CONVERTER = new AbstractLexerBasedConverter<Object>() {
		@Override
		protected void assertValidValue(Object value) {
		}

		@Override
		protected String toEscapedString(Object value) {
			return "null";
		}

		@Override
		public Object toValue(String string, INode node) {
			return null;
		}
	};

	private static IValueConverter<Long> LONG_CONVERTER = new AbstractLexerBasedConverter<Long>() {

		@Override
		protected String toEscapedString(Long value) {
			return value.toString();
		}

		@Override
		public Long toValue(String string, INode node) {
			if(Strings.isEmpty(string))
				throw new ValueConverterException("Couldn't convert empty string to a long value.", node, null);
			try {
				return Long.valueOf(string);
			}
			catch(NumberFormatException e) {
				throw new ValueConverterException("Couldn't convert '" + string + "' to a long value.", node, e);
			}
		}
	};

	private static IValueConverter<Double> DOUBLE_CONVERTER = new AbstractLexerBasedConverter<Double>() {

		@Override
		protected String toEscapedString(Double value) {
			return value.toString();
		}

		@Override
		public Double toValue(String string, INode node) {
			if(Strings.isEmpty(string))
				throw new ValueConverterException("Couldn't convert empty string to a double value.", node, null);
			try {
				return Double.valueOf(string);
			}
			catch(NumberFormatException e) {
				throw new ValueConverterException("Couldn't convert '" + string + "' to a double value.", node, e);
			}
		}
	};

	@ValueConverter(rule = "DOUBLE")
	public IValueConverter<Double> DOUBLE() {
		return DOUBLE_CONVERTER;
	}

	private boolean isQuoted(String rule) {
		return rule.length() >= 2 && (rule.charAt(0) == '"' && rule.charAt(rule.length() - 1) == '"' || rule.startsWith("Q_"));
	}

	@ValueConverter(rule = "LONG")
	public IValueConverter<Long> LONG() {
		return LONG_CONVERTER;
	}

	@ValueConverter(rule = "NULL")
	public IValueConverter<Object> NULL() {
		return NULL_CONVERTER;
	}

	@Override
	public String toString(Object value, String lexerRule) {
		if(!(value instanceof String))
			return super.toString(value, lexerRule);

		return isQuoted(lexerRule)
				? '"' + Strings.convertToJavaString((String) value, false) + '"'
						: super.toString(value, lexerRule);
	}

	@Override
	public Object toValue(String string, String lexerRule, INode node) throws ValueConverterException {
		return isQuoted(lexerRule)
				? STRING().toValue(string, node)
						: super.toValue(string, lexerRule, node);
	}
}

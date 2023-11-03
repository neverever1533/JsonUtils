package cn.imaginary.toolkit.json;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

public class JsonString {
	private Properties prop = new Properties();

	public static String ampersand = "&";
	public static String ampersand_replace = "&amp;";
	public static String apostrophe = "'";
	public static String apostrophe_replace = "&apos;";
	public static String asterisk = "*";
	public static String backSlash = "\\";
	public static String closeBrace = "}";
	public static String closeBrace_replace = "&cbrace;";
	public static String closeBracket = "]";
	public static String closeBracket_replace = "&cbrack;";
	public static String colon = ":";
	public static String colon_replace = "&colon;";
	public static String comma = ",";
	public static String comma_replace = "&comma;";
	public static String dollar = "$";
	public static String equals = "=";
	public static String equals_replace = "&equals;";
	public static String greaterThan = ">";
	public static String greaterThan_replace = "&gt;";
	public static String lessThan = "<";
	public static String lessThan_replace = "&lt;";
	public static String minus = "-";
	public static String null_json = "null";
	public static String openBrace = "{";
	public static String openBrace_replace = "&obrace;";
	public static String openBracket = "[";
	public static String openBracket_replace = "&obrack;";
	public static String period = ".";
	public static String plus = "+";
	public static String quotation = "\"";
	public static String quotation_replace = "&quot;";
	public static String semicolon = ";";
	public static String slash = "/";
	public static String space = " ";
	public static String underscore = "_";
	public static String vertical = "|";

	private String regex;
	private String replacement;

	public JsonString() {
		updata();
	}

	private void updata() {
		prop.put(apostrophe, apostrophe_replace);
		prop.put(closeBrace, closeBrace_replace);
		prop.put(closeBracket, closeBracket_replace);
		prop.put(colon, colon_replace);
		prop.put(comma, comma_replace);
		prop.put(equals, equals_replace);
		prop.put(greaterThan, greaterThan_replace);
		prop.put(lessThan, lessThan_replace);
//		prop.put(openBrace, openBrace_replace);
//		prop.put(openBracket, openBracket_replace);
		prop.put(quotation, quotation_replace);
	}

	public String toString(String string) {
		if (null != string) {
			if (string.contains(ampersand_replace)) {
				string = string.replaceAll(ampersand_replace, ampersand);
			}
			if (string.contains(openBrace_replace)) {
				string = string.replaceAll(openBrace_replace, openBrace);
			}
			if (string.contains(openBracket_replace)) {
				string = string.replaceAll(openBracket_replace, openBracket);
			}
		}
		return toString(string, false);
	}

	private boolean isUnReplaced(String string) {
		Collection<Object> vSet = prop.values();
		Object object;
		String value;
		for (Iterator<Object> iterator = vSet.iterator(); iterator.hasNext();) {
			object = iterator.next();
			if (null != object) {
				value = object.toString();
				if (string.contains(value)) {
					return false;
				}
			}
		}
		return true;
	}

	private String toString(String string, boolean isJson) {
		if (null != string) {
			Set<Entry<Object, Object>> enSet = prop.entrySet();
			Object key;
			Object value;
			Entry<Object, Object> entry;
			for (Iterator<Entry<Object, Object>> iterator = enSet.iterator(); iterator.hasNext();) {
				entry = iterator.next();
				if (null != entry) {
					key = entry.getKey();
					value = entry.getValue();
					if (null != key && null != value) {
						if (isJson) {
							regex = key.toString();
							replacement = value.toString();
						} else {
							regex = value.toString();
							replacement = key.toString();
						}
						if (string.contains(regex)) {
							string = string.replaceAll(regex, replacement);
						}
					}
				}
			}
		}
		return string;
	}

	public String toStringJson(String string) {
		if (null != string) {
			if (isUnReplaced(string)) {
				string = string.replaceAll(ampersand, ampersand_replace);
			}
			if (string.contains(openBrace)) {
				regex = "\\{";
				string = string.replaceAll(regex, openBrace_replace);
			}
			if (string.contains(openBracket)) {
				regex = "\\[";
				string = string.replaceAll(regex, openBracket_replace);
			}
		}
		return toString(string, true);
	}
}

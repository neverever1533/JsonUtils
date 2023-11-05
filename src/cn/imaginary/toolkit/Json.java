package cn.imaginary.toolkit;

import cn.imaginary.toolkit.json.JsonArray;
import cn.imaginary.toolkit.json.JsonObject;
import cn.imaginary.toolkit.json.JsonString;

public class Json {
	private String closeBrace = JsonString.closeBrace;
	private String closeBracket = JsonString.closeBracket;
	private String colon = JsonString.colon;
	private String comma = JsonString.comma;
	private String openBrace = JsonString.openBrace;
	private String openBracket = JsonString.openBracket;
	private String quotation = JsonString.quotation;
	private String space = JsonString.space;

	public String jsonformat(String string) {
		if (null != string) {
			String regex;
			String replacement;
			
			replacement = "$1";
			regex = "\\s*(\\})\\s*";
			string = string.replaceAll(regex, replacement);
			regex = "\\s*(\\])\\s*";
			string = string.replaceAll(regex, replacement);
			regex = "\\s*(:)\\s*";
			string = string.replaceAll(regex, replacement);
			regex = "\\s*(,)\\s*";
			string = string.replaceAll(regex, replacement);
			regex = "\\s*(\\{)\\s*";
			string = string.replaceAll(regex, replacement);
			regex = "\\s*(\\[)\\s*";
			string = string.replaceAll(regex, replacement);
		}
		return string;
	}

	public Object parseJsonValue(String string) {
		Object obj = null;
		if (null != string) {
			if (string.matches("-*\\d+")) {
				obj = Long.valueOf(string);
				Long l = (Long) obj;
				if (l > Integer.MIN_VALUE && l < Integer.MAX_VALUE) {
					obj = Integer.valueOf(string);
				}
			} else if (string.matches("-*\\d*\\.\\d+")) {
				obj = Float.valueOf(string);
			} else if (string.equalsIgnoreCase(JsonString.null_json)) {
				obj = null;
			} else if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false")) {
				obj = Boolean.valueOf(string);
			} else if (string.startsWith(quotation) && string.endsWith(quotation)) {
				obj = string.substring(1, string.length() - 1);
			} else {
				obj = string;
			}
		}
		return obj;
	}

	public JsonArray parseJsonArray(String string) {
		JsonArray jsonArray = null;
		if (null != string) {
			jsonArray = new JsonArray();
			int pre = string.indexOf(openBracket);
			int ffix = string.lastIndexOf(closeBracket);
			if (!(pre != -1 && ffix != -1)) {
				return null;
			}
			string = string.substring(pre + 1, ffix);
			pre = 0;
			ffix = 0;
			int ffix_ = 0;
			int level;
			int i;
			int j;
			int iLength;
			int jLength;
			String temp;
			String value;
			String obj;

			for (i = 0, iLength = string.length(); i < iLength; i++) {
				value = string.substring(i);
				level = 0;
				if (value.startsWith(space)) {
					continue;
				}
				
				for (j = 0, jLength = value.length(); j < jLength; j++) {
					temp = value.substring(j);
					if (value.startsWith(openBrace)) {
						if (temp.startsWith(openBrace)) {
							level++;
						} else if (temp.startsWith(closeBrace)) {
							level--;
						}
						if (level == 0) {
							obj = value.substring(0, j + 1);
							if (obj.length() != 0) {
								jsonArray.add(parseJsonObject(obj));
								i += obj.length() + 1;
								break;
							}
						}
					} else if (value.startsWith(openBracket)) {
						if (temp.startsWith(openBracket)) {
							level++;
						} else if (temp.startsWith(closeBracket)) {
							level--;
						}
						if (level == 0) {
							obj = value.substring(0, j + 1);
							if (obj.length() != 0) {
								jsonArray.add(parseJsonArray(obj));
								i += obj.length() + 1;
								break;
							}
						}
					} else {
						ffix_ = temp.indexOf(comma);
						if (ffix_ != -1) {
							obj = temp.substring(0, ffix_);
						} else {
							obj = temp;
						}
						jsonArray.add(parseJsonValue(obj.trim()));
						i += obj.length() + 1;
						break;
					}
				}

			}
		}
		return jsonArray;
	}

	public JsonObject parseJsonObject(String string) {
		JsonObject jsonObject = null;
		if (null != string) {
			jsonObject = new JsonObject();
			int pre = string.indexOf(openBrace);
			int ffix = string.lastIndexOf(closeBrace);
			if (!(pre != -1 && ffix != -1)) {
				return null;
			}
			string = string.substring(pre + 1, ffix);
			pre = 0;
			ffix = 0;
			int ffix_ = 0;
			int level;
			int i;
			int j;
			int iLength;
			int jLength;
			String temp;
			String str;
			String key;
			String value;
			String obj;

			for (i = 0, iLength = string.length(); i < iLength; i++) {
				str = string.substring(i);
				if (str.startsWith(quotation)) {
					ffix = str.indexOf(quotation, 1);
					pre = str.indexOf(colon, ffix);
					jLength = str.length();
					if (pre != -1 && ffix != -1 && jLength > pre + 1) {
						key = str.substring(1, pre - 1);
						value = str.substring(pre + 1);
						if (key.length() > 2 && value.length() > 0) {
							jLength = value.length();
							level = 0;

							for (j = 0; j < jLength; j++) {
								temp = value.substring(j);
								if (value.startsWith(space)) {
									continue;
								} else if (value.startsWith(openBrace)) {
									if (temp.startsWith(openBrace)) {
										level++;
									} else if (temp.startsWith(closeBrace)) {
										level--;
									}
									if (level == 0) {
										obj = value.substring(0, j + 1);
										if (obj.length() != 0) {
											jsonObject.add(key, parseJsonObject(obj));
											i += key.length() + 3 + obj.length();
											break;
										}
									}
								} else if (value.startsWith(openBracket)) {
									if (temp.startsWith(openBracket)) {
										level++;
									} else if (temp.startsWith(closeBracket)) {
										level--;
									}
									if (level == 0) {
										obj = value.substring(0, j + 1);
										if (obj.length() != 0) {
											jsonObject.add(key, parseJsonArray(obj));
											i += key.length() + 3 + obj.length();
											break;
										}
									}
								} else {
									ffix_ = temp.indexOf(comma);
									if (ffix_ != -1) {
										obj = temp.substring(0, ffix_);
									} else {
										obj = temp;
									}
									jsonObject.add(key, parseJsonValue(obj.trim()));
									i += key.length() + 3 + obj.length();
									break;
								}
							}

						}
					}
				}
			}
		}
		return jsonObject;
	}

}

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

	public JsonArray getJsonArray(String string) {
		if (null == string || (string.startsWith(openBrace) && string.endsWith(closeBrace))) {
			return null;
		}
		JsonArray jsonArray = new JsonArray();
		return jsonArray;
	}

	public JsonObject getJsonObject(String string) {
		if (null == string || (string.startsWith(openBracket) && string.endsWith(closeBracket))) {
			return null;
		}
		JsonObject jsonObject = new JsonObject();
		return jsonObject;
	}

	public String jsonformat(String string) {
		if (null != string) {
			String regex;
			String replacement;

			replacement = "$1";
			regex = "(,)\\s+";
			string = string.replaceAll(regex, replacement);

			replacement = "$1,$2";
			regex = "(\\w+)(})";
			string = string.replaceAll(regex, replacement);
			regex = "(\\w+)(])";
			string = string.replaceAll(regex, replacement);
			regex = "(})(})";
			string = string.replaceAll(regex, replacement);
			regex = "(])(})";
			string = string.replaceAll(regex, replacement);
			regex = "(})(])";
			string = string.replaceAll(regex, replacement);
			regex = "(])(])";
			string = string.replaceAll(regex, replacement);

			replacement = "$1$2";
			regex = "(,)\\s+(\")";
			string = string.replaceAll(regex, replacement);
			regex = "(,)\\s+(})";
			string = string.replaceAll(regex, replacement);
			regex = "(,)\\s+(])";
			string = string.replaceAll(regex, replacement);
			regex = "(\\{)\\s+(\\[)";
			string = string.replaceAll(regex, replacement);
			regex = "(\\[)\\s+(\\{)";
			string = string.replaceAll(regex, replacement);
			regex = "(\\{)\\s+(\\{)";
			string = string.replaceAll(regex, replacement);
			regex = "(\\[)\\s+(\\[)";
			string = string.replaceAll(regex, replacement);
			regex = "(])\\s+(})";
			string = string.replaceAll(regex, replacement);
			regex = "(})\\s+(])";
			string = string.replaceAll(regex, replacement);
			regex = "(])\\s+(])";
			string = string.replaceAll(regex, replacement);
			regex = "(})\\s+(})";
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
			int offset = 0;
			int offset_ = 0;
			pre = 0;
			ffix = 0;
			int ffix_ = 0;
			int level;
			int len;
			int length = string.length();
			String temp;
			String value;
			String obj;

			while (offset >= 0 && offset < length) {
				value = string.substring(offset);
				len = value.length();
				level = 0;
				offset_ = 0;

				while (offset_ >= 0 && offset_ < len) {
					temp = value.substring(offset_);
					if (value.startsWith(openBrace)) {
						if (level == 0) {
							obj = value.substring(0, offset_);
							if (obj.length() != 0) {
								jsonArray.add(parseJsonObject(obj));
								offset += obj.length() + 1;
								break;
							}
						}
						if (temp.startsWith(openBrace)) {
							level++;
						} else if (temp.startsWith(closeBrace)) {
							level--;
						}
					} else if (value.startsWith(openBracket)) {
						if (level == 0) {
							obj = value.substring(0, offset_);
							if (obj.length() != 0) {
								jsonArray.add(parseJsonArray(obj));
								offset += obj.length() + 1;
								break;
							}
						}
						if (temp.startsWith(openBracket)) {
							level++;
						} else if (temp.startsWith(closeBracket)) {
							level--;
						}
					} else {
						ffix_ = temp.indexOf(comma);
						if (ffix_ != -1) {
							obj = temp.substring(0, ffix_);
							jsonArray.add(parseJsonValue(obj));
							offset += obj.length() + 1;
							break;
						}
					}
					offset_++;
				}

			}
			offset++;
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
			int offset = 0;
			int offset_ = 0;
			pre = 0;
			ffix = 0;
			int ffix_ = 0;
			int level;
			int len;
			int length = string.length();
			String temp;
			String str;
			String key;
			String value;
			String obj;

			while (offset >= 0 && offset < length) {
				str = string.substring(offset);
				if (str.startsWith(quotation)) {
					ffix = str.indexOf(quotation, 1);
					pre = str.indexOf(colon, ffix);
					len = str.length();
					if (pre != -1 && ffix != -1 && len > pre + 1) {
						key = str.substring(1, pre - 1);
						value = str.substring(pre + 1);
						if (key.length() > 2 && value.length() > 0) {
							len = value.length();
							level = 0;
							offset_ = 0;

							while (offset_ >= 0 && offset_ < len) {
								temp = value.substring(offset_);
								if (value.startsWith(openBrace)) {
									if (level == 0) {
										obj = value.substring(0, offset_);
										if (obj.length() != 0) {
											jsonObject.add(key, parseJsonObject(obj));
											offset += key.length() + 3 + obj.length();
											break;
										}
									}
									if (temp.startsWith(openBrace)) {
										level++;
									} else if (temp.startsWith(closeBrace)) {
										level--;
									}
								} else if (value.startsWith(openBracket)) {
									if (level == 0) {
										obj = value.substring(0, offset_);
										if (obj.length() != 0) {
											jsonObject.add(key, parseJsonArray(obj));
											offset += key.length() + 3 + obj.length();
											break;
										}
									}
									if (temp.startsWith(openBracket)) {
										level++;
									} else if (temp.startsWith(closeBracket)) {
										level--;
									}
								} else {
									ffix_ = temp.indexOf(comma);
									if (ffix_ != -1) {
										obj = temp.substring(0, ffix_);
										jsonObject.add(key, parseJsonValue(obj));
										offset += key.length() + 3 + obj.length();
										break;
									}
								}
								offset_++;
							}

						}
					}
				}
				offset++;
			}
		}
		return jsonObject;
	}

}

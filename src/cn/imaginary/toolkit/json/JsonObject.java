package cn.imaginary.toolkit.json;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class JsonObject {
	private Properties properties_ = new Properties();

	private JsonString jsonString = new JsonString();

	private String closeBrace = JsonString.closeBrace;
	private String closeBracket = JsonString.closeBracket;
	private String colon = JsonString.colon;
	private String comma = JsonString.comma;
	private String null_json = JsonString.null_json;
	private String openBrace = JsonString.openBrace;
	private String openBracket = JsonString.openBracket;
	private String quotation = JsonString.quotation;
	private String space = JsonString.space;

	public void add(Properties properties) {
		update(properties, true);
	}

	public void add(String key, Object value) {
		update(key, value, false);
	}

	public Properties get() {
		return properties_;
	}

	public void set(Properties properties) {
		if (null != properties) {
			properties_.clear();
			update(properties, false);
		}
	}

	public void set(String key, Object value) {
		update(key, value, true);
	}

	public String toStringJson() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(openBrace);
		Set<Entry<Object, Object>> enSet = properties_.entrySet();
		int p = 0;
		Entry<Object, Object> obj;
		Object key;
		Object value;
		Object[] array;
		for (Iterator<Entry<Object, Object>> iterator = enSet.iterator(); iterator.hasNext();) {
			obj = iterator.next();
			if (null != obj) {
				if (p != 0) {
					stringBuffer.append(comma);
					stringBuffer.append(space);
				}
				p++;
				key = obj.getKey();
				stringBuffer.append(quotation);
				stringBuffer.append(key);
				stringBuffer.append(quotation);
				stringBuffer.append(colon);
				value = obj.getValue();
				if (null != value) {
					if (value instanceof String) {
						if (value.equals(null_json)) {
							stringBuffer.append(value);
						} else {
							stringBuffer.append(quotation);
							stringBuffer.append(value);
							stringBuffer.append(quotation);
						}
					} else if (value instanceof JsonObject) {
						stringBuffer.append(((JsonObject) value).toStringJson());
					} else if (value instanceof JsonArray) {
						stringBuffer.append(((JsonArray) value).toStringJson());
					} else if (value instanceof Object[]) {
						array = (Object[]) value;
						stringBuffer.append(toStringJson(array));
					} else {
						stringBuffer.append(value);
					}
				}
			}
		}
		stringBuffer.append(closeBrace);
		return stringBuffer.toString();
	}

	private String toStringJson(Object[] array) {
		StringBuffer stringBuffer;
		if (null != array) {
			stringBuffer = new StringBuffer();
			stringBuffer.append(openBracket);
			Object obj;
			Object[] arr;
			for (int i = 0, len = array.length; i < len; i++) {
				obj = array[i];
				if (null != obj) {
					if (i != 0) {
						stringBuffer.append(comma);
						stringBuffer.append(space);
					}
					if (obj instanceof String) {
						if (obj.equals(null_json)) {
							stringBuffer.append(obj);
						} else {
							stringBuffer.append(quotation);
							stringBuffer.append(obj);
							stringBuffer.append(quotation);
						}
					} else if (obj instanceof JsonObject) {
						stringBuffer.append(((JsonObject) obj).toStringJson());
					} else if (obj instanceof JsonArray) {
						stringBuffer.append(((JsonArray) obj).toStringJson());
					} else if (obj instanceof Object[]) {
						arr = (Object[]) obj;
						stringBuffer.append(toStringJson(arr));
					} else {
						stringBuffer.append(obj);
					}
				}
			}
			stringBuffer.append(closeBracket);
			return stringBuffer.toString();
		}
		return null;
	}

	private void update(Properties properties, boolean isReplace) {
		if (null != properties) {
			Set<Entry<Object, Object>> enSet = properties_.entrySet();
			Object key;
			Object value;
			Entry<Object, Object> entry;
			for (Iterator<Entry<Object, Object>> iterator = enSet.iterator(); iterator.hasNext();) {
				entry = iterator.next();
				if (null != entry) {
					key = entry.getKey();
					value = entry.getValue();
					if (null != key && null != value) {
						update(key.toString(), value, isReplace);
					}
				}
			}
		}
	}

	private void update(String key, Object value, boolean isReplace) {
		if (null != key) {
			if (null == value) {
				value = null_json;
			}
			key = jsonString.toStringJson(key);
			if (value instanceof String) {
				value = jsonString.toStringJson(value.toString());
			}
			if (isReplace) {
				if (properties_.containsKey(key)) {
					properties_.replace(key, value);
				}
			} else {
				if (!properties_.containsKey(key)) {
					properties_.put(key, value);
				}
			}
		}
	}

}

package cn.imaginary.toolkit.json;

import java.util.ArrayList;
import java.util.Iterator;

public class JsonArray {
	private ArrayList<Object> arrayList = new ArrayList<Object>();

	private JsonString jsonString = new JsonString();

	private String closeBracket = JsonString.closeBracket;
	private String comma = JsonString.comma;
	private String null_json = JsonString.null_json;
	private String openBracket = JsonString.openBracket;
	private String quotation = JsonString.quotation;
	private String space = JsonString.space;

	public void add(int index, Object obj) {
		update(index, obj, false);
	}

	public void add(Object obj) {
		add(arrayList.size(), obj);
	}

	public Object[] get() {
		return arrayList.toArray();
	}

	public void set(int index, Object obj) {
		update(index, obj, true);
	}

	public String toStringJson() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(openBracket);
		int p = 0;
		Object obj;
		Object[] arr;
		for (Iterator<Object> iterator = arrayList.iterator(); iterator.hasNext();) {
			obj = iterator.next();
			if (null != obj) {
				if (p != 0) {
					stringBuffer.append(comma);
					stringBuffer.append(space);
				}
				p++;
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

	private void update(int index, Object obj, boolean isReplace) {
		if (null == obj) {
			obj = null_json;
		}
		int size = arrayList.size();
		if (index > size) {
			index = size;
		} else if (index < 0) {
			index = 0;
		}
		if (obj instanceof String) {
			obj = jsonString.toStringJson(obj.toString());
		}
		if (isReplace) {
			arrayList.set(index, obj);
		} else {
			arrayList.add(index, obj);
		}
	}

}

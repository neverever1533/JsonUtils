package cn.imaginary.toolkit.json;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class JsonObject {

    private Properties properties_ = new Properties();

    private String apostrophe = JsonString.apostrophe;
    private String closeBrace = JsonString.closeBrace;
    private String colon = JsonString.colon;
    private String comma = JsonString.comma;
    private String hex = JsonString.hex;
    private String null_json = JsonString.null_json;
    private String openBrace = JsonString.openBrace;
    private String quotation = JsonString.quotation;
    private String space = JsonString.space;

    public void add(Object key, Object value) {
        if (JsonKey.isJsonKey(key)) {
            if (key instanceof JsonKey) {
                key = ((JsonKey) key).getKey();
            } else if (key instanceof JsonString) {
                key = ((JsonString) key).getString();
            }
            if (null == value) {
                value = null_json;
            }
            properties_.put(key, value);
        }
    }

    public void addAll(Map<? extends Object, ? extends Object> t) {
        properties_.putAll(t);
    }

    public void clear() {
        properties_.clear();
    }

    public Object clone() {
        return properties_.clone();
    }

    public boolean containsKey(Object key) {
        return properties_.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return properties_.containsValue(value);
    }

    public Properties get() {
        return properties_;
    }

    public Object getValue(Object key) {
        return properties_.get(key);
    }

    public boolean isEmpty() {
        return properties_.isEmpty();
    }

    public void remove(Object key) {
        properties_.remove(key);
    }

    public void replace(Object key, Object value) {
        properties_.replace(key, value);
    }

    public void set(Properties properties) {
        if (null != properties) {
            clear();
            properties_ = properties;
        }
    }

    public int size() {
        return properties_.size();
    }

    public String toString() {
        return toString(properties_);
    }

    public String toString(Properties properties) {
        if (null != properties) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(openBrace);
            Set<Entry<Object, Object>> enSet = properties_.entrySet();
            int p = 0;
            Entry<Object, Object> entry;
            Object key;
            Object value;
            for (Iterator<Entry<Object, Object>> iterator = enSet.iterator(); iterator.hasNext();) {
                entry = iterator.next();
                if (null != entry) {
                    if (p != 0) {
                        stringBuffer.append(comma);
                        stringBuffer.append(space);
                    }
                    p++;
                    key = entry.getKey();
                    stringBuffer.append(quotation);
                    stringBuffer.append(key);
                    stringBuffer.append(quotation);
                    stringBuffer.append(colon);
                    value = entry.getValue();
                    if (null != value) {
                        if (value instanceof Character) {
                            stringBuffer.append(apostrophe);
                            stringBuffer.append(value);
                            stringBuffer.append(apostrophe);
                        } else if (value instanceof String) {
                            if (value.equals(null_json) || value.toString().matches(hex)) {
                                stringBuffer.append(value);
                            } else {
                                stringBuffer.append(quotation);
                                stringBuffer.append(value);
                                stringBuffer.append(quotation);
                            }
                        } else if (value instanceof JsonString) {
                            stringBuffer.append(((JsonString) value).toString());
                        } else if (value instanceof JsonObject) {
                            stringBuffer.append(((JsonObject) value).toString());
                        } else if (value instanceof JsonArray) {
                            stringBuffer.append(((JsonArray) value).toString());
                        } else if (value instanceof Object[]) {
                            stringBuffer.append(JsonArray.toString((Object[]) value));
                        } else {
                            stringBuffer.append(value);
                        }
                    }
                }
            }
            stringBuffer.append(closeBrace);
            return stringBuffer.toString();
        }
        return null;
    }
}

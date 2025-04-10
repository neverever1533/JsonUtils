package cn.imaginary.toolkit.json;

public class JsonKey {

    public JsonKey() {}

    public JsonKey(JsonString jsonString) {
        setKey(jsonString);
    }

    public JsonKey(String key) {
        setKey(key);
    }

    private String key_Json;

    public String getKey() {
        return key_Json;
    }

    public void setKey(JsonString jsonString) {
        if (null != jsonString) {
            setKey(jsonString.getString());
        }
    }

    public void setKey(String key) {
        key_Json = key;
    }

    public static boolean isJsonKey(Object key) {
        if (null != key && (key instanceof String || key instanceof JsonString)) {
            return true;
        } else {
            return false;
        }
    }
}
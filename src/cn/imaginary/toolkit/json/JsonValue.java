package cn.imaginary.toolkit.json;

public class JsonValue {

    public JsonValue() {}

    public JsonValue(Object value) {
        setValue(value);
    }

    private Object value_Json;

    public Object getValue() {
        return value_Json;
    }

    public void setValue(Object value) {
        if (isJsonValue(value)) {
            value_Json = value;
        }
    }

    public static boolean isJsonValue(Object value) {
        if (
            null == value ||
            value instanceof Boolean ||
            value instanceof Character ||
            value instanceof String ||
            value instanceof Number ||
            value instanceof JsonString ||
            value instanceof JsonArray ||
            value instanceof JsonObject
        ) {
            return true;
        } else {
            return false;
        }
    }
}
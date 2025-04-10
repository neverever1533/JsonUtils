package cn.imaginary.toolkit.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class JsonArray {

    private static String apostrophe = JsonString.apostrophe;
    private static String closeBracket = JsonString.closeBracket;
    private static String comma = JsonString.comma;
    private static String hex = JsonString.hex;
    private static String null_json = JsonString.null_json;
    private static String openBracket = JsonString.openBracket;
    private static String quotation = JsonString.quotation;
    private static String space = JsonString.space;

    public static String toStringJson(Object[] array) {
        if (null != array) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(openBracket);
            Object obj;
            for (int i = 0, iLength = array.length; i < iLength; i++) {
                obj = array[i];
                if (null != obj) {
                    if (i != 0) {
                        stringBuffer.append(comma);
                        stringBuffer.append(space);
                    }
                    if (obj instanceof Character) {
                        stringBuffer.append(apostrophe);
                        stringBuffer.append(obj);
                        stringBuffer.append(apostrophe);
                    } else if (obj instanceof String) {
                        if (obj.equals(null_json) || obj.toString().matches(hex)) {
                            stringBuffer.append(obj);
                        } else {
                            stringBuffer.append(quotation);
                            stringBuffer.append(obj);
                            stringBuffer.append(quotation);
                        }
                    } else if (obj instanceof JsonString) {
                        stringBuffer.append(((JsonString) obj).toStringJson());
                    } else if (obj instanceof JsonObject) {
                        stringBuffer.append(((JsonObject) obj).toStringJson());
                    } else if (obj instanceof JsonArray) {
                        stringBuffer.append(((JsonArray) obj).toStringJson());
                    } else if (obj instanceof Object[]) {
                        stringBuffer.append(toStringJson((Object[]) obj));
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

    private ArrayList<Object> arrayList = new ArrayList<Object>();

    public void add(int index, JsonObject item) {
        Object obj = item;
        add(index, obj);
    }

    public void add(int index, JsonArray item) {
        Object obj = item;
        add(index, obj);
    }

    public void add(int index, JsonString item) {
        Object obj = item;
        add(index, obj);
    }

    public void add(int index, String item) {
        Object obj = item;
        add(index, obj);
    }

    public void add(int index, Object obj) {
        arrayList.add(index, obj);
    }

    public void add(JsonObject item) {
        Object obj = item;
        add(obj);
    }

    public void add(JsonArray item) {
        Object obj = item;
        add(obj);
    }

    public void add(JsonString item) {
        Object obj = item;
        add(obj);
    }

    public void add(String item) {
        Object obj = item;
        add(obj);
    }

    public void add(Object obj) {
        arrayList.add(obj);
    }

    public void addAll(Collection<?> c) {
        arrayList.addAll(c);
    }

    public void addAll(int index, Collection<?> c) {
        arrayList.addAll(index, c);
    }

    public void clear() {
        arrayList.clear();
    }

    public Object clone() {
        return arrayList.clone();
    }

    public boolean contains(JsonObject item) {
        Object obj = item;
        return contains(obj);
    }

    public boolean contains(JsonArray item) {
        Object obj = item;
        return contains(obj);
    }

    public boolean contains(JsonString item) {
        Object obj = item;
        return contains(obj);
    }

    public boolean contains(String item) {
        Object obj = item;
        return contains(obj);
    }

    public boolean contains(Object obj) {
        return arrayList.contains(obj);
    }

    public boolean containsAll(Collection<?> c) {
        return arrayList.containsAll(c);
    }

    public ArrayList<Object> get() {
        return arrayList;
    }

    public int indexOf(JsonObject item) {
        Object obj = item;
        return indexOf(obj);
    }

    public int indexOf(JsonArray item) {
        Object obj = item;
        return indexOf(obj);
    }

    public int indexOf(JsonString item) {
        Object obj = item;
        return indexOf(obj);
    }

    public int indexOf(String item) {
        Object obj = item;
        return indexOf(obj);
    }

    public int indexOf(Object obj) {
        return arrayList.indexOf(obj);
    }

    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    public int lastIndexOf(JsonObject item) {
        Object obj = item;
        return lastIndexOf(obj);
    }

    public int lastIndexOf(JsonArray item) {
        Object obj = item;
        return lastIndexOf(obj);
    }

    public int lastIndexOf(JsonString item) {
        Object obj = item;
        return lastIndexOf(obj);
    }

    public int lastIndexOf(String item) {
        Object obj = item;
        return lastIndexOf(obj);
    }

    public int lastIndexOf(Object obj) {
        return arrayList.lastIndexOf(obj);
    }

    public void remove(int index) {
        arrayList.remove(index);
    }

    public void remove(JsonObject item) {
        Object obj = item;
        remove(obj);
    }

    public void remove(JsonArray item) {
        Object obj = item;
        remove(obj);
    }

    public void remove(JsonString item) {
        Object obj = item;
        remove(obj);
    }

    public void remove(String item) {
        Object obj = item;
        remove(obj);
    }

    public void remove(Object obj) {
        arrayList.remove(obj);
    }

    public boolean removeAll(Collection<?> c) {
        return arrayList.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return arrayList.retainAll(c);
    }

    public void set(ArrayList<Object> list) {
        if (null != list) {
            clear();
            arrayList = list;
        }
    }

    public void set(int index, JsonObject item) {
        Object obj = item;
        set(index, obj);
    }

    public void set(int index, JsonArray item) {
        Object obj = item;
        set(index, obj);
    }

    public void set(int index, JsonString item) {
        Object obj = item;
        set(index, obj);
    }

    public void set(int index, String item) {
        Object obj = item;
        set(index, obj);
    }

    public void set(int index, Object obj) {
        arrayList.set(index, obj);
    }

    public int size() {
        return arrayList.size();
    }

    public void sort(Comparator<? super Object> c) {
        arrayList.sort(c);
    }

    public List<Object> subList(int start, int end) {
        return arrayList.subList(start, end);
    }

    public Object[] toArray() {
        return arrayList.toArray();
    }

    public String toStringJson() {
        return toStringJson(arrayList.toArray());
    }
}

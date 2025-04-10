package cn.imaginary.toolkit;

import cn.imaginary.toolkit.json.JsonArray;
import cn.imaginary.toolkit.json.JsonObject;
import cn.imaginary.toolkit.json.JsonString;

public class Json {

    private static String carriageReturn_And_LineFeed = JsonString.carriageReturn_And_LineFeed;
    private static String tabs = JsonString.tabs;
    private static String space = JsonString.space;

    private static void addTabs(StringBuffer stringBuffer, int level) {
        if (null != stringBuffer && level > 0) {
            for (int i = 0, iLength = level; i < iLength; i++) {
                stringBuffer.append(tabs);
            }
        }
    }

    public static String format(String string) {
        if (null != string) {
            StringBuffer stringBuffer = new StringBuffer();
            char sign;
            int level = 0;
            for (int i = 0, iLength = string.length(); i < iLength; i++) {
                sign = string.charAt(i);
                switch (sign) {
                    case '{':
                        stringBuffer.append(carriageReturn_And_LineFeed);
                        addTabs(stringBuffer, level);
                        level++;
                        stringBuffer.append(sign);
                        stringBuffer.append(carriageReturn_And_LineFeed);
                        addTabs(stringBuffer, level);
                        break;
                    case '[':
                        stringBuffer.append(carriageReturn_And_LineFeed);
                        addTabs(stringBuffer, level);
                        level++;
                        stringBuffer.append(sign);
                        stringBuffer.append(carriageReturn_And_LineFeed);
                        addTabs(stringBuffer, level);
                        break;
                    case ',':
                        stringBuffer.append(sign);
                        stringBuffer.append(space);
                        stringBuffer.append(carriageReturn_And_LineFeed);
                        addTabs(stringBuffer, level);
                        break;
                    case ']':
                        level--;
                        stringBuffer.append(carriageReturn_And_LineFeed);
                        addTabs(stringBuffer, level);
                        stringBuffer.append(sign);
                        break;
                    case '}':
                        level--;
                        stringBuffer.append(carriageReturn_And_LineFeed);
                        addTabs(stringBuffer, level);
                        stringBuffer.append(sign);
                        break;
                    case ':':
                        stringBuffer.append(space);
                        stringBuffer.append(sign);
                        stringBuffer.append(space);
                        break;
                    default:
                        stringBuffer.append(sign);
                        break;
                }
            }
            string = stringBuffer.toString();
        }
        return string;
    }

    private String apostrophe = JsonString.apostrophe;
    private String closeBrace = JsonString.closeBrace;
    private String closeBracket = JsonString.closeBracket;
    private String colon = JsonString.colon;
    private String comma = JsonString.comma;
    private String false_json = JsonString.false_json;
    private String null_json = JsonString.null_json;
    private String openBrace = JsonString.openBrace;
    private String openBracket = JsonString.openBracket;
    private String quotation = JsonString.quotation;
    private String replacement = "$1";
    private String replacement_ = ", $1";
    private String true_json = JsonString.true_json;
    private String whitespace_ = "\\s+(.*)";

    public String formatJson(String string) {
        if (null != string) {
            String regex;
            regex = "\\s*(\\})\\s*";
            string = string.replaceAll(regex, replacement);
            string = string.replaceAll(regex, replacement_);
            regex = "\\s*(\\])\\s*";
            string = string.replaceAll(regex, replacement);
            string = string.replaceAll(regex, replacement_);
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

    public JsonArray parseJsonArray(String string) {
        JsonArray jsonArray = null;
        if (null != string) {
            jsonArray = new JsonArray();
            int pre = string.indexOf(openBracket);
            int ffix = string.lastIndexOf(closeBracket);
            if (pre == -1 || ffix == -1) {
                return null;
            }
            string = string.substring(pre + 1, ffix).trim();
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
                if (value.matches(whitespace_)) {
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

                                i += obj.length();
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

                                i += obj.length();
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
                        if (obj.length() != 0) {
                            jsonArray.add(parseJsonValue(obj.trim()));

                            i += obj.length();
                            break;
                        }
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
            if (pre == -1 || ffix == -1) {
                return null;
            }
            string = string.substring(pre + 1, ffix).trim();
            pre = 0;
            ffix = 0;
            int ffix_ = 0;
            int level;
            int i;
            int j;
            int length;
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
                    length = str.length();
                    if (pre != -1 && ffix != -1 && length >= pre + 1) {
                        key = str.substring(1, ffix);
                        value = str.substring(pre + 1).trim();
                        jLength = value.length();
                        i += length - pre - 1 - jLength;
                        if (jLength > 0) {
                            level = 0;

                            for (j = 0; j < jLength; j++) {
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
                                            jsonObject.add(key, parseJsonObject(obj));
                                            i += pre + 1 + obj.length();
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
                                            i += pre + 1 + obj.length();
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
                                    if (obj.length() != 0) {
                                        jsonObject.add(key, parseJsonValue(obj.trim()));
                                        i += pre + 1 + obj.length();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return jsonObject;
    }

    private boolean tag_Original = true;

    public void setTagOriginal(boolean isTagOriginal) {
        tag_Original = isTagOriginal;
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
            } else if (string.matches("-*\\d*\\.\\d+f*")) {
                obj = Float.valueOf(string);
            } else if (string.equalsIgnoreCase(null_json)) {
                obj = null;
            } else if (string.equalsIgnoreCase(true_json) || string.equalsIgnoreCase(false_json)) {
                obj = Boolean.valueOf(string);
            } else if (string.startsWith(quotation) && string.endsWith(quotation)) {
                // obj = string.substring(1, string.length() - 1);
                string = string.substring(1, string.length() - 1);
                JsonString jsonString = new JsonString();
                if (!tag_Original) {
                    string = jsonString.tagReplaced(string);
                }
                jsonString.setString(string);
                // obj = jsonString.getStringJson();
                obj = jsonString;
            } else if (string.startsWith(apostrophe) && string.endsWith(apostrophe) && string.length() == 3) {
                obj = string.charAt(1);
            } else {
                // obj = string;
                obj = null;
            }
        }
        return obj;
    }
}

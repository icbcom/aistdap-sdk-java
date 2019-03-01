package ru.icbcom.aistdapsdkjava.api.objecttype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Тип атрибута.
 */
public enum AttributeType {
    INTEGER, STRING, BOOLEAN, FLOAT, TIME, DATE, DATETIME, ENUMERATION, SET;

    private static Map<String, AttributeType> namesMap = new HashMap<>();

    static {
        namesMap.put("Integer", INTEGER);
        namesMap.put("String", STRING);
        namesMap.put("Boolean", BOOLEAN);
        namesMap.put("Float", FLOAT);
        namesMap.put("Time", TIME);
        namesMap.put("Date", DATE);
        namesMap.put("DateTime", DATETIME);
        namesMap.put("Enumeration", ENUMERATION);
        namesMap.put("Set", SET);
    }

    @JsonCreator
    public static AttributeType forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, AttributeType> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }
        return null;
    }
}

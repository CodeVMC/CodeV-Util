package net.codevmc.util.serialization;

import com.google.common.primitives.Bytes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class SerializationHelper {

    public static String serialize(Object o) {
        return getBasicDataTypeSymbol(o) + getSerializeObject(o).toString();
    }

    private static List<String> getSerializeObject(Object o) {
        List<String> serializeFieldList = new ArrayList<>();
        Arrays.asList(o.getClass().getDeclaredFields())
                .stream()
                .peek(field -> field.setAccessible(true))
                .filter(field -> hasSerializationAnnotation(field))
                .forEach(field -> {
                    serializeFieldList.add(serializeField(o, field));
                });
        return serializeFieldList;
    }

    private static String serializeField(Object o, Field field) {
        Class type = field.getType();
        String fieldClassName = type.getName();
        String fieldName = field.getName();
        String symbolName = getBasicDataTypeSymbol(type);
        if (isCollectionChild(type))
            fieldClassName = "Collection";
        if (isMapChild(type))
            fieldClassName = "Map";
        try {
            Object fieldInstance = field.get(o);
            if (fieldInstance == null) {
                return fieldName + ":" + "N";
            }
            switch (fieldClassName) {

                case "int":
                    return fieldName + ":" + symbolName + field.getInt(o);
                case "long":
                    return fieldName + ":" + symbolName + field.getLong(o);
                case "short":
                    return fieldName + ":" + symbolName + field.getShort(o);
                case "boolean":
                    return fieldName + ":" + symbolName + field.getBoolean(o);
                case "byte":
                    return fieldName + ":" + symbolName + field.getByte("o");
                case "char":
                    return fieldName + ":" + symbolName + field.getChar(o);
                case "double":
                    return fieldName + ":" + symbolName + field.getDouble(o);
                case "float":
                    return fieldName + ":" + symbolName + field.getFloat(o);
                case "java.lang.String":
                    return fieldName + ":" + symbolName + Arrays.toString(((String) field.get(o)).getBytes());
                case "Collection":
                    return fieldName + ":" + symbolName + serializeCollection((Collection) field.get(o));
                case "Map":
                    return fieldName + ":" + symbolName + serializeMap((Map) field.get(o));
                default:
                    return fieldName + ":" + symbolName + serialize(field.get(o));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "null";
    }

    private static String serializeCollection(Collection collection) {
        if (collection.isEmpty())
            return "[]";
        Object[] array = collection.toArray();
        ArrayList<String> list = new ArrayList<>();
        for (Object o1 : array)
            list.add(object2String(o1));
        return list.toString();
    }

    private static String object2String(Object o) {
        Class clazz = o.getClass();
        String name = clazz.getName();
        switch (name) {
            case "int":
            case "short":
            case "long":
            case "boolean":
            case "byte":
            case "char":
            case "double":
            case "float":
                return getBasicDataTypeSymbol(o) + o.toString();
            case "java.lang.String":
                return getBasicDataTypeSymbol(o) + Arrays.toString(((String) o).getBytes());
            case "Collection":
                return getBasicDataTypeSymbol(o) + serializeCollection((Collection) o);
            case "Map":
                return getBasicDataTypeSymbol(o) + serializeMap((Map) o);
            default:
                return getBasicDataTypeSymbol(o) + serialize(o);
        }
    }

    private static String getBasicDataTypeSymbol(Object o) {
        return getBasicDataTypeSymbol(o.getClass());
    }

    private static String getBasicDataTypeSymbol(Class clazz) {
        String name = clazz.getName();
        if (isCollectionChild(clazz))
            name = "Collection";
        if (isMapChild(clazz))
            name = "Map";
        switch (name) {
            case "int":
                return "I";
            case "long":
                return "J";
            case "short":
                return "S";
            case "boolean":
                return "Z";
            case "byte":
                return "B";
            case "char":
                return "C";
            case "double":
                return "D";
            case "float":
                return "F";
            case "java.lang.String":
                return "T";
            case "Collection":
                return "O" + clazz.getName();
            case "Map":
                return "P" + clazz.getName();
            default:
                return "L" + name;
        }
    }

    private static String serializeMap(Map map) {
        if (map.isEmpty())
            return "";
        ArrayList<String> list = new ArrayList();
        Set<Map.Entry> set = map.entrySet();
        for (Map.Entry entry : set) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            list.add(object2String(key) + ":" + object2String(value));
        }
        return list.toString();
    }

    private static boolean isMapChild(Class type) {
        return Map.class.isAssignableFrom(type);
    }

    private static boolean isCollectionChild(Class clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    private static boolean hasSerializationAnnotation(Field field) {
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(Serialization.class))
                return true;
        }
        return false;
    }

    public static <T> T deserialize(String str) {
        str = str.trim();
        if (str.isEmpty())
            return null;
        return (T) deserializeField(str);
    }

    private static Map<String, Object> deserializeMap(String str) {
        Map<String, Object> map;
        try{
            map = (Map<String, Object>) getInstanceObjectByClassName(getClassNameFromSerializeString(str));
        }catch (Exception e){
            map = new HashMap<>();
        }
        MapDeserializer deserializer = new MapDeserializer(getElementFromSerializeString(str));
        while (deserializer.hasNextEntry()) {
            Map.Entry<String, Object> entry = deserializer.getNextEntry();
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    private static String getClassNameFromSerializeString(String str) {
        return str.substring(0, str.indexOf('['));
    }

    private static Object getInstanceObjectByClassName(String str) {
        try {
            Class clazz = Class.forName(str);
            return clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }
        throw new RuntimeException(str);
    }

    private static class MapDeserializer {

        private String string;

        private int index = 0;

        private int lastIndex = 0;

        boolean indexByHasNextEntry = false;

        public MapDeserializer(String string) {
            if (string.startsWith("[") && string.endsWith("]"))
                this.string = string.substring(1, string.length() - 1);
            else throw new RuntimeException("wrong serialized string: " + string);
        }

        public Map.Entry<String, Object> getNextEntry() {
            if (!indexByHasNextEntry)
                indexNextComma();
            indexByHasNextEntry = false;
            String piece = string.substring(lastIndex, index).trim();
            String key = piece.substring(0, piece.indexOf(':'));
            String value = piece.substring(piece.indexOf(':') + 1);
            Object o = deserializeField(value);
            return new Map.Entry<String, Object>() {
                @Override
                public String getKey() {
                    return key;
                }

                @Override
                public Object getValue() {
                    return o;
                }

                @Override
                public Object setValue(Object value) {
                    return o;
                }
            };
        }

        public boolean hasNextEntry() {
            lastIndex = index;
            indexNextComma();
            if (index == lastIndex) {
                return false;
            }
            indexByHasNextEntry = true;
            return true;
        }

        private void indexNextComma() {
            int rightBracketNum = 0;
            while (true) {
                char c = string.charAt(index);
                if (c == ',' && rightBracketNum == 0) {
                    return;
                } else if (c == '[')
                    rightBracketNum++;
                else if (c == ']')
                    rightBracketNum--;
                index++;
            }
        }
    }

    private static String getElementFromSerializeString(String str) {
        return str.substring(str.indexOf('['));
    }

    private static Collection deserializeCollection(String str) {
        Collection collection = (Collection) getInstanceObjectByClassName(getClassNameFromSerializeString(str));
        String[] elementArray = getElementArray(str);
        for (String element : elementArray)
            collection.add(deserializeField(element));
        return collection;
    }

    private static String[] getElementArray(String str) {
        String elementsWithBracket = getElementFromSerializeString(str);
        String elements = elementsWithBracket.substring(1, elementsWithBracket.length() - 1);
        return elements.split(",");
    }

    private static String deserializeString(String bytes) {
        ArrayList<Byte> byteArrayList = new ArrayList<>();
        for (String b : getElementArray(bytes))
            byteArrayList.add(Byte.valueOf(b));
        return new String(Bytes.toArray(byteArrayList));
    }

    private static Object deserializeField(String element) {
        char startChar = element.charAt(0);
        if (startChar == 'N')
            return null;
        String serializeString = element.substring(1);
        switch (startChar) {
            case 'I':
                return Integer.valueOf(serializeString);
            case 'J':
                return Long.valueOf(serializeString);
            case 'S':
                return Short.valueOf(serializeString);
            case 'Z':
                return Boolean.valueOf(serializeString);
            case 'B':
                return Byte.valueOf(serializeString);
            case 'C':
                return Character.valueOf(serializeString.charAt(0));
            case 'D':
                return Double.valueOf(serializeString);
            case 'F':
                return Float.valueOf(serializeString);
            case 'T':
                return deserializeString(serializeString);
            case 'O':
                return deserializeCollection(serializeString);
            case 'P':
                return deserializeMap(serializeString);
            case 'L':
                return deserializeObject(serializeString);
            default:
                throw new RuntimeException("deserializeField error: " + element);
        }
    }

    private static Object deserializeObject(String s) {
        try {
            return deserializeObject(Class.forName(getClassNameFromSerializeString(s)), getElementFromSerializeString(s));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    private static Object deserializeObject(Class clazz, String s) {
        try {
            return deserializeObject(clazz.newInstance(), s);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    private static Object deserializeObject(Object instance, String s) {
        Map<String, Object> fieldMap = deserializeMap(s);
        Class clazz = instance.getClass();
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            String fieldName = entry.getKey();
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                String fieldClassName = field.getType().getName();
                try {
                    switch (fieldClassName) {
                        case "int":
                            field.setInt(instance, (int) entry.getValue());
                            break;
                        case "long":
                            field.setLong(instance, (long) entry.getValue());
                            break;
                        case "short":
                            field.setShort(instance, (short) entry.getValue());
                            break;
                        case "boolean":
                            field.setBoolean(instance, (boolean) entry.getValue());
                            break;
                        case "byte":
                            field.setByte(instance, (byte) entry.getValue());
                            break;
                        case "char":
                            field.setChar(instance, (char) entry.getValue());
                            break;
                        case "double":
                            field.setDouble(instance, (double) entry.getValue());
                            break;
                        case "float":
                            field.setFloat(instance, (float) entry.getValue());
                            break;
                        default: {
                            field.set(instance,entry.getValue());
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }
        return instance;
    }
}

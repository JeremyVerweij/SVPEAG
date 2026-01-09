package io.github.jeremyverweij.vcp.walker;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * This has all the data types, this is basically an enum, but you can add more when another program needs it
 */
public class DataType {
    public static List<Class<? extends DataType>> allTypes = new ArrayList<>(List.of(CharType.class, StringType.class,
            ByteType.class, ShortType.class, IntType.class, LongType.class, FloatType.class, DoubleType.class, BooleanType.class));

    public static class CharType extends DataType{}
    public static class StringType extends DataType{}

    public static class NumberType extends DataType{}

    public static class ByteType extends NumberType{}
    public static class ShortType extends NumberType{}
    public static class IntType extends NumberType{}
    public static class LongType extends NumberType{}

    public static class FloatType extends NumberType{}
    public static class DoubleType extends NumberType{}

    public static class BooleanType extends NumberType{}

    @Override
    public int hashCode() {
        return this.getClass().getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass().isAssignableFrom(obj.getClass()) ||
                obj.getClass().isAssignableFrom(this.getClass()) ||
                obj.getClass() == this.getClass();
    }

    @Override
    public String toString() {
        return this.getClass().getName().toLowerCase().replace("type", "");
    }

    public static DataType getDataType(String t){
        Class<? extends DataType> clazz = null;

        for (Class<? extends DataType> allType : allTypes) {
            if (allType.getSimpleName().equals(t)){
                clazz = allType;
                break;
            }
        }

        if (clazz == null) return null;

        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}

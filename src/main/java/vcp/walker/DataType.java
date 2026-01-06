package vcp.walker;

import java.util.Objects;

/**
 * This has all the data types, this is basically an enum, but you can add more when another program needs it
 */
public class DataType {
    public static class CharType extends DataType{}
    public static class StringType extends DataType{}

    public static class ByteType extends DataType{}
    public static class ShortType extends DataType{}
    public static class IntType extends DataType{}
    public static class LongType extends DataType{}

    public static class FloatType extends DataType{}
    public static class DoubleType extends DataType{}

    public static class BooleanType extends DataType{}

    @Override
    public int hashCode() {
        return this.getClass().getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.getClass().getName(), obj.getClass().getName());
    }
}

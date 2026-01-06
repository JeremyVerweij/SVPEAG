package vcp.walker;

import java.util.Objects;

public abstract class ValueHolder<T> {
    private T value;

    protected ValueHolder(T value){
        this.value = value;
    }

    public T getValue(){
        return value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public abstract int Int();
    public abstract boolean Bool();
    public abstract float Float();
    public abstract String String();

    public static class BooleanValue extends ValueHolder<Boolean>{
        public BooleanValue(Boolean value) {
            super(value);
        }

        @Override
        public int Int(){
            return getValue() ? 1 : 0;
        }

        @Override
        public float Float() {
            return Int();
        }

        @Override
        public boolean Bool() {
            return getValue();
        }

        @Override
        public String String(){
            return getValue() ? "true" : "false";
        }
    }

    public static class IntegerValue extends ValueHolder<Integer>{
        public IntegerValue(Integer value) {
            super(value);
        }

        @Override
        public int Int(){
            return getValue();
        }

        @Override
        public float Float() {
            return Int();
        }

        @Override
        public boolean Bool() {
            return getValue() != 0;
        }

        @Override
        public String String(){
            return String.valueOf(getValue());
        }
    }

    public static class FloatValue extends ValueHolder<Float>{
        public FloatValue(Float value) {
            super(value);
        }

        @Override
        public int Int(){
            return getValue().intValue();
        }

        @Override
        public float Float() {
            return getValue();
        }

        @Override
        public boolean Bool() {
            return getValue() != 0;
        }

        @Override
        public String String(){
            return String.valueOf(getValue());
        }
    }

    public static class StringValue extends ValueHolder<String>{
        public StringValue(String value) {
            super(value);
        }

        @Override
        public int Int(){
            return Integer.parseInt(getValue());
        }

        @Override
        public float Float() {
            return Float.parseFloat(getValue());
        }

        @Override
        public boolean Bool() {
            return Objects.equals(getValue(), "true");
        }

        @Override
        public String String(){
            return getValue();
        }
    }
}

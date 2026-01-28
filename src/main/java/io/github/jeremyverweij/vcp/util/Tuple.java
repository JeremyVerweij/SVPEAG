package io.github.jeremyverweij.vcp.util;

import java.util.Objects;

public class Tuple<A, B>{
    private final A value1;
    private final B value2;
    private final int hashCode;

    public Tuple(A value1, B value2) {
        this.value1 = value1;
        this.value2 = value2;

        this.hashCode = Objects.hash(this.value1, this.value2);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tuple<?,?> tuple){
            return tuple.value1.equals(this.value1) && tuple.value2.equals(this.value2);
        }

        return false;
    }

    public A getValue1() {
        return value1;
    }

    public B getValue2() {
        return value2;
    }
}

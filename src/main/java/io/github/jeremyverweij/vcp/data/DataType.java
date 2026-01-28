package io.github.jeremyverweij.vcp.data;

import java.util.Objects;

public record DataType(String name, String collectionType, String defaultValue) {
    public boolean isSameSuperType(DataType other){
        return Objects.equals(this.collectionType, other.collectionType);
    }
}

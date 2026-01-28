package io.github.jeremyverweij.vcp.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataTypeRegistry {
    private final Map<String, DataType> registry;

    public DataTypeRegistry(){
        this.registry = new HashMap<>();
    }

    public void register(DataType type){
        this.registry.put(type.name(), type);
    }

    public DataType get(String name){
        return this.registry.get(name);
    }

    public Collection<DataType> getAll(){
        return this.registry.values();
    }
}

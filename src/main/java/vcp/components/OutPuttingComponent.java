package vcp.components;

import vcp.Value;

public interface OutPuttingComponent<T> {
    Value<T> getOutValue();
    Class<T> getType();
}

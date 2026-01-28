package io.github.jeremyverweij.vcp.data.node;

import io.github.jeremyverweij.vcp.data.DataType;

public interface CodeNode {
    boolean canUseSuperTypeClass();

    int getOutConnections();
    boolean hasInConnection();

    int dataIOAmount();
    boolean canUseDirectValuesOrConstantsOnIO(int ioIndex);
    DataType getDataTypeForIO(int ioIndex);
    String getNameForIO(int ioIndex);

    String getName();
}

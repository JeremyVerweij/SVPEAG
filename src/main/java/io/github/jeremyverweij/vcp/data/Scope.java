package io.github.jeremyverweij.vcp.data;

import io.github.jeremyverweij.vcp.util.Tuple;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Scope {
    private final Scope superScope;
    private final Set<Tuple<DataType, String>> variables;

    public Scope(Scope scope){
        this.superScope = scope;
        this.variables = new HashSet<>();
    }

    public Scope(){
        this(null);
    }

    public void addVar(DataType type, String name){
        this.variables.add(new Tuple<>(type, name));
    }

    public void removeVar(String name){
        this.variables.removeIf(variable -> Objects.equals(variable.getValue2(), name));
    }

    public Set<Tuple<DataType, String>> getAvailableVariablesInScope(){
        Set<Tuple<DataType, String>> allVars = new HashSet<>();

        if(this.superScope != null){
            allVars.addAll(this.superScope.getAvailableVariablesInScope());
        }

        allVars.addAll(this.variables);

        return allVars;
    }

    public boolean isInScope(Scope other){
        if (this.superScope == null)
            return false;

        if (this.superScope == other)
            return true;

        return this.superScope.isInScope(other);
    }
}

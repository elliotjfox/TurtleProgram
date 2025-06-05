package com.example.mandaladrawer.parser;

import com.example.mandaladrawer.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableScope {

    private final List<Map<String, ProgramVariable>> list;

    public VariableScope() {
        list = new ArrayList<>();

        list.add(new HashMap<>());
    }

    public VariableScope(VariableScope copy) {
        list = new ArrayList<>(copy.list);

        addScopeLevel();
    }

    public void add(ProgramVariable variable) {
        list.getLast().put(variable.getName(), variable);
    }

    public void addScopeLevel() {
        list.add(new HashMap<>());
    }

    public boolean containsKey(String key) {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).containsKey(key)) return true;
        }
        return false;
    }

    public ProgramVariable get(String key) {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).containsKey(key)) return list.get(i).get(key);
        }
        return null;
    }
}

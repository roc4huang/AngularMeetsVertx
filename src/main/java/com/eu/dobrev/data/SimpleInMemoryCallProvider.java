package com.eu.dobrev.data;

import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * This class represents a simple in-memory call database
 *
 * To be used only for testing purposes
 *
 * Created by martindobrev on 16/01/16.
 */
public class SimpleInMemoryCallProvider implements CallProvider {

    private Map<String, Call> callDatabase;


    public SimpleInMemoryCallProvider() {
        if (callDatabase == null)
            callDatabase = new HashMap<>();
    }

    @Override
    public Collection<Call> getCalls() {
        return callDatabase.values();
    }

    @Override
    public Call getCall(String id) {
        return callDatabase.get(id);
    }

    @Override
    public boolean createCall(Call call) {

        // Do not add the call if the provided object is null
        if (call.id == null)
            return false;
        
        // Do not add the call if the ID already exists
        if (callDatabase.containsKey(call.id))
            return false;

        callDatabase.put(call.id, call);
        return true;
    }

    @Override
    public Call deleteCall(String id) {
        return callDatabase.remove(id);
    }

}

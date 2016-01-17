package com.eu.dobrev.data;

import java.util.Collection;

/**
 *
 * Simple interface for providing call data
 *
 * Created by martindobrev on 16/01/16.
 */
public interface CallProvider {

    public Collection<Call> getCalls();
    public Call getCall(String id);
    public boolean createCall(Call call);
    public Call deleteCall(String id);

}

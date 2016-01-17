package com.eu.dobrev;

import com.eu.dobrev.data.Call;
import com.eu.dobrev.data.CallProvider;
import com.eu.dobrev.data.SimpleInMemoryCallProvider;
import com.eu.dobrev.verticle.SimpleHttpServerVerticle;
import com.eu.dobrev.verticle.UselessVerticle;
import com.google.gson.Gson;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;

import java.util.UUID;

/**
 * Created by martindobrev on 16/01/16.
 */
public class VertxMain extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SimpleHttpServerVerticle(null));
    }

    private static CallProvider getDummyCallProvider() {
        CallProvider cp = new SimpleInMemoryCallProvider();
        for (int i = 0; i < 10; i++) {
            cp.createCall(createRandomCall());
        }
        return cp;
    }

    private static Call createRandomCall() {
        Call c = new Call();
        c.id = UUID.randomUUID().toString();
        c.callee = "+4917678772624";
        c.caller = "+35932696359";
        c.start = System.currentTimeMillis() - (long) Math.abs(Math.random() * 1000000);
        c.duration = 1000 + (long) (Math.random() * 1000);
        System.out.println("Creating dummy call:");
        System.out.println((new Gson()).toJson(c));
        return c;
    }
}

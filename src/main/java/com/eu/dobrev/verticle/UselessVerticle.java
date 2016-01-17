package com.eu.dobrev.verticle;

import io.vertx.core.AbstractVerticle;

/**
 * This is just a simple useless verticle
 * to test verticle deployment
 *
 * Created by martindobrev on 16/01/16.
 */
public class UselessVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();
        System.out.println("Useless verticle is being started...");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Useless verticle is being stopped...");
    }
}

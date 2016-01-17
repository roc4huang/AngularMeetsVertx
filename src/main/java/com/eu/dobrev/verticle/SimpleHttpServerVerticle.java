package com.eu.dobrev.verticle;

import com.eu.dobrev.data.Call;
import com.eu.dobrev.data.CallProvider;
import com.eu.dobrev.data.SimpleInMemoryCallProvider;
import com.google.gson.Gson;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.TemplateEngine;

import java.util.UUID;

/**
 *
 *
 * Created by martindobrev on 16/01/16.
 */
public class SimpleHttpServerVerticle extends AbstractVerticle {

    private HttpServer httpServer = null;
    private CallProvider callDataProvider;
    private Gson gson = new Gson();


    public SimpleHttpServerVerticle(CallProvider callProvider) {
        callDataProvider = callProvider;

        // if provider is not initialized - create a simple in-memory call provider
        if (callDataProvider == null) {
            callDataProvider = new SimpleInMemoryCallProvider();
        }
    }


    public void start() throws Exception {
        httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/calls/:callId").handler(this::handleGetCall);
        router.post("/calls/add").handler(this::handleAddCall);
        router.get("/calls").handler(this::handleListCalls);
        router.delete("/calls/:callId").handler(this::handleDeleteCall);

        StaticHandler sh = StaticHandler.create();

        sh.setIndexPage("index.html");
        router.route("/*").handler(sh);


        //router.get("/").

        httpServer.requestHandler(router::accept).listen(9999);
    }




    @Override
    public void stop() throws Exception {
        super.stop();
        if (httpServer != null) {
            httpServer.close();
        }
    }


    private void handleGetCall(RoutingContext routingContext) {
        String callId = routingContext.request().getParam("callId");
        HttpServerResponse response = routingContext.response();

        Call call = callDataProvider.getCall(callId);

        if (call == null) {
            sendError(400, response);
        } else {
            response.putHeader("content-type", "application/json").end(gson.toJson(call));
        }
    }


    private void handleAddCall(RoutingContext routingContext) {
        Gson gson = new Gson();
        HttpServerResponse response = routingContext.response();
        Call c = gson.fromJson(routingContext.getBodyAsString(), Call.class);

        if (c != null) {
            c.id = UUID.randomUUID().toString();
            callDataProvider.createCall(c);
            response.putHeader("content-type", "application/json").end(gson.toJson(c));
        } else {
            sendError(400, response);
        }
    }

    private void handleListCalls(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "application/json").end(gson.toJson(callDataProvider.getCalls()));
    }

    private void handleDeleteCall(RoutingContext routingContext) {
        String callId = routingContext.request().getParam("callId");
        HttpServerResponse response = routingContext.response();

        Call deletedCall = callDataProvider.deleteCall(callId);
        if (deletedCall == null) {
            sendError(400, response);
        } else {
            response.putHeader("content-type", "application/json").end(gson.toJson(deletedCall));
        }
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }
}

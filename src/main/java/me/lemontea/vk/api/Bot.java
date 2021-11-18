package me.lemontea.vk.api;

import com.google.gson.Gson;
import me.lemontea.vk.api.longpoll.LongPollClient;
import me.lemontea.vk.api.objects.keyboard.Keyboard;
import me.lemontea.vk.api.objects.templates.Template;
import me.lemontea.vk.api.request.Request;
import me.lemontea.vk.api.response.Response;
import me.lemontea.vk.api.util.query.QuerySerializer;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Bot {

    public static final Logger LOGGER = Logger.getLogger("Bot");

    private static final String API_VERSION = "5.131";
    private static final String API_URL = "https://api.vk.com/method/";

    private final Gson gson;

    private final ExecutorService httpClientExecutor;
    private final ExecutorService handlersExecutor;

    private final String accessToken;

    private final QuerySerializer serializer;

    private final HttpClient httpClient;

    public Bot(String accessToken) {
        this.accessToken = accessToken;

        gson = new Gson();

        httpClientExecutor = Executors.newFixedThreadPool(2);
        handlersExecutor = Executors.newFixedThreadPool(4);

        serializer = new QuerySerializer();
        httpClient = HttpClient.newBuilder().executor(httpClientExecutor).build();

        serializer.getSerializers().put(Keyboard.class, (serializer, obj) -> gson.toJson(obj, Keyboard.class));
        serializer.getSerializers().put(Template.class, (serializer, obj) -> gson.toJson(obj, Template.class));
    }

    public <R extends Response<?>> CompletableFuture<R> sendRequest(Request<R> request) throws URISyntaxException {
        HttpRequest httpRequest =
                HttpRequest.newBuilder(new URI(API_URL + request.getRequestInfo().methodName()))
                        .POST(HttpRequest.BodyPublishers.ofString(serializer.toQuery(request) + String.format(
                                "access_token=%s&v=%s",
                                accessToken, API_VERSION
                        )))
                        .build();

        return httpClient
                .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(httpResp -> gson.fromJson(httpResp.body(), request.getResponseType()));
    }

    public LongPollClient createLongPollClient(int groupId) {
        return new LongPollClient(this, handlersExecutor, groupId);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public Gson getGsonInstance() {
        return gson;
    }

    public void shutdown() {
        handlersExecutor.shutdown();
        httpClientExecutor.shutdown();
    }

}

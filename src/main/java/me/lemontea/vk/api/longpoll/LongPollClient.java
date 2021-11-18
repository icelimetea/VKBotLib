package me.lemontea.vk.api.longpoll;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.lemontea.vk.api.Bot;
import me.lemontea.vk.api.longpoll.adapters.EventJSONAdapter;
import me.lemontea.vk.api.longpoll.events.Event;
import me.lemontea.vk.api.longpoll.events.EventRegistry;
import me.lemontea.vk.api.request.requests.GroupLongPollServerRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.logging.Level;

public class LongPollClient {

    private static final int WAIT_TIME = 25;

    private final Bot bot;
    private final long groupId;

    private final HttpClient httpClient;

    private final EventRegistry eventRegistry;

    private final ExecutorService handlersExecutor;

    private final Gson gson;

    private volatile boolean running;

    public LongPollClient(Bot bot, HttpClient httpClient, ExecutorService handlersExecutor, long groupId) {
        this.bot = bot;
        this.groupId = groupId;

        this.httpClient = httpClient;

        this.handlersExecutor = handlersExecutor;

        eventRegistry = new EventRegistry();

        gson = new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventJSONAdapter(eventRegistry))
                .create();

        running = true;
    }

    public void start() {
        try {
            bot.sendRequest(new GroupLongPollServerRequest(groupId)).thenAccept(resp -> {
                if (resp.getError() != null) {
                    Bot.LOGGER.log(Level.SEVERE, "Long poll API error: " + resp.getError().getErrorMessage());

                    return;
                }

                pollEvents(
                        resp.getResponse().getLongPollServer(),
                        resp.getResponse().getLongPollKey(),
                        resp.getResponse().getLongPollTimestamp()
                );
            }).exceptionally(e -> {
                Bot.LOGGER.log(Level.SEVERE, "Exception occurred while trying to start polling!", e);

                start();

                return null;
            });
        } catch (URISyntaxException e) {
            Bot.LOGGER.log(Level.SEVERE, "API URI is wrong!", e);
        }
    }

    private void pollEvents(String server, String key, String timestamp) {
        if (!running)
            return;

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(String.format(
                    "%s?act=a_check&key=%s&ts=%s&wait=%d",
                    server, key, timestamp, WAIT_TIME
            ))).GET().build();

            CompletionStage<LongPollResponse> httpRespFuture =
                    httpClient
                            .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                            .thenApply(strResp -> gson.fromJson(strResp.body(), LongPollResponse.class));

            httpRespFuture
                    .thenAccept(resp -> pollEvents(server, key, resp.getTimestamp()))
                    .exceptionally(e -> {
                        Bot.LOGGER.log(Level.SEVERE, "Unable to connect to the long poll server!", e);

                        start();

                        return null;
                    });

            httpRespFuture
                    .thenAccept(resp -> {
                        for (Event event : resp.getEvents()) {
                            if (event == null)
                                continue;

                            for (BiConsumer<Bot, LongPollPayload> handler : eventRegistry.getHandlers(event.type()))
                                handlersExecutor.submit(() -> {
                                    try {
                                        handler.accept(bot, event.object());
                                    } catch (Exception e) {
                                        Bot.LOGGER.log(Level.SEVERE, "Exception caught while processing the handler!", e);
                                    }
                                });
                        }
                    }).exceptionally(e -> {
                        Bot.LOGGER.log(Level.SEVERE, "Unable to handle the events!", e);

                        return null;
                    });
        } catch (URISyntaxException e) {
            Bot.LOGGER.log(Level.SEVERE, "Long poll URI is incorrect!", e);
        }
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public void shutdown() {
        running = false;
    }

}

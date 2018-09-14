package com.fm.assignment.api;

/**
 * Created by Lenovo on 11/09/2018.
 */

import com.fm.assignment.websocket.Notification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketEndpointTest {
    @Value("${local.server.port}")
    private int port;
    private String URL;

    private static final String SEND_CREATE_BOARD_ENDPOINT = "/app/create/";
    private static final String SEND_MOVE_ENDPOINT = "/app/notify/";
    private static final String SUBSCRIBE_CREATE_BOARD_ENDPOINT = "/topic/board/";
    private static final String SUBSCRIBE_MOVE_ENDPOINT = "/topic/notify/";


    private CompletableFuture<Notification> completableFuture;

    @Before
    public void setup() {
        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/freight";
    }

    @Test
    public void testCreateGameEndpoint() throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
        String uuid = UUID.randomUUID().toString();

        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, TimeUnit.SECONDS);

        stompSession.subscribe(SUBSCRIBE_CREATE_BOARD_ENDPOINT + uuid, new CustomStompFrameHandler());
        stompSession.send(SEND_CREATE_BOARD_ENDPOINT + uuid, null);

        Notification notification = completableFuture.get(10, TimeUnit.SECONDS);

        Assert.assertNotNull(notification);
    }

    @Test
    public void testMakeMoveEndpoint() throws InterruptedException, ExecutionException, TimeoutException {
        String uuid = UUID.randomUUID().toString();

        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, TimeUnit.SECONDS);

        stompSession.subscribe(SUBSCRIBE_MOVE_ENDPOINT + uuid, new CustomStompFrameHandler());
        stompSession.send(SEND_MOVE_ENDPOINT + uuid,TimeUnit.SECONDS);
        Notification gameStateAfterMove = completableFuture.get(5, TimeUnit.SECONDS);

        Assert.assertNotNull(gameStateAfterMove);
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }


    private class CustomStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return Notification.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((Notification) o);
        }
    }
}

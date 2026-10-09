package nexus.io.websocket.ify.config;

import nexus.io.context.BootConfiguration;
import nexus.io.tio.boot.server.TioBootServer;
import nexus.io.tio.boot.websocket.WebSocketRouter;
import nexus.io.tio.http.server.router.HttpRequestRouter;
import nexus.io.websocket.ify.handler.WebsocketIfyIndexHandler;
import nexus.io.websocket.ify.handler.WebsockifyHandler;

public class WebHelloConfig implements BootConfiguration {

  public void config() {

    TioBootServer server = TioBootServer.me();
    HttpRequestRouter requestRouter = server.getRequestRouter();
    if (requestRouter != null) {

      WebsocketIfyIndexHandler helloHandler = new WebsocketIfyIndexHandler();
      requestRouter.get("/websocketify", helloHandler::index);
    }

    WebSocketRouter router = TioBootServer.me().getWebSocketRouter();
    if (router != null) {
      router.add("/websockify", new WebsockifyHandler());
    }

  }
}

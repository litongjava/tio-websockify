package nexus.io.websocket.ify.handler;

import nexus.io.model.body.RespBodyVo;
import nexus.io.tio.boot.http.TioRequestContext;
import nexus.io.tio.http.common.HttpRequest;
import nexus.io.tio.http.common.HttpResponse;

public class WebsocketIfyIndexHandler {
  public HttpResponse index(HttpRequest request) {
    RespBodyVo respVo = RespBodyVo.ok() ;
    return TioRequestContext.getResponse().setJson(respVo);
  }
}

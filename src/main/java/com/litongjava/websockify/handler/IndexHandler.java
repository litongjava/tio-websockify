package com.litongjava.websockify.handler;

import com.litongjava.model.body.RespBodyVo;
import com.litongjava.tio.boot.http.TioRequestContext;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;

public class IndexHandler {
  public HttpResponse index(HttpRequest request) {
    RespBodyVo respVo = RespBodyVo.ok() ;
    return TioRequestContext.getResponse().setJson(respVo);
  }
}

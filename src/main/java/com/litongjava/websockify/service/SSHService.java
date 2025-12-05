package com.litongjava.websockify.service;

import com.litongjava.tio.utils.base64.Base64Utils;
import com.litongjava.tio.utils.json.JsonUtils;
import com.litongjava.websockify.vo.SSHTarget;

public class SSHService {

  public SSHTarget decodeBase64(String msg) {
    String decodeToString = Base64Utils.decodeToString(msg);
    return JsonUtils.parse(decodeToString, SSHTarget.class);
  }
}

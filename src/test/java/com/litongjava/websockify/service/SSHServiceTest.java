package com.litongjava.websockify.service;

import org.junit.Test;

import com.litongjava.tio.utils.base64.Base64Utils;

public class SSHServiceTest {

  @Test
  public void test() {
    String msg="eyJ1c2VybmFtZSI6InJvb3QiLCJwYXNzd29yZCI6IjIyIiwiaG9zdCI6IjEyNy4wLjAuMSIsInBvcnQiOjIyfQ==";
    String decodeToString = Base64Utils.decodeToString(msg);
    System.out.println(decodeToString);
  }
}

package nexus.io.websocket.ify;

import org.junit.Test;

import nexus.io.tio.utils.base64.Base64Utils;

public class SSHServiceTest {

  @Test
  public void test() {
    String msg = "eyJ1c2VybmFtZSI6InJvb3QiLCJwYXNzd29yZCI6IjIyIiwiaG9zdCI6IjEyNy4wLjAuMSIsInBvcnQiOjIyfQ==";
    String decodeToString = Base64Utils.decodeToString(msg);
    System.out.println(decodeToString);
  }
}

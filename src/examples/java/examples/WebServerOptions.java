package examples;

import io.vertx.core.json.JsonObject;
import io.vertx.docgen.Source;

import java.util.ArrayList;
import java.util.List;

@Source(translate = false)
public class WebServerOptions {

  private String host;
  private int port;
  private String root;
  private List<String> certificates;

  public WebServerOptions() {
    host = "localhost";
    port = 80;
    root = "content";
    certificates = new ArrayList<>();
  }

  public WebServerOptions(WebServerOptions that) {
    host = that.host;
    port = that.port;
    root = that.root;
    certificates = new ArrayList<>(that.certificates);
  }

  public WebServerOptions(JsonObject json) {
    host = json.getString("host", "localhost");
    port = json.getInteger("port", 80);
    root = json.getString("root", "content");
    certificates = json.getJsonArray("certificates") != null ? json.getJsonArray("certificates").getList() : new ArrayList<>();
  }

  public WebServerOptions setHost(String host) {
    this.host = host;
    return this;
  }

  public WebServerOptions setPort(int port) {
    this.port = port;
    return this;
  }

  public WebServerOptions setRoot(String root) {
    this.root = root;
    return this;
  }

  public WebServerOptions setCertificates(List<String> certificates) {
    this.certificates = certificates;
    return this;
  }

  public WebServerOptions addCertificate(String certificate) {
    this.certificates.add(certificate);
    return this;
  }
}

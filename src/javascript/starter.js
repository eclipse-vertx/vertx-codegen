load("src/javascript/require.js");

var Vertx = require("src/gen/javascript/vertx");

var io = Packages.io;

var vertx = new Vertx(io.vertx.core.VertxFactory.newVertx());

var options = {
  host: "localhost",
  port: 1234
};

var netServer = vertx.createNetServer(options);

netServer.connectHandler(function(conn) {
  java.lang.System.out.println("Got conn " + conn);
  conn.dataHandler(function(buff) {
    java.lang.System.out.println("Got buff " + buff);
    conn.writeBuffer(buff);
  })
});

netServer.listen();

java.lang.Thread.sleep(100000);
package com.hll.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import com.firenio.baseio.component.SslContext;
import com.firenio.baseio.component.SslContextBuilder;

public class JettyStartup {

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getProperty("server.port"));
        boolean enableSSL = Boolean.parseBoolean(System.getProperty("server.enableSSL"));
        boolean deploy = Boolean.parseBoolean(System.getProperty("server.deploy"));
        startup(port, enableSSL, deploy);
    }

    private static String getCurrentPath() throws UnsupportedEncodingException {
        URL url = JettyStartup.class.getClassLoader().getResource(".");
        String path;
        if (url == null) {
            path = new File(".").getAbsoluteFile().getParent();
        } else {
            path = new File(url.getFile()).getAbsoluteFile().getPath();
        }
        return URLDecoder.decode(path, "UTF-8");
    }

    public static void startup(int port, boolean enableSSL, boolean deploy) throws Exception {
        Server server = new Server();
        HttpConfiguration httpConfig = new HttpConfiguration();
        ServerConnector httpsConnector;
        if (enableSSL) {
            httpConfig.setSecureScheme("https");
            SslContext sslContext = buildSslContext();
            httpsConnector = new ServerConnector(server,
                    new JettySslConnectionFactory(sslContext, "http/1.1"),
                    new HttpConnectionFactory(httpConfig));
        } else {
            httpConfig.setSecureScheme("http");
            httpsConnector = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
        }

        httpsConnector.setPort(port);
        httpsConnector.setIdleTimeout(180000);
        server.addConnector(httpsConnector);

        String webapp;
        if (deploy) {
            webapp = getCurrentPath() + "/app";
        } else {
            webapp = "src/main/webapp";
        }

        HashSessionManager sm = new HashSessionManager();
        sm.setSessionCookie("JSESSIONID_" + port);
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setBaseResource(Resource.newResource(webapp));
        context.setWelcomeFiles(new String[]{"/index.html"});
        context.setSessionHandler(new SessionHandler(sm));
        server.setHandler(context);
        server.start();
        server.join();
    }

    private static SslContext buildSslContext() throws IOException {
        File storeFile = new File(getCurrentPath() + "/conf/server.keystore");
        System.out.println("^^^^^^^^^^^^^^^^^" + storeFile.getPath());
        if (storeFile.exists()) {
            //return SSLUtil.initServer(storeFile, "123412", "ryms", "123412");
            return SslContextBuilder.forServer()
                    .keyManager(new FileInputStream(storeFile), "123412", "ryms", "123412")
                    .build();
        }
        File keyFile = new File(getCurrentPath() + "/conf/2042846_api.yuanxiaoai.cn.key");
        File certFile = new File(getCurrentPath() + "/conf/2042846_api.yuanxiaoai.cn.pem");
        //return SSLUtil.initServer(keyFile, certFile);
        return SslContextBuilder.forServer()
                .keyManager(new FileInputStream(keyFile), new FileInputStream(certFile)).build();
    }

}

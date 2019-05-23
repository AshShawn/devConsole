package com.hll.test;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;

import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.ssl.SslConnection;
import org.eclipse.jetty.server.AbstractConnectionFactory;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;

import com.firenio.baseio.component.SslContext;


public class JettySslConnectionFactory extends AbstractConnectionFactory {

    private final SslContext sslContext;
    private final String _nextProtocol;
    private boolean renegotiationAllowed;

    public JettySslConnectionFactory(SslContext sslContext, String nextProtocol) {
        super("SSL-" + nextProtocol);
        _nextProtocol = nextProtocol;
        this.sslContext = sslContext;
    }

    public SslContext getSslContext() {
        return sslContext;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();

        SSLEngine engine = sslContext.newEngine("127.0.0.1",0);
        engine.setUseClientMode(false);
        SSLSession session = engine.getSession();

        if (session.getPacketBufferSize() > getInputBufferSize()) {
            setInputBufferSize(session.getPacketBufferSize());
        }
    }

    @Override
    public Connection newConnection(Connector connector, EndPoint endPoint) {
        InetSocketAddress remote = endPoint.getRemoteAddress();
        InetAddress address = remote.getAddress();
        SSLEngine engine = sslContext.newEngine(address.getHostAddress(), remote.getPort());
        engine.setUseClientMode(false);

        SslConnection sslConnection = newSslConnection(connector, endPoint, engine);
        sslConnection.setRenegotiationAllowed(isRenegotiationAllowed());
        configure(sslConnection, connector, endPoint);

        ConnectionFactory next = connector.getConnectionFactory(_nextProtocol);
        EndPoint decryptedEndPoint = sslConnection.getDecryptedEndPoint();
        Connection connection = next.newConnection(connector, decryptedEndPoint);
        decryptedEndPoint.setConnection(connection);

        return sslConnection;
    }

    protected SslConnection newSslConnection(Connector connector, EndPoint endPoint,
                                             SSLEngine engine) {
        return new SslConnection(connector.getByteBufferPool(), connector.getExecutor(), endPoint,
                engine);
    }

    @Override
    public String toString() {
        return String.format("%s@%x{%s}", this.getClass().getSimpleName(), hashCode(),
                getProtocol());
    }

    public boolean isRenegotiationAllowed() {
        return renegotiationAllowed;
    }

    public void setRenegotiationAllowed(boolean renegotiationAllowed) {
        this.renegotiationAllowed = renegotiationAllowed;
    }

}

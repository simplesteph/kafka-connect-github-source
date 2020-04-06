package com.simplesteph.kafka;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.Closeable;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class HttpClientProvider implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(HttpClientProvider.class);
    private PoolingHttpClientConnectionManager poolingConnectionManager;
    private CloseableHttpClient httpClient;
    public HttpClientProvider(){
        CreateAllSSLHttpClient();
    }
    public void CreateAllSSLHttpClient() {
        try {
            TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> socketFactoryRegistry =
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("https", sslsf)
                            .register("http", new PlainConnectionSocketFactory())
                            .build();

            this.poolingConnectionManager  =
                    new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            this.httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                    .setConnectionManager(poolingConnectionManager).build();

            log.info("Successfully created AcceptAllSSLHttpClient");
        }
        catch (GeneralSecurityException ex){
            log.error("Unable to create AcceptAllSSLHttpClient");
        }
    }
    public CloseableHttpClient getHttpClient(){
        return httpClient;
    }
    @Override
    public void close() throws IOException {
        if(httpClient!=null)
            httpClient.close();
        if(poolingConnectionManager!=null) {
            poolingConnectionManager.close();
            poolingConnectionManager.shutdown();
        }
    }
}

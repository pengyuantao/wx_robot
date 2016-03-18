package main.java.com.wuji.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

public class CustomRetryHandler implements HttpRequestRetryHandler {

    Logger log = Logger.getLogger(CustomRetryHandler.class);

    private int maxExecutionCount;

    public CustomRetryHandler(int executionCount) {

        this.maxExecutionCount = executionCount;
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {

        if (executionCount >= maxExecutionCount) {
            // Do not retry if over max retry count
            log.error("retry over max times");
            return false;
        }
        if (exception instanceof InterruptedIOException) {
            // Timeout
            log.error("Timeout");
            return false;
        }
        if (exception instanceof UnknownHostException) {
            // Unknown host
            log.error("Unknown host");
            return false;
        }
        if (exception instanceof ConnectTimeoutException) {
            // Connection refused
            log.error("Connection refused");
            return false;
        }
        if (exception instanceof SSLException) {
            // SSL handshake exception
            log.error("SSL handshake exception");
            return false;
        }
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        HttpRequest request = clientContext.getRequest();
        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
        if (idempotent) {
            // Retry if the request is considered idempotent
            return true;
        }
        return false;
    }

}

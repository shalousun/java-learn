package com.yusun.rpc.framework;

import java.io.Serializable;

/**
 * @author yu 2020/11/8.
 */
public class URL implements Serializable {

    private String hostname;

    private Integer port;

    public static URL builder(){
        return new URL();
    }

    public String getHostname() {
        return hostname;
    }

    public URL setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public URL setPort(Integer port) {
        this.port = port;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"hostname\":\"")
                .append(hostname).append('\"');
        sb.append(",\"port\":")
                .append(port);
        sb.append('}');
        return sb.toString();
    }
}

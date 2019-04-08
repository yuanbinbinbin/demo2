package com.base.baselibrary.net.callback;

import okhttp3.Response;

public class HttpException extends Exception {
    private final int code;
    private final String message;
    private final transient Response response;

    public HttpException(Response response) {
        this.code = response.code();
        this.message = response.message();
        this.response = response;
    }

    /**
     * HTTP status code.
     */
    public int code() {
        return code;
    }

    /**
     * HTTP status message.
     */
    public String message() {
        return message;
    }

    /**
     * 获取body
     *
     * @return
     */
    public String body() {
        try {
            if (response != null) {
                return response.body().string();
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    public Response response() {
        return response;
    }

    @Override
    public String toString() {
        return "HttpException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}

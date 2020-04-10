
package com.uguryasar.weatherforecast.network.extra;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        String requestLog = String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers());

        if (request.method().compareToIgnoreCase("post") == 0) {
            requestLog = "\n" + requestLog + "\n" + bodyToString(request);
        }

        Request original = request.newBuilder()
                .headers(request.headers())
                //.addHeader("CacheLang", LoginActivity.ACTIVE_LANGUAGE)
                .method(request.method(), request.body())
                .removeHeader("Accept-Encoding")
                .addHeader("Accept-Encoding", "identity")
                .build();


        Response response = null;
        try {
            response = chain.proceed(original);
        } catch (SocketTimeoutException | UnknownHostException socketEx) {
        }
        long t2 = System.nanoTime();

        String responseLog = String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, original.headers());

        String bodyString = response.body().string();

        //   Log.d("TAG", "response" + "\n" + responseLog + "\n" + response.body().string());
        Log.d("TAG", "PRE RESPONSE>>>" + "\n" + responseLog + "\n" + bodyString);


        //TODO:GENERIC RESPONSE BODY DECRYPTION
        //bodyString = DecryptInterceptor.getDecodedList(bodyString, "GET");
        //Log.d("TAG", "EDITED RESPONSE<<<" + "\n" + responseLog + "\n" + bodyString);

        // response.body().string();
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }

    private static String bodyToString(@NonNull final Request request) {
        try {
            final Request copy = request.newBuilder().removeHeader("Content-Length").build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (@NonNull final IOException e) {
            return "did not work";
        }
    }

    public static RequestBody create(@Nullable final
                                     MediaType contentType,
                                     final byte[] content) {

        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content);
            }
        };
    }
}
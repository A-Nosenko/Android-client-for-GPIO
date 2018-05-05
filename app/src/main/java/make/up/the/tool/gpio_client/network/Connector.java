package make.up.the.tool.gpio_client.network;

import android.util.Log;

import java.io.IOException;

import make.up.the.tool.gpio_client.model.Device;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static make.up.the.tool.gpio_client.config.Constants.AUTHORIZATION;
import static make.up.the.tool.gpio_client.config.Constants.COLON;
import static make.up.the.tool.gpio_client.config.Constants.GET_MODE;
import static make.up.the.tool.gpio_client.config.Constants.HTTPS;
import static make.up.the.tool.gpio_client.config.Constants.LOG_MARKER;
import static make.up.the.tool.gpio_client.config.Constants.SLASH;

/**
 * @author Anatolii Nosenko
 * @version 04 May 2018
 */
public class Connector {

    private static OkHttpClient buildOkHttpClient(final Device device) {

        return new OkHttpClient().newBuilder().addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder builder = originalRequest
                                .newBuilder()
                                .header(AUTHORIZATION, Credentials.basic(device.getLogin(), device.getPassword()));
                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                }
        ).build();
    }

    public static String checkRelayMode(Device device) {

        Request request = new Request.Builder()
                .url(HTTPS + device.getHost() + COLON + device.getPort() + SLASH + GET_MODE)
                .build();

        String answer = null;
        try {
            Response response = buildOkHttpClient(device).newCall(request).execute();
            if (response == null) {
                return null;
            }
            answer = response.body().string();
        } catch (IOException e) {
            Log.e(LOG_MARKER, e.getMessage());
        }
        return answer;
    }
}

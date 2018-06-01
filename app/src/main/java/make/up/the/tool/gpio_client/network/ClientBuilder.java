package make.up.the.tool.gpio_client.network;

import java.io.IOException;

import make.up.the.tool.gpio_client.model.Device;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static make.up.the.tool.gpio_client.config.Constants.AUTHORIZATION;

/**
 * @author Anatolii Nosenko
 * @version 29 May 2018
 */
public class ClientBuilder {
    static OkHttpClient buildOkHttpClient(final Device device) {

        return new OkHttpClient().newBuilder().addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder builder = originalRequest
                                .newBuilder()
                                .header(AUTHORIZATION, Credentials.basic(device.getLogin(),
                                        device.getPassword()));
                        return chain.proceed(builder.build());
                    }
                }
        ).build();
    }
}

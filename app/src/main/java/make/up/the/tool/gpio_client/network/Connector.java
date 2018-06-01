package make.up.the.tool.gpio_client.network;

import android.util.Log;

import java.io.IOException;

import make.up.the.tool.gpio_client.model.Device;
import okhttp3.Request;
import okhttp3.Response;

import static make.up.the.tool.gpio_client.config.Constants.HTTPS;
import static make.up.the.tool.gpio_client.config.Constants.LOG_MARKER;
import static make.up.the.tool.gpio_client.config.Constants.SLASH;

/**
 * @author Anatolii Nosenko
 * @version 29 May 2018
 */
public class Connector {
    public Response getResponse(Device device, String claim) {
        String host = device.getHost();
        if (host == null || host.isEmpty()) {
            return null;
        }

        Request request = new Request.Builder()
                .url(HTTPS + host + SLASH + claim)
                .build();

        Response response = null;
        try {
            response = ClientBuilder.buildOkHttpClient(device).newCall(request).execute();
        } catch (IOException e) {
            Log.e(LOG_MARKER, e.getMessage());
        }
        return response;
    }
}

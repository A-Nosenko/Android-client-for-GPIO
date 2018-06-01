package make.up.the.tool.gpio_client.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import make.up.the.tool.gpio_client.model.Device;
import make.up.the.tool.gpio_client.model.Relay;
import make.up.the.tool.gpio_client.parsers.RelayParser;
import okhttp3.Response;

import static make.up.the.tool.gpio_client.config.Constants.GET_MODE;
import static make.up.the.tool.gpio_client.config.Constants.GET_RELAY;
import static make.up.the.tool.gpio_client.config.Constants.GET_RELAYS;
import static make.up.the.tool.gpio_client.config.Constants.GET_RELAYS_COUNT;
import static make.up.the.tool.gpio_client.config.Constants.LOG_MARKER;
import static make.up.the.tool.gpio_client.config.Constants.RELAY;
import static make.up.the.tool.gpio_client.config.Constants.STATUS;
import static make.up.the.tool.gpio_client.config.Constants.SWITCH;
import static make.up.the.tool.gpio_client.config.Constants.VALUE;

/**
 * @author Anatolii Nosenko
 * @version 04 May 2018
 */
public class RequestManager {
    private static final String TAG = RequestManager.class.getName();
    private static Connector connector = new Connector();

    public static String checkRelayMode(Device device) {

        String answer;
        Response response = connector.getResponse(device, GET_MODE);

        if (response == null || response.body() == null) {
            return null;
        }

        try {
            answer = response.body().string();
        } catch (IOException e) {
            Log.e(LOG_MARKER, e.getMessage());
            return null;
        }
        return answer;
    }

    public static int getRelaysCount(Device device) {
        int result;

        Response response = connector.getResponse(device, GET_RELAYS_COUNT);
        if (response == null || response.body() == null) {
            return -1;
        }
        try {
            result = Integer.valueOf(response.body().string());
        } catch (IOException | NumberFormatException e) {
            Log.e(LOG_MARKER, e.getMessage());
            return -1;
        }
        return result;
    }

    public static List<Relay> getRelays(Device device) {
        Response response = connector.getResponse(device, GET_RELAYS);
        List<Relay> relays = new ArrayList<>();

        if (response == null || response.body() == null) {
            return relays;
        }

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(response.body().string());
        } catch (IOException|JSONException e) {
            Log.e(LOG_MARKER, e.getMessage());
            return relays;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Relay relay = RelayParser
                        .parse(new JSONObject(jsonArray.getJSONObject(i).getString(VALUE)));
                Log.i(TAG, relay.getTechnicalName());
                relays.add(relay);
            } catch (JSONException e) {
                Log.i(LOG_MARKER, e.getMessage());
            }
        }

        return relays;
    }

    public static Relay getRelay(Device device, int id) {
        Response response = connector.getResponse(device, GET_RELAY + id);

        if (response == null || response.body() == null) {
            return null;
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response.body().string());
        } catch (IOException|JSONException e) {
            Log.e(LOG_MARKER, e.getMessage());
            return null;
        }

        JSONObject relayJSON;
        try {
            relayJSON = jsonObject.getJSONObject(RELAY);
        } catch (JSONException e) {
            Log.e(LOG_MARKER, e.getMessage());
            return null;
        }

        if (relayJSON == null) {
            return null;
        }

        return RelayParser.parse(relayJSON);
    }

    public static int switchRelay(Device device, int id, boolean status) {

        int result;
        Response response = connector.getResponse(device, SWITCH + "=" + id + "&" + STATUS + "=" + status);
        if (response == null || response.body() == null) {
            return -1;
        }
        try {
            result = Integer.valueOf(response.body().string());
        } catch (IOException | NumberFormatException e) {
            Log.e(LOG_MARKER, e.getMessage());
            return -1;
        }
        return result;
    }
}

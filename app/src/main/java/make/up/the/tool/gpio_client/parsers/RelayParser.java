package make.up.the.tool.gpio_client.parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import make.up.the.tool.gpio_client.model.Relay;

import static make.up.the.tool.gpio_client.config.Constants.*;

/**
 * @author Anatolii Nosenko
 * @version 07 May 2018
 */
public class RelayParser {
    private static Relay parce(JSONObject jsonObject) {
        String technicalName = null;
        String customName = null;
        boolean enabled = false;

        try {
            technicalName = jsonObject.getString(TECHNICAL_NAME);
            customName = jsonObject.getString(CUSTOM_NAME);
            enabled = jsonObject.getBoolean(ENABLED);
        } catch (JSONException e) {
            Log.e(RelayParser.class.getName(), e.getMessage());
        }

        return new Relay(technicalName, customName, enabled);
    }
}

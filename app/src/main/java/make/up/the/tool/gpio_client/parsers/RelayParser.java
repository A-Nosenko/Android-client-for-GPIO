package make.up.the.tool.gpio_client.parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import make.up.the.tool.gpio_client.model.Relay;

import static make.up.the.tool.gpio_client.config.Constants.CUSTOM_NAME;
import static make.up.the.tool.gpio_client.config.Constants.ENABLED;
import static make.up.the.tool.gpio_client.config.Constants.ID;
import static make.up.the.tool.gpio_client.config.Constants.TECHNICAL_NAME;

/**
 * @author Anatolii Nosenko
 * @version 07 May 2018
 */
public class RelayParser {
    public static Relay parse(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        int id;
        String technicalName;
        String customName;
        boolean enabled;

        try {
            id = jsonObject.getInt(ID);
            technicalName = jsonObject.getString(TECHNICAL_NAME);
            customName = jsonObject.getString(CUSTOM_NAME);
            enabled = jsonObject.getBoolean(ENABLED);
        } catch (JSONException e) {
            Log.e(RelayParser.class.getName(), e.getMessage());
            return null;
        }

        return new Relay(id, technicalName, customName, enabled);
    }
}

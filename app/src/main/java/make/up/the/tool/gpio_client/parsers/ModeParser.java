package make.up.the.tool.gpio_client.parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static make.up.the.tool.gpio_client.config.Constants.EMPTY;
import static make.up.the.tool.gpio_client.config.Constants.LOG_MARKER;
import static make.up.the.tool.gpio_client.config.Constants.MODE;

/**
 * @author Anatolii Nosenko
 * @version 07 May 2018
 */
public class ModeParser {
    public static String parse(JSONObject jsonObject) {
        String result = null;
        try {
            result = (String) jsonObject.get(MODE);
        } catch (JSONException e) {
            Log.e(LOG_MARKER, e.getMessage());
        }
        if (result == null) {
            Log.e(LOG_MARKER, "Can't read mode!");
            return EMPTY;
        }
        return result;
    }
}

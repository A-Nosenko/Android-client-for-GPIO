package make.up.the.tool.gpio_client.model;

import android.content.Context;
import android.content.SharedPreferences;

import static make.up.the.tool.gpio_client.config.Constants.CUSTOM_NAME;
import static make.up.the.tool.gpio_client.config.Constants.EMPTY;
import static make.up.the.tool.gpio_client.config.Constants.HOST;
import static make.up.the.tool.gpio_client.config.Constants.LOGIN;
import static make.up.the.tool.gpio_client.config.Constants.PASSWORD;
import static make.up.the.tool.gpio_client.config.Constants.PORT;

/**
 * @author Anatolii Nosenko
 * @version 30 April 2018
 */
public class Device {
    private final String factoryName;

    private String customName;
    private String host;
    private String port;
    private String login;
    private String password;
    private boolean connected;

    public Device(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void saveSetting(Context context) {
        SharedPreferences preferences = context
                .getSharedPreferences(getFactoryName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CUSTOM_NAME, getCustomName());
        editor.putString(HOST, getHost());
        editor.putString(PORT, getPort());
        editor.putString(LOGIN, getLogin());
        editor.putString(PASSWORD, getPassword());
        editor.apply();
    }

    public void restoreSetting(Context context) {
        SharedPreferences preferences = context
                .getSharedPreferences(getFactoryName(), Context.MODE_PRIVATE);
        setCustomName(preferences.getString(CUSTOM_NAME, EMPTY));
        setHost(preferences.getString(HOST, EMPTY));
        setPort(preferences.getString(PORT, EMPTY));
        setLogin(preferences.getString(LOGIN, EMPTY));
        setPassword(preferences.getString(PASSWORD, EMPTY));
    }
}

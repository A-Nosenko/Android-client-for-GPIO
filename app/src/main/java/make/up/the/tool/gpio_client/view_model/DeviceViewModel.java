package make.up.the.tool.gpio_client.view_model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import make.up.the.tool.gpio_client.BR;
import make.up.the.tool.gpio_client.model.Device;
import make.up.the.tool.gpio_client.network.Connector;
import make.up.the.tool.gpio_client.parsers.ModeParser;
import make.up.the.tool.gpio_client.view_model.button_interfaces.ConnectionButton;

import static make.up.the.tool.gpio_client.config.Constants.DISCONNECTED;
import static make.up.the.tool.gpio_client.config.Constants.LOG_MARKER;
import static make.up.the.tool.gpio_client.config.Constants.MODE_RELAY;

/**
 * @author Anatolii Nosenko
 * @version 01 May 2018
 */
public class DeviceViewModel extends BaseObservable implements ConnectionButton {
    private Device device;
    private Context context;

    private boolean connectingInProcess;

    public DeviceViewModel(Device device, Context context) {
        this.device = device;
        this.context = context;
        connectingInProcess = false;
    }

    public String getFactoryName() {
        return device.getFactoryName();
    }

    public String getCustomName() {
        return device.getCustomName();
    }

    public void setCustomName(String customName) {
        device.setCustomName(customName);
    }

    public String getHost() {
        return device.getHost();
    }

    public void setHost(String host) {
        device.setHost(host);
    }

    public String getPort() {
        return device.getPort();
    }

    public void setPort(String port) {
        device.setPort(port);
    }

    public String getLogin() {
        return device.getLogin();
    }

    public void setLogin(String login) {
        device.setLogin(login);
    }

    public String getPassword() {
        return device.getPassword();
    }

    public void setPassword(String password) {
        device.setPassword(password);
    }

    @Bindable
    public boolean isConnectingInProcess() {
        return connectingInProcess;
    }

    public void setConnectingInProcess(boolean connectingInProcess) {
        this.connectingInProcess = connectingInProcess;
        notifyPropertyChanged(BR.connectingInProcess);
    }

    @Bindable
    public boolean isConnected() {
        return device.isConnected();
    }

    public void setConnected(boolean connected) {
        device.setConnected(connected);
        notifyPropertyChanged(BR.connected);
    }

    public void save(Context context) {
        device.saveSetting(context);
    }

    @Override
    public void connect() {
        setConnectingInProcess(true);
        new ConnectionTask().execute();
    }

    private class ConnectionTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void ... args) {
            return Connector.checkRelayMode(device);
        }

        @Override
        protected void onPostExecute(String result) {
            setConnectingInProcess(false);
            Toast.makeText(context,
                    (result == null) ? DISCONNECTED : result,
                    Toast.LENGTH_SHORT)
                    .show();
            if (result == null) {
                setConnected(false);
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (ModeParser.parse(jsonObject).equals(MODE_RELAY)) {
                    setConnected(true);
                }
            } catch (JSONException e) {
                Log.e(LOG_MARKER, e.getMessage());
            }
        }
    }
}

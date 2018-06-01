package make.up.the.tool.gpio_client.view_model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.AsyncTask;
import android.util.Log;

import make.up.the.tool.gpio_client.config.DevicesHolder;
import make.up.the.tool.gpio_client.model.Device;
import make.up.the.tool.gpio_client.model.Relay;
import make.up.the.tool.gpio_client.network.RequestManager;
import make.up.the.tool.gpio_client.view_model.button_interfaces.SwitchRelayButton;

import static make.up.the.tool.gpio_client.config.Constants.LOG_MARKER;

/**
 * @author Anatolii Nosenko
 * @version 12 May 2018
 */
public class RelayViewModel extends BaseObservable implements SwitchRelayButton {

    private final int deviceId;
    private boolean connecting;
    private Relay relay;

    public RelayViewModel(int deviceId, Relay relay) {
        this.deviceId = deviceId;
        this.relay = relay;
        connecting = false;
    }

    public String getRelayTechnicalName() {
        return relay.getTechnicalName();
    }

    public String getRelayCustomName() {
        return relay.getCustomName();
    }

    @Bindable
    public boolean isRelayEnabled() {
        return relay.isEnabled();
    }

    @Bindable
    public boolean isConnecting() {
        return connecting;
    }

    @Override
    public void switchRelay() {
        new SwitchTask().execute();
    }

    private class SwitchTask extends AsyncTask<Void, Void, Relay> {

        @Override
        protected void onPreExecute() {
            connecting = true;
            notifyChange();
        }

        @Override
        protected Relay doInBackground(Void... voids) {
            int result;
            Log.e(LOG_MARKER,
                    "Try to switch relay " + relay.getId() + " in " + !relay.isEnabled());
            Device device = DevicesHolder.getHolder().getDevices()[deviceId];
            result = RequestManager.switchRelay(device, relay.getId(), !relay.isEnabled());
            if (result != -1) {
                return RequestManager.getRelay(DevicesHolder.getHolder().getDevices()[deviceId], relay.getId());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Relay relayNew) {
            connecting = false;
            if (relayNew != null) {
                relay = relayNew;
                notifyChange();
            }
        }
    }
}

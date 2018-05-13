package make.up.the.tool.gpio_client.view_model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.AsyncTask;

import make.up.the.tool.gpio_client.model.Relay;

/**
 * @author Anatolii Nosenko
 * @version 12 May 2018
 */
public class RelayViewModel extends BaseObservable {

    private Relay relay;

    public RelayViewModel(Relay relay) {
        this.relay = relay;
    }

    public int getRelayId() {
        return relay.getId();
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
}

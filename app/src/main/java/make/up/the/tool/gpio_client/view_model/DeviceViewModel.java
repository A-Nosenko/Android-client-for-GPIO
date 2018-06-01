package make.up.the.tool.gpio_client.view_model;

import android.content.Context;
import android.databinding.BaseObservable;

import make.up.the.tool.gpio_client.model.Device;

/**
 * @author Anatolii Nosenko
 * @version 01 May 2018
 */
public class DeviceViewModel extends BaseObservable {
    private Device device;

    public DeviceViewModel(Device device, Context context) {
        this.device = device;
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

    public void save(Context context) {
        device.saveSetting(context);
    }

}

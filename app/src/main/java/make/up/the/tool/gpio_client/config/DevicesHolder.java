package make.up.the.tool.gpio_client.config;

import make.up.the.tool.gpio_client.model.Device;

import static make.up.the.tool.gpio_client.config.Constants.DEVICE_A;
import static make.up.the.tool.gpio_client.config.Constants.DEVICE_B;
import static make.up.the.tool.gpio_client.config.Constants.DEVICE_C;
import static make.up.the.tool.gpio_client.config.Constants.DEVICE_COUNT;

/**
 * @author Anatolii Nosenko
 * @version 30 April 2018
 */
public class DevicesHolder {
    private static DevicesHolder holder;

    private Device[] devices;

    private DevicesHolder() {
        devices = new Device[DEVICE_COUNT];
        devices[0] = new Device(DEVICE_A);
        devices[1] = new Device(DEVICE_B);
        devices[2] = new Device(DEVICE_C);
    }

    public static DevicesHolder getHolder() {
        if (holder == null) {
            holder = new DevicesHolder();
        }
        return holder;
    }

    public Device[] getDevices() {
        return devices;
    }
}

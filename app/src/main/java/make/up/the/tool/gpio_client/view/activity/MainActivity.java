package make.up.the.tool.gpio_client.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import make.up.the.tool.gpio_client.R;
import make.up.the.tool.gpio_client.config.DevicesHolder;
import make.up.the.tool.gpio_client.model.Device;
import make.up.the.tool.gpio_client.view.fragment.FragmentWithList;

import static make.up.the.tool.gpio_client.config.Constants.DEVICE_COUNT;

/**
 * @author Anatolii Nosenko
 * @version 30 April 2018
 */
public class MainActivity extends SingleFragmentActivity {

    private int currentDeviceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Device device : DevicesHolder.getHolder().getDevices()) {
            device.restoreSetting(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTitle();
    }

    @Override
    protected Fragment getFragment() {
        return getFragment(0);
    }

    private Fragment getFragment(int id) {
        if ((id < 0) || (id >= DEVICE_COUNT)) {
            return null;
        }
        return FragmentWithList.newInstance(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.device_A:
                if (currentDeviceId != 0) {
                    currentDeviceId = 0;
                    replaceFragment();
                    updateTitle();
                }
                break;
            case R.id.device_B:
                if (currentDeviceId != 1) {
                    currentDeviceId = 1;
                    replaceFragment();
                    updateTitle();
                }
                break;
            case R.id.device_C:
                if (currentDeviceId != 2) {
                    currentDeviceId = 2;
                    replaceFragment();
                    updateTitle();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment() {
        if ((currentDeviceId < 0) || (currentDeviceId >= DEVICE_COUNT)) {
            return;
        }
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_for_fragment, getFragment(currentDeviceId))
                .commit();
    }

    public void updateTitle() {
        if ((currentDeviceId < 0) || (currentDeviceId >= DEVICE_COUNT)) {
            return;
        }
        setTitle(DevicesHolder.getHolder().getDevices()[currentDeviceId].getCustomName());
    }
}

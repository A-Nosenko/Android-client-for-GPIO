package make.up.the.tool.gpio_client.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import make.up.the.tool.gpio_client.R;
import make.up.the.tool.gpio_client.config.DevicesHolder;
import make.up.the.tool.gpio_client.databinding.FragmentWithListBinding;
import make.up.the.tool.gpio_client.network.Connector;
import make.up.the.tool.gpio_client.view_model.DeviceViewModel;
import make.up.the.tool.gpio_client.view_model.button_interfaces.SettingButton;

/**
 * @author Anatolii Nosenko
 * @version 30 April 2018
 */
public class FragmentWithList extends Fragment implements SettingButton {

    private static final String CURRENT_DEVICE_ID = "CURRENT_DEVICE_ID";

    private int deviceId;
    private int relaysCount = -1;
    private DeviceViewModel viewModel;

    public static FragmentWithList newInstance(int id) {
        FragmentWithList fragment = new FragmentWithList();
        Bundle args = new Bundle();
        args.putInt(CURRENT_DEVICE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            deviceId = args.getInt(CURRENT_DEVICE_ID, 0);
        }
        viewModel = new DeviceViewModel(DevicesHolder.getHolder().getDevices()[deviceId], getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentWithListBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_with_list, container, false);
        binding.setDeviceViewModel(viewModel);
        binding.setSettingButton(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        new RelaysCount().execute();
    }

    @Override
    public void openSetting() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentWithSettingDialog dialog = FragmentWithSettingDialog.newInstance(deviceId);
        dialog.show(fragmentManager, getContext().getString(R.string.setting));
    }

    private class RelaysCount extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            return Connector.getRelaysCount(DevicesHolder.getHolder().getDevices()[deviceId]);
        }

        @Override
        protected void onPostExecute(Integer count) {
            if (count == null) {
                return;
            }
            relaysCount = count;
            Toast.makeText(getContext(), "Relays count = " + relaysCount, Toast.LENGTH_SHORT).show();
        }
    }
}

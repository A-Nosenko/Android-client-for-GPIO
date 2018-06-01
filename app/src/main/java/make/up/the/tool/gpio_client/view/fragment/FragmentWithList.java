package make.up.the.tool.gpio_client.view.fragment;

import android.content.Context;
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
import android.widget.ProgressBar;

import java.util.List;

import make.up.the.tool.gpio_client.R;
import make.up.the.tool.gpio_client.config.DevicesHolder;
import make.up.the.tool.gpio_client.databinding.FragmentWithListBinding;
import make.up.the.tool.gpio_client.model.Relay;
import make.up.the.tool.gpio_client.network.RequestManager;
import make.up.the.tool.gpio_client.view.fragment.recycler_view.ViewAdapter;
import make.up.the.tool.gpio_client.view_model.DeviceViewModel;
import make.up.the.tool.gpio_client.view_model.button_interfaces.SettingButton;

/**
 * @author Anatolii Nosenko
 * @version 30 April 2018
 */
public class FragmentWithList extends Fragment implements SettingButton {

    private static final String CURRENT_DEVICE_ID = "CURRENT_DEVICE_ID";

    private int deviceId;
    private FragmentWithListBinding binding;
    private ProgressBar progressBar;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        DeviceViewModel deviceViewModel = new DeviceViewModel(
                DevicesHolder.getHolder().getDevices()[deviceId], getContext());

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_with_list, container, false);
        binding.setDeviceViewModel(deviceViewModel);
        binding.setSettingButton(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View view = binding.getRoot();
        progressBar = view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Loader().execute();
    }

    @Override
    public void openSetting() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        FragmentWithSettingDialog dialog = FragmentWithSettingDialog.newInstance(deviceId);
        Context context = getContext();
        if (context == null) {
            return;
        }
        dialog.show(fragmentManager, context.getString(R.string.setting));
    }

    class Loader extends AsyncTask<Void, Void, List<Relay>> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Relay> doInBackground(Void... voids) {
            return RequestManager.getRelays(DevicesHolder.getHolder().getDevices()[deviceId]);
        }

        @Override
        protected void onPostExecute(List<Relay> relays) {
            ViewAdapter adapter = new ViewAdapter(deviceId, relays);
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }
}

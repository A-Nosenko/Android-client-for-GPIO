package make.up.the.tool.gpio_client.view.fragment;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import make.up.the.tool.gpio_client.R;
import make.up.the.tool.gpio_client.config.DevicesHolder;
import make.up.the.tool.gpio_client.databinding.FragmentWithSettingBinding;
import make.up.the.tool.gpio_client.view.activity.MainActivity;
import make.up.the.tool.gpio_client.view_model.DeviceViewModel;

/**
 * @author Anatolii Nosenko
 * @version 02 May 2018
 */
public class FragmentWithSettingDialog extends DialogFragment {
    private static final String CURRENT_DEVICE_ID = "CURRENT_DEVICE_ID";

    private int deviceId;
    private DeviceViewModel viewModel;

    public static FragmentWithSettingDialog newInstance(int id) {
        FragmentWithSettingDialog fragment = new FragmentWithSettingDialog();
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentWithSettingBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()), R.layout.fragment_with_setting, null, false);
        binding.setDeviceViewModel(viewModel);
        return new AlertDialog.Builder(getContext())
                .setView(binding.getRoot())
                .create();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.save(getContext());
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.updateTitle();
            activity.replaceFragment();
        }
    }
}

package make.up.the.tool.gpio_client.view.fragment.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import make.up.the.tool.gpio_client.databinding.RelaysListItemBinding;
import make.up.the.tool.gpio_client.model.Relay;
import make.up.the.tool.gpio_client.view_model.RelayViewModel;

/**
 * @author Anatolii Nosenko
 * @version 31 May 2018
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Relay> relaysList;
    private LayoutInflater inflater;
    private final int deviceId;

    public ViewAdapter(int deviceId, List<Relay> relaysList) {
        this.deviceId = deviceId;
        this.relaysList = relaysList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        RelaysListItemBinding itemBinding = RelaysListItemBinding
                .inflate(inflater, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        RelayViewModel viewModel = new RelayViewModel(deviceId, relaysList.get(position));
        holder.bind(viewModel);
    }

    @Override
    public int getItemCount() {
        return relaysList.size();
    }
}
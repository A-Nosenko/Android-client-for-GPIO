package make.up.the.tool.gpio_client.view.fragment.recycler_view;

import android.support.v7.widget.RecyclerView;

import make.up.the.tool.gpio_client.databinding.RelaysListItemBinding;
import make.up.the.tool.gpio_client.view_model.RelayViewModel;

/**
 * @author Anatolii Nosenko
 * @version 31 May 2018
 */
class ViewHolder extends RecyclerView.ViewHolder {

    private RelaysListItemBinding itemBinding;

    ViewHolder(RelaysListItemBinding itemBinding) {
        super(itemBinding.getRoot());
        this.itemBinding = itemBinding;
    }

    void bind(RelayViewModel viewModel) {
        this.itemBinding.setRelayViewModel(viewModel);
    }
}

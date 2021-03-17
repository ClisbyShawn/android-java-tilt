package com.android.shawnclisby.javatilt;

import androidx.recyclerview.widget.RecyclerView;

import com.android.shawnclisby.javatilt.databinding.ViewHolderLoadBinding;
import com.android.shawnclisby.javatilt.models.Load;

public class LoadViewHolder extends RecyclerView.ViewHolder {

    private ViewHolderLoadBinding binding;

    public LoadViewHolder(ViewHolderLoadBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(Load load) {
        binding.tvLoadOriginCity.setText(Utils.formatCity(load.shipCity) + ", " + load.shipState + " " + load.ord_origin_zip);
        binding.tvLoadDestinationCity.setText(Utils.formatCity(load.conCity) + ", " + load.conState + " " + load.ord_dest_zip);
        binding.tvLoadPickupDate.setText(load.ord_startdate);
        binding.tvLoadDropOffDate.setText(load.ord_completiondate);
        binding.tvLoadCommodity.setText(load.cmd_name);
        binding.tvLoadWeight.setText(Utils.formatWeight(load.ord_totalweight));
        binding.tvLoadTrailer.setText(load.trailerName);
        binding.tvLoadProfit.setText("$" + load.ord_totalcharge);
        binding.tvLoadPerMile.setText("$" + load.totalpermile + "/mi.");
        binding.tvLoadMiles.setText(load.ord_totalmiles + "mi.");
    }
}

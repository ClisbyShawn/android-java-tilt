package com.android.shawnclisby.javatilt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.shawnclisby.javatilt.models.Load;
import com.android.shawnclisby.javatilt.repository.TiltRepository;

import java.util.List;

public class TiltViewModel extends ViewModel {

    private TiltRepository tiltRepository =
            new TiltRepository();

    private MutableLiveData<List<Load>> _liveLoads =
            new MutableLiveData(tiltRepository.getLoads());

    public LiveData<List<Load>> liveLoads = _liveLoads;

    private MutableLiveData<String[]> _trailerNames =
            new MutableLiveData(tiltRepository.getTrailerNames());
    public LiveData<String[]> trailerNames = _trailerNames;


    private MutableLiveData<String[]> _valueCommodities =
            new MutableLiveData(tiltRepository.getCommoditiesValues());

    public LiveData<String[]> commoditiesValue = _valueCommodities;

    private MutableLiveData<FilterEvents> _filterEvent = new MutableLiveData(FilterEvents.INITIAL);
    public LiveData<FilterEvents> filterEvent = _filterEvent;

    private MutableLiveData<ToolbarEvents> _toolEvent = new MutableLiveData(ToolbarEvents.COLLAPSED);
    public LiveData<ToolbarEvents> toolEvent = _toolEvent;

    public void chipSelection(FilterEvents event) {
        _filterEvent.postValue(event);
    }

    public void trailerSelected(FilterEvents event) {
        _filterEvent.postValue(event);
    }

    public void commoditySelected(FilterEvents event) {
        _filterEvent.postValue(event);
    }

    public void updateToolEvent(ToolbarEvents event) {
        _toolEvent.postValue(event);
    }

}

enum FilterEvents {
    TRAILER_SELECTED,
    COMMODITY_SELECTED,
    CHIP_SELECTED,
    INITIAL
}

enum ToolbarEvents {
    COLLAPSED,
    EXPANDED
}



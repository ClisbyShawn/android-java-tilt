package com.android.shawnclisby.javatilt;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.shawnclisby.javatilt.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private DatePickerDialog picker;
    private final SearchAction actions;

    public static SearchFragment newInstance(SearchAction action) {
        return new SearchFragment(action);
    }

    public SearchFragment(SearchAction action) {
        this.actions = action;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        //Locations
        binding.tieHomeOriginLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.tieHomeDestinationLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Sliders
        binding.sliderHomeOriginRadius.setLabelFormatter(Utils::formatMiles);
        binding.sliderHomeDestinationRadius.setLabelFormatter(Utils::formatMiles);

        //Dates
        binding.tieHomeFromDate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                picker = new DatePickerDialog(requireContext(), (DatePickerDialog.OnDateSetListener) (view, year, month, dayOfMonth) -> {
                    binding.tieHomeFromDate.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                }, Utils.getCurrentYear(), Utils.getCurrentMonth(), Utils.getCurrentDay());

                picker.show();
                return true;
            }
            return false;
        });
        binding.tieHomeToDate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                picker = new DatePickerDialog(requireContext(), (DatePickerDialog.OnDateSetListener) (view, year, month, dayOfMonth) -> {
                    binding.tieHomeToDate.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                }, Utils.getCurrentYear(), Utils.getCurrentMonth(), Utils.getCurrentDay());

                picker.show();
                return true;
            }
            return false;
        });

        //Weight
        binding.tieHomeMaxWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.tieHomeMaxWeight.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                actions.searchClicked();
                return true;
            }
            return false;
        });

        //Button Search
        binding.btnHomeSearch.setOnClickListener(v -> {
            actions.searchClicked();
        });

        //Button Reset
        binding.btnHomeReset.setOnClickListener(v -> {
            clearForm();
        });

        binding.btnHomeCancel.setOnClickListener(v -> {
            actions.searchCancelled();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void clearForm() {
        binding.tieHomeToDate.setText(null);
        binding.tieHomeFromDate.setText(null);
        binding.tieHomeMaxWeight.setText(null);
        binding.tieHomeDestinationLocation.setText(null);
        binding.tieHomeOriginLocation.setText(null);
    }

    interface SearchAction {
        void searchClicked();

        void searchCancelled();
    }
}

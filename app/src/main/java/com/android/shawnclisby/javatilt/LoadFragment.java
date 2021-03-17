package com.android.shawnclisby.javatilt;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.transition.TransitionManager;

import com.android.shawnclisby.javatilt.adapters.LoadsAdapter;
import com.android.shawnclisby.javatilt.adapters.SortPagerAdapter;
import com.android.shawnclisby.javatilt.databinding.FragmentLoadBinding;
import com.google.android.material.transition.MaterialContainerTransform;

import java.util.ArrayList;

public class LoadFragment extends Fragment implements SearchFragment.SearchAction {

    private FragmentLoadBinding binding;
    private TiltViewModel viewModel;
    private LoadsAdapter loadsAdapter;
    private SortPagerAdapter sortAdapter;
    private MaterialContainerTransform transition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentLoadBinding.inflate(inflater, container, false);

        ArrayList<Fragment> fragments = new ArrayList();
        fragments.add(SearchFragment.newInstance(this));

        loadsAdapter = new LoadsAdapter();
        sortAdapter = new SortPagerAdapter(getParentFragmentManager(), getLifecycle(), fragments);
        viewModel = new ViewModelProvider(requireActivity()).get(TiltViewModel.class);

        binding.recyclerLoadLoads.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerLoadLoads.setAdapter(loadsAdapter);

        binding.pagerLoadsViews.setAdapter(sortAdapter);

        binding.fabLoadSearch.setOnClickListener(v -> {
            transition = buildContainerTransformation();
            transition.setStartView(binding.fabLoadSearch);
            transition.setEndView(binding.cardLoadSearch);
            transition.addTarget(binding.cardLoadSearch);

            TransitionManager.beginDelayedTransition(binding.getRoot(), transition);
            binding.cardLoadSearch.setVisibility(View.VISIBLE);
            binding.viewLoadsScrim.setVisibility(View.VISIBLE);
            binding.fabLoadSearch.setVisibility(View.INVISIBLE);
        });

        binding.ivLoadFilter.setOnClickListener(v -> {
            if (viewModel.toolEvent.getValue() == ToolbarEvents.COLLAPSED) {
                alphaIn(binding.containerLoadFilter);
                viewModel.updateToolEvent(ToolbarEvents.EXPANDED);
            } else {
                alphaOut(binding.containerLoadFilter);
                viewModel.updateToolEvent(ToolbarEvents.COLLAPSED);
            }
        });

        binding.actLoadCommodity.setOnItemClickListener((parent, view, position, id) -> {
            viewModel.commoditySelected(FilterEvents.COMMODITY_SELECTED);
        });

        binding.actLoadTrailer.setOnItemClickListener((parent, view, position, id) -> {
            viewModel.trailerSelected(FilterEvents.TRAILER_SELECTED);
        });

        binding.chipGroupLoadContainer.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId > 0) viewModel.chipSelection(FilterEvents.CHIP_SELECTED);
            else viewModel.chipSelection(FilterEvents.INITIAL);
        });

        viewModel.liveLoads.observe(getViewLifecycleOwner(), loads -> {
            loadsAdapter.submitList(loads);
            binding.recyclerLoadLoads.setAdapter(loadsAdapter);
        });

        viewModel.trailerNames.observe(getViewLifecycleOwner(), trailers -> {
            ArrayAdapter trailerNames = new ArrayAdapter(requireContext(), R.layout.drop_down_trailer, trailers);
            binding.actLoadTrailer.setAdapter(trailerNames);
        });

        viewModel.commoditiesValue.observe(getViewLifecycleOwner(), commodityNames -> {
            ArrayAdapter commodityValues = new ArrayAdapter(requireContext(), R.layout.drop_down_commodity, commodityNames);
            binding.actLoadCommodity.setAdapter(commodityValues);
        });

        viewModel.filterEvent.observe(getViewLifecycleOwner(), event -> {
            if (event != FilterEvents.INITIAL)
                binding.viewLoadNotification.setVisibility(View.VISIBLE);
            else binding.viewLoadNotification.setVisibility(View.INVISIBLE);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("NewApi")
    private MaterialContainerTransform buildContainerTransformation() {
        MaterialContainerTransform materialContainerTransform =
                new MaterialContainerTransform();
        materialContainerTransform.setScrimColor(Color.TRANSPARENT);
        materialContainerTransform.setDuration(550);
        materialContainerTransform.setInterpolator(new FastOutSlowInInterpolator());
        materialContainerTransform.setFadeMode(MaterialContainerTransform.FADE_MODE_IN);
        return materialContainerTransform;
    }

    @Override
    public void searchClicked() {
        transitionToInitialState();
    }

    @Override
    public void searchCancelled() {
        transitionToInitialState();

    }

    private void transitionToInitialState() {
        transition.setStartView(binding.cardLoadSearch);
        transition.setEndView(binding.fabLoadSearch);
        transition.addTarget(binding.fabLoadSearch);

        TransitionManager.beginDelayedTransition(binding.getRoot(), transition);
        binding.cardLoadSearch.setVisibility(View.INVISIBLE);
        binding.viewLoadsScrim.setVisibility(View.INVISIBLE);
        binding.fabLoadSearch.setVisibility(View.VISIBLE);
    }

    public void alphaIn(View view) {
        view.setVisibility(View.VISIBLE);
        SpringAnimation springIn = new SpringAnimation(view, DynamicAnimation.ALPHA, 1f);
        springIn.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        springIn.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        springIn.start();
    }

    public void alphaOut(View view) {
        SpringAnimation springOut = new SpringAnimation(view, DynamicAnimation.ALPHA, 1f);
        springOut.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        springOut.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        springOut.start();
        springOut.addEndListener((animation, canceled, value, velocity) -> {
            view.setVisibility(View.GONE);
        });

    }

}

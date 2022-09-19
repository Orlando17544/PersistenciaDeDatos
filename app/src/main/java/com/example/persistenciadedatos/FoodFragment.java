package com.example.persistenciadedatos;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment implements OnFoodAdapterItemClickListener {

    private FoodListAdapter adapter;
    private DataViewModel dataViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Integer restaurantId;
    private String foodType;

    public FoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(int param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurantId = getArguments().getInt(ARG_PARAM1);
            foodType = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff(), this::onFoodAdapterItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        dataViewModel.getFoods(restaurantId, foodType).observe(getViewLifecycleOwner(), foods -> {
            adapter.submitList(foods);
        });

        registerForContextMenu(recyclerView);

        return view;
    }

    @Override
    public void onFoodAdapterItemClickListener(int position) {
        Intent intent = new Intent(getActivity(), NewFoodActivity.class);
        intent.putExtra(RestaurantActivity.FOOD_ACTIVITY, adapter.getCurrentList().get(position));
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RestaurantActivity.NEW_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Food food = data.getParcelableExtra(NewFoodActivity.EXTRA_REPLY);
            dataViewModel.insertFood(food);
        } else if (requestCode == RestaurantActivity.UPDATE_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getStringExtra(UpdateRestaurantActivity.EXTRA_ACTION).equals("update")) {
                Restaurant restaurant = data.getParcelableExtra(UpdateRestaurantActivity.EXTRA_REPLY);
                dataViewModel.updateRestaurant(restaurant);
            } else if (data.getStringExtra(UpdateRestaurantActivity.EXTRA_ACTION).equals("delete")) {
                Restaurant restaurant = data.getParcelableExtra(UpdateRestaurantActivity.EXTRA_REPLY);
                dataViewModel.deleteRestaurant(restaurant);
            }
        } else {
            Toast.makeText(
                    getActivity(),
                    "No se pudo completar la operación",
                    Toast.LENGTH_LONG).show();
        }
    }
}
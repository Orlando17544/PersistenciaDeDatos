package com.example.persistenciadedatos;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

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
    private Restaurant restaurant;
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
    public static FoodFragment newInstance(Restaurant param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurant = getArguments().getParcelable(ARG_PARAM1);
            foodType = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        MaterialToolbar topAppBar = getActivity().findViewById(R.id.topAppBar);

        topAppBar.setTitle(restaurant.getName());

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        Intent intent = new Intent(getActivity(), NewFoodActivity.class);
                        intent.putExtra("restaurantId", restaurant.getId());
                        startActivityForResult(intent, RestaurantActivity.NEW_FOOD_ACTIVITY_REQUEST_CODE);
                        return true;
                }
                return false;
            }
        });

        getActivity().findViewById(R.id.topAppBar);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff(), this::onFoodAdapterItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        dataViewModel.getFoods(restaurant.getId(), foodType).observe(getViewLifecycleOwner(), foods -> {
            adapter.submitList(foods);
        });

        registerForContextMenu(recyclerView);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RestaurantActivity.NEW_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Food food = data.getParcelableExtra(NewFoodActivity.EXTRA_REPLY);
            dataViewModel.insertFood(food);
        } else if (requestCode == RestaurantActivity.UPDATE_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getStringExtra(UpdateFoodActivity.EXTRA_ACTION).equals("update")) {
                Food food = data.getParcelableExtra(UpdateFoodActivity.EXTRA_REPLY);
                dataViewModel.updateFood(food);
            } else if (data.getStringExtra(UpdateFoodActivity.EXTRA_ACTION).equals("delete")) {
                Food food = data.getParcelableExtra(UpdateFoodActivity.EXTRA_REPLY);
                dataViewModel.deleteFood(food);
            }
        } else {
            Toast.makeText(
                    getActivity(),
                    "No se pudo completar la operación",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (this.isVisible()) {
            Intent intent = new Intent(getActivity(), UpdateFoodActivity.class);
            intent.putExtra(RestaurantActivity.UPDATE_FOOD_ACTIVITY, adapter.getSelectedFood());
            intent.putExtra(RestaurantActivity.UPDATE_FOOD_ACTIVITY2, restaurant);
            startActivityForResult(intent, RestaurantActivity.UPDATE_FOOD_ACTIVITY_REQUEST_CODE);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onFoodAdapterItemClickListener(int position) {
        Intent intent = new Intent(getActivity(), FoodActivity.class);
        intent.putExtra(RestaurantActivity.FOOD_ACTIVITY, adapter.getCurrentList().get(position));
        startActivity(intent);
    }
}
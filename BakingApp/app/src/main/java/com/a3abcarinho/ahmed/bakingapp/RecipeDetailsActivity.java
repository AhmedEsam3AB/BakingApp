package com.a3abcarinho.ahmed.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;



public class RecipeDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);
      if(getResources().getBoolean(R.bool.Tablet)){
          if(savedInstanceState == null) {
              getSupportFragmentManager().beginTransaction().replace(R.id.container, new StepDetailsActivity.StepDetailsFragment(), "DETAIL_FRAGMENT").commit();
          }
      }

        }


    public static class RecipeDetailFragment extends Fragment implements CustomOnClickListener {
        private RecyclerView ingredientRecyclerView;
        private RecyclerView stepRecyclerView;
        private Recipe recipe;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.recipe_detail_fragment,container,false);
            Intent intent = getActivity().getIntent();
            recipe = intent.getParcelableExtra(Recipe.KEY);
            getActivity().setTitle(recipe.getName());
            ingredientRecyclerView = (RecyclerView) view.findViewById(R.id.ingredientsRV);
            stepRecyclerView = (RecyclerView) view.findViewById(R.id.stepsRV);
            LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(getActivity());
            ingredientLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity(),recipe.getIngredientList());
            ingredientRecyclerView.setLayoutManager(ingredientLayoutManager);
            ingredientRecyclerView.setAdapter(ingredientAdapter);
            ingredientRecyclerView.setNestedScrollingEnabled(true);

            LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getActivity());
            stepLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), stepLayoutManager.getOrientation());
            StepAdapter stepAdapter = new StepAdapter(getActivity(),recipe.getStepList());
            stepAdapter.setOnClickListener(this);
            stepRecyclerView.setLayoutManager(stepLayoutManager);
            stepRecyclerView.addItemDecoration(dividerItemDecoration);
            stepRecyclerView.setAdapter(stepAdapter);
            return view;


        }
        @Override
        public void onClickListener(View view, int position) {
            ArrayList<Step> stepArrayList = new ArrayList<>();
            stepArrayList.addAll(recipe.getStepList());
            if(!getActivity().getResources().getBoolean(R.bool.Tablet)){
                Intent intent = new Intent(getActivity(), StepDetailsActivity.class);
                intent.putParcelableArrayListExtra(Step.KEY,stepArrayList);
                intent.putExtra("position", position);
                startActivity(intent);
            }else{
                Bundle arguments = new Bundle();
                arguments.putParcelableArrayList(Step.KEY,stepArrayList);
                arguments.putInt("position", position);
                StepDetailsActivity.StepDetailsFragment fragment = new StepDetailsActivity.StepDetailsFragment();
                fragment.setArguments(arguments);
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }

        }


    }
}


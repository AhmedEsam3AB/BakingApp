package com.a3abcarinho.ahmed.bakingapp;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public static class MainFragment extends Fragment implements OnRecipeReady, CustomOnClickListener {
        private RecyclerView recpRecyclerView;
        private RecipeAdapter recipeAdapter;
        private List<Recipe> recipeList;



        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.main_fragment, container, false);
            recpRecyclerView = (RecyclerView) view.findViewById(R.id.mainRV);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), Utilities.numberOfColumns(getActivity()));
            layoutManager.setOrientation(GridLayoutManager.VERTICAL);
            recpRecyclerView.setLayoutManager(layoutManager);
            Networking httpRequest = new Networking(this);
            httpRequest.sendGetRecp(Recipe.RECIPES_URL);
            recipeList = new ArrayList<>();
            recipeAdapter = new RecipeAdapter(getActivity(),recipeList);
            recipeAdapter.setOnClickListener(this);
            recpRecyclerView.setAdapter(recipeAdapter);
            return view;


        }
        public void setRecpRecyclerViewV(List<Recipe> recipe){
            int visible;
           if(Utilities.isConnected(getActivity())){
               if (recipe == null || recipe.isEmpty()){
                   visible = View.VISIBLE;
               }else{
                   visible = View.GONE;
               }
           }else {
               visible = View.VISIBLE;
           }

        }
        @Override
        public void getRecpList(final List<Recipe> recipes) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recipeList.addAll(recipes);
                    recipeAdapter.notifyDataSetChanged();
                    setRecpRecyclerViewV(recipes);
                }
            });

        }

        @Override
        public void onClickListener(View view, int position) {
            Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
            intent.putExtra(Recipe.KEY,recipeList.get(position));
            startActivity(intent);

        }
    }
}

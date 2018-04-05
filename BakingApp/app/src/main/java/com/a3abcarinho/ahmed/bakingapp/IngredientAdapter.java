package com.a3abcarinho.ahmed.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Ingredient> ingredientList;
    public IngredientAdapter(Context context,List<Ingredient> ingredientList){
        this.ingredientList = ingredientList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.ingredients_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ingredientTV.setText(" - " +ingredientList.get(position).getQuantity()+" "+ingredientList.get(position).getMeasure()+ " - "+ingredientList.get(position).getIngredient() );

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientTV;
        public ViewHolder(View itemView) {
            super(itemView);
            ingredientTV = (TextView) itemView.findViewById(R.id.ingredient_TV);
        }
    }
}

package com.a3abcarinho.ahmed.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
private Context context;
    private LayoutInflater layoutInflater;
    private List<Recipe> recipeList;
    private CustomOnClickListener onClickListener;
    public RecipeAdapter(Context context,List<Recipe> recipeList){
        this.context = context;
        this.recipeList = recipeList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setOnClickListener(CustomOnClickListener listener){
        onClickListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recipes_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.recpNameTV.setText(recipeList.get(position).getName());
        String imageUrl = recipeList.get(position).getImage();
        if (imageUrl.isEmpty()){
            imageUrl = null;
        }
        Picasso.with(context).load(imageUrl).placeholder(R.drawable.recp).into(holder.recpImage);

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView recpNameTV;
        public ImageView recpImage;
        public ViewHolder(View itemView) {
            super(itemView);
            recpImage = (ImageView) itemView.findViewById(R.id.recpImage);
            recpNameTV = (TextView) itemView.findViewById(R.id.recpName_TV);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(onClickListener != null){
                onClickListener.onClickListener(view,getAdapterPosition());
            }

        }
    }
}

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


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Step> stepList;
    private CustomOnClickListener onClickListener;
    public StepAdapter(Context context,List<Step> stepList){

        this.context = context;
        this.stepList = stepList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void setOnClickListener(CustomOnClickListener listener){
        onClickListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.steps_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.stepTV.setText(position+ " - "+stepList.get(position).getShortDescription());
        String thumbnailURl = stepList.get(position).getThumbnailUrl();
        if(thumbnailURl.isEmpty()){
            thumbnailURl = null;
        }
        Picasso.with(context).load(thumbnailURl).placeholder(R.drawable.recp).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView stepTV;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnails);
            stepTV = (TextView) itemView.findViewById(R.id.step_TV);
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

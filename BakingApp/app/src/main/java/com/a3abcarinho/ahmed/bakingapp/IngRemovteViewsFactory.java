package com.a3abcarinho.ahmed.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 18/01/18.
 */

class IngRemovteViewsFactory implements RemoteViewsService.RemoteViewsFactory, OnRecipeReady {
    private List<Ingredient> ingredientList;
    private List<Recipe> recipeList;
    public Context context;
    private int widgetID;
    private Networking networking;
    private AppWidgetManager appWidgetManager;
    public IngRemovteViewsFactory(Context context, Intent intent) {
        recipeList = new ArrayList<>();
        this.context = context;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        appWidgetManager = AppWidgetManager.getInstance(context);
    }

    @Override
    public void onCreate() {
        networking = new Networking(this);
        networking.sendGetRecp(Recipe.RECIPES_URL);

    }

    @Override
    public void onDataSetChanged() {


    }

    @Override
    public void onDestroy() {
        recipeList.clear();
        ingredientList.clear();

    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        if (recipeList != null){
            remoteViews.setTextViewText(R.id.recpWidget,recipeList.get(position).getName());
            ingredientList = recipeList.get(position).getIngredientList();
            StringBuilder stringBuilder = new StringBuilder();
            for(Ingredient ingredient:ingredientList){
                stringBuilder.append("-");
                stringBuilder.append(ingredient.getQuantity());
                stringBuilder.append(" - ");
                stringBuilder.append(ingredient.getMeasure());
                stringBuilder.append(" - ");
                stringBuilder.append(ingredient.getIngredient());
                stringBuilder.append("\n");
            }
            remoteViews.setTextViewText(R.id.ingredWidget,stringBuilder);
            Bundle bundle = new Bundle();
            bundle.putInt(WidgetProvider.ingredients, position);
            Intent intent = new Intent();
            intent.putExtras(bundle);
        }else{

        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void getRecpList(List<Recipe> recipes) {
        recipeList.addAll(recipes);
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetID,R.id.widgetContainer);

    }
}

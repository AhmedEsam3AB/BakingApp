package com.a3abcarinho.ahmed.bakingapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class Networking {
    private List<Recipe> recipeList;
    private OnRecipeReady recipeReady;
    public Networking(OnRecipeReady recipeReady){
        this.recipeReady = recipeReady;
    }
    public void sendGetRecp(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResp = response.body().string();
                setRecpList(stringResp);

            }
        });
    }

    public void setRecpList(String response) {
       recipeList = new ArrayList<>();
       List<Ingredient> ingredientList = new ArrayList<>();
       List<Step> stepList = new ArrayList<>();
       if (response != null)
       {
           try {
               JSONArray jsonArray = new JSONArray(response);
               for (int i=0;i<jsonArray.length();i++){
                   JSONObject jsonObject = jsonArray.getJSONObject(i);
                   Recipe recipe = new Recipe();
                   recipe.setId(jsonObject.getInt("id"));
                   recipe.setName(jsonObject.getString("name"));
                   recipe.setImage(jsonObject.getString("image"));
                   JSONArray ingredientJsonArray = jsonObject.getJSONArray("ingredients");
                   for (int j=0;j<ingredientJsonArray.length();j++){
                       JSONObject ingredientJsonObject = ingredientJsonArray.getJSONObject(j);
                       Ingredient ingredient = new Ingredient();
                       ingredient.setIngredient(ingredientJsonObject.getString("ingredient"));
                       ingredient.setMeasure(ingredientJsonObject.getString("measure"));
                       ingredient.setQuantity(ingredientJsonObject.getInt("quantity"));
                       ingredientList.add(ingredient);
                   }
                   JSONArray stepJsonArray = jsonObject.getJSONArray("steps");
                   for (int j=0;j<stepJsonArray.length();j++){
                       JSONObject stepJsonObject = stepJsonArray.getJSONObject(j);
                       Step step = new Step();
                       step.setId(stepJsonObject.getInt("id"));
                       step.setDescription(stepJsonObject.getString("description"));
                       step.setShortDescription(stepJsonObject.getString("shortDescription"));
                       step.setVideoUrl(stepJsonObject.getString("videoURL"));
                       step.setThumbnailUrl(stepJsonObject.getString("thumbnailURL"));
                       stepList.add(step);
                   }
                   recipe.setIngredientList(ingredientList);
                   recipe.setStepList(stepList);
                   recipeList.add(recipe);
                   ingredientList = new ArrayList<>();
                   stepList = new ArrayList<>();
               }
               recipeReady.getRecpList(recipeList);

           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
    }
    public List<Recipe> getRecipeList(){
        return recipeList;
    }
}

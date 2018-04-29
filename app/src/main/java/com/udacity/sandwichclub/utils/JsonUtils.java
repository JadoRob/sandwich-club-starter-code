package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LoggingPermission;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject sandwichName = sandwichObject.getJSONObject("name");
            sandwich.setMainName(sandwichName.getString("mainName"));
            sandwich.setImage(sandwichObject.getString("image"));
            sandwich.setPlaceOfOrigin(sandwichObject.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichObject.getString("description"));

            JSONArray alsoKnownAsArray = sandwichName.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = new ArrayList<String>();
            for(int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAsList.add(alsoKnownAsArray.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAsList);

            JSONArray ingredientsArray = sandwichObject.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<String>();
            for(int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }
            sandwich.setIngredients(ingredientsList);








        } catch (Exception e) {
            e.printStackTrace();
        }



        return sandwich;
    }
}

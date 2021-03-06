package com.udacity.sandwichclub;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> otherNames = sandwich.getAlsoKnownAs();
        List<String> sandwichIngredients = sandwich.getIngredients();
        TextView alsoKnownAsView = findViewById(R.id.also_known_tv);
        TextView alsoKnowlLabel = findViewById(R.id.also_known_label);
        TextView ingredientsView = findViewById(R.id.ingredients_tv);
        TextView placeOfOriginView = findViewById(R.id.origin_tv);
        TextView placeOfOriginLabel = findViewById(R.id.place_of_origin_label);
        TextView descriptionView = findViewById(R.id.description_tv);
        // if the sandwich has other names, iterate through the list and append them to the TextView
        if (otherNames.size() != 0) {
            for (int i = 0; i < otherNames.size(); i++) {
                alsoKnownAsView.append(otherNames.get(i));
                // apends a comma as long as the string is not the first or last
                if (i + 1 != otherNames.size()) {
                    alsoKnownAsView.append(", ");
                }
            }
        // if the sandwich doesn't have other names, hides the alsoKnownAs View
        } else {
            alsoKnownAsView.setVisibility(View.GONE);
            alsoKnowlLabel.setVisibility(View.GONE);
        }
        for (int i = 0; i < sandwichIngredients.size(); i++) {
            ingredientsView.append(sandwichIngredients.get(i));
        // apends a comma as long as the string is not the first, or last
            if (i + 1 != sandwichIngredients.size()) { ingredientsView.append(", "); }
        }
        // if a place of origin is unknown, hide the placeOfOrigin TextView
        if (sandwich.getPlaceOfOrigin().equals("")) {
            placeOfOriginLabel.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);
        } else {
            placeOfOriginView.setText(sandwich.getPlaceOfOrigin());
        }
        descriptionView.setText(sandwich.getDescription());
    }
}

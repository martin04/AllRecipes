package allrecipes.mpip.wbs.com.allrecipesmpip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.IngredientsAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.NutrientsAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.Ingredient;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.Nutrient;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeLOD;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.GetIngredients;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.GetNutrients;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.GetRecipe;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.OnDetailsResultHandler;

/**
 * Created by Martin on 8/14/2015.
 */
public class RecipeDetailsActivity extends AppCompatActivity implements OnDetailsResultHandler {

    //RecyclerViews
    private RecyclerView lstIngredients;
    private RecyclerView lstNutrients;

    //Adapters
    private IngredientsAdapter ingredAdapter;
    private NutrientsAdapter nutriAdapter;

    private LinearLayoutManager manager;
    private LinearLayoutManager manager_n;
    private TextView txtCuisine;
    private TextView txtServings;
    private TextView txtServingsSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        assert getSupportActionBar() != null;
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));

        txtCuisine = (TextView) findViewById(R.id.txtCuisine);
        txtServings = (TextView) findViewById(R.id.txtServings);
        txtServingsSize = (TextView) findViewById(R.id.txtServingsSize);

        manager = new LinearLayoutManager(this);
        manager_n = new LinearLayoutManager(this);

        lstIngredients = (RecyclerView) findViewById(R.id.lstIngredients);
        lstIngredients.setHasFixedSize(true);
        lstIngredients.setLayoutManager(manager);

        lstNutrients = (RecyclerView) findViewById(R.id.lstNutrients);
        lstNutrients.setHasFixedSize(true);
        lstNutrients.setLayoutManager(manager_n);


        //ovde povik kon asynctask za prevzemanje na detali
        new GetRecipe(this).execute(getIntent().getStringExtra("name"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.shopping_cart:
                startActivity(new Intent(this, ShoppingCartActivity.class));
                break;
            /*case R.id.home:

                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onRecipeResult(RecipeLOD r) {
        //fill the lists and textViews
        txtCuisine.setText(String.format("Cuisine:\n" + (Character.toUpperCase(r.getCuisine().charAt(0)) + r.getCuisine().substring(1))));
        txtServings.setText(String.format("Servings:\n" + r.getServings()));
        txtServingsSize.setText(String.format("Servings size:\n" + r.getServings_size()));

        new GetIngredients(this).execute(r.getId());
        new GetNutrients(this).execute(r.getId());
    }

    @Override
    public void onIngredientResult(List<Ingredient> ingredients) {
        ingredAdapter = new IngredientsAdapter(ingredients);
        lstIngredients.setAdapter(ingredAdapter);
    }

    @Override
    public void onNutrientResult(List<Nutrient> nutrients) {
        nutriAdapter = new NutrientsAdapter(nutrients, this);
        lstNutrients.setAdapter(nutriAdapter);
    }

    @Override
    public void onDetailsError() {
        Toast.makeText(this, "An error occured.", Toast.LENGTH_SHORT).show();
    }
}

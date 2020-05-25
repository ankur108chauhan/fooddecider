package com.ankur.fooddecider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView displayMenu;
    private EditText addMenuEdittext;
    private Button addFoodButton;
    private Button clearFoodButton;
    private Button decideButton;
    private ImageView logoImageView;
    private ArrayList<String> foodList = new ArrayList<>();
    private int randomIndex;
    private final String FOOD_LIST = "food_List";
    private final String COUNTER_LIST = "counter_List";
    private Vibrator vibrator;
    private int counter = 0;
    private SharedPreferences preferences;
    private SharedPreferences counterPreferences;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor counterEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayMenu = findViewById(R.id.displayfood_textview);
        addMenuEdittext = findViewById(R.id.newmenu_edittext);
        addFoodButton = findViewById(R.id.addfood_button);
        clearFoodButton = findViewById(R.id.clearfood_button);
        decideButton = findViewById(R.id.decide_button);
        logoImageView = findViewById(R.id.imageView);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        preferences = getSharedPreferences(FOOD_LIST,MODE_PRIVATE);
        counterPreferences = getSharedPreferences(COUNTER_LIST,MODE_PRIVATE);


        counter = counterPreferences.getInt("Counter",0);
        Log.d("Counter","onResponse" + counter);

        Map<String,?> map = preferences.getAll();

        for(Map.Entry<String,?> item : map.entrySet()) {
            foodList.add((String) item.getValue());
        }

        decideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(50);

                if(!foodList.isEmpty()) {
                    Random random = new Random();
                    randomIndex = Math.abs(random.nextInt(foodList.size()));
                    displayMenu.setText(foodList.get(randomIndex));
                }

            }
        });

        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrator.vibrate(50);
                String food = addMenuEdittext.getText().toString().trim();

                if(!food.isEmpty()) {

                    Toast.makeText(MainActivity.this,food + " added",
                            Toast.LENGTH_SHORT).show();
                    editor = preferences.edit();
                    counterEditor = counterPreferences.edit();
                    editor.putString("Food"+String.valueOf(counter),food);
                    counter++;
                    counterEditor.putInt("Counter",counter);
                    Log.d("foodAdded" ,"onClick " + food);
                    editor.apply();
                    counterEditor.apply();
                }

                foodList.add(food);
                addMenuEdittext.getText().clear();
            }
        });

        clearFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(50);
                displayMenu.setText("");
            }
        });

        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                preferences.edit().clear().apply();
                counterPreferences.edit().clear().apply();
                foodList.clear();
                displayMenu.setText("");
            }
        });
    }
}



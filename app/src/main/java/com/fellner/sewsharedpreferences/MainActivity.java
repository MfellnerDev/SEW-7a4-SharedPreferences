package com.fellner.sewsharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameTextInput = (EditText) findViewById(R.id.textfield_name);

        String currentSharedPreferenceNameValue = returnNameOutOfSharedPreferences();

        // if the current shared preferences have the name already saved
        if (!currentSharedPreferenceNameValue.equals(""))    {
            nameTextInput.setText(currentSharedPreferenceNameValue);
            Log.i(LOG_TAG, "Successfully loaded the name \"" + currentSharedPreferenceNameValue + "\" into the app.");
        } else {
            String initialValue = getResources().getString(R.string.initial_value);
            nameTextInput.setText(initialValue);
            Log.i(LOG_TAG, "There was no pre-saved name, loading \"" + initialValue + "\" into the app.");
        }

        // get the save button view
        final Button saveButton = (Button) findViewById(R.id.button_save);
        //attach an eventlistener to the save button
        saveButton.setOnClickListener(new View.OnClickListener()  {
            public void onClick (View v)    {
                String userNameInput = nameTextInput.getText().toString();
                storeInputIntoSharedPreferences(userNameInput);
                Log.i(LOG_TAG,"Successfully saved \"" + userNameInput + "\" into the shared preferences!");
            }
        });

        // get the delete button view
        final Button deleteButton = (Button) findViewById(R.id.button_delete);
        //attach an eventlistener to the delete button
        deleteButton.setOnClickListener(new View.OnClickListener()  {
            public void onClick (View v)    {
                String userNameInput = nameTextInput.getText().toString();
                deleteSharedAllPreferences();
                Log.i(LOG_TAG,"Successfully deleted \"" + userNameInput + "\" from the shared preferences!");
            }
        });
    }

    private void storeInputIntoSharedPreferences(String inputToStore)  {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);


        SharedPreferences.Editor edit = sharedPreferences.edit();
        // put our input in the editor
        edit.putString("name", inputToStore);
        //commit the changes -> they'll get saved
        edit.apply();
    }

    private void deleteSharedAllPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        //clear all shared preferences
        editor.clear();
        editor.apply();
    }

    private String returnNameOutOfSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);

        return sharedPreferences.getString("name", "");
    }
}
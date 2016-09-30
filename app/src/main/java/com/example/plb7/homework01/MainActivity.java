/** Paige Brinks, plb7
 * DUE 9/30/2016
 *
 * Build a simple calculator app
 */


package com.example.plb7.homework01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MainActivity extends AppCompatActivity {
    // declare instance variables
    private EditText value1;
    private EditText value2;
    private Spinner spinner;
    private String selectedOperator;
    private TextView result;
    private SharedPreferences savedValues;
    private Integer val1;
    private Integer val2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize variables
        value1 = (EditText) findViewById(R.id.value1);
        value2 = (EditText) findViewById(R.id.value2);
        spinner = (Spinner) findViewById(R.id.spinner);
        result = (TextView) findViewById(R.id.result);
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        selectedOperator = "+";

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operation_array,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    /** calculate(View v) performs calculation
     *
     * @param v, the View
     */
    public void calculate(View v) {
        // get users input
        selectedOperator = spinner.getSelectedItem().toString();
        val1 = Integer.parseInt( value1.getText().toString());
        val2 = Integer.parseInt( value2.getText().toString());
        int total = 0;

        // check for illegal operation
        if(selectedOperator.equals("/") && val2 == 0) {
            Toast.makeText(MainActivity.this,
                    "Cannot divide by zero!", Toast.LENGTH_LONG).show();
        } else {
            // perform correct operation based on what operator user selected
            if (selectedOperator.equals("+")) {
                total = val1 + val2;
            } else if (selectedOperator.equals("-")) {
                total = val1 - val2;
            } else if (selectedOperator.equals("*")) {
                total = val1 * val2;
            } else if (selectedOperator.equals("/")) {
                total = val1 / val2;
            }
            // display
            result.setText(Integer.toString(total));
        }
    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.putInt("value1", Integer.parseInt( value1.getText().toString()));
        editor.putInt("value2", Integer.parseInt( value2.getText().toString()));
        editor.putString("selectedOperator", selectedOperator);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        val1 = savedValues.getInt("value1", 1);
        val2 = savedValues.getInt("value2", 1);
        selectedOperator = savedValues.getString("selectedOperator", "+");
    }
}
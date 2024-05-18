package dev.lab.myelectricbill;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonCalculate, buttonClear;
    EditText editTextKwH, editTextRebate;
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonClear = findViewById(R.id.buttonClear);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        editTextKwH = findViewById(R.id.editTextKwH);
        editTextRebate = findViewById(R.id.editTextRebate);
        resultView = findViewById(R.id.resultView);

        buttonCalculate.setOnClickListener(this);
        buttonClear.setOnClickListener(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }
    public void openAboutPage(){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuAbout) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menuHome) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {
        if (v == buttonCalculate) {
            String number2 = editTextRebate.getText().toString();
            String number1 = editTextKwH.getText().toString();

            double consumption;
            double rebatePercentage;
            double totalCharges = 0;
            double finalCost = 0;
            try {
                consumption = Double.parseDouble(number1);
                rebatePercentage = Double.parseDouble(number2);
                totalCharges = 0;

                if (consumption <= 200) {
                    totalCharges = consumption * 0.218;
                } else if (consumption <= 300) {
                    totalCharges = 200 * 0.218 + (consumption - 200) * 0.334;
                } else if (consumption <= 600) {
                    totalCharges = 200 * 0.218 + 100 * 0.334 + (consumption - 300) * 0.516;
                } else {
                    totalCharges = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (consumption - 600) * 0.546;
                }

                double rebateAmount = totalCharges * rebatePercentage / 100;
                finalCost = totalCharges - rebateAmount;

                // Format the final cost to 2 decimal places
                DecimalFormat df = new DecimalFormat("0.00");
                String formattedFinalCost = df.format(finalCost);

                resultView.setText("Total: RM " + formattedFinalCost);

            } catch (NumberFormatException nfe) {
                Toast.makeText(this, "Please enter numbers in the input field", Toast.LENGTH_SHORT).show();
            } catch (Exception exception) {
                Toast.makeText(this, "Please enter numbers in the input field", Toast.LENGTH_SHORT).show();
            }

        } else if (v == buttonClear) {
            //Clear the input fields
            editTextKwH.setText("");
            editTextRebate.setText("");
            resultView.setText("");

        }
    }
}
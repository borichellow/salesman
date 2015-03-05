package com.example.boris.salesman;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    ArrayList coordinatesX = new ArrayList();
    ArrayList coordinatesY = new ArrayList();
    LinearLayout parent;
    int[] x = {R.id.coorX1, R.id.coorX2, R.id.coorX3, R.id.coorX4, R.id.coorX5, R.id.coorX6};
    int[] y = {R.id.coorY1, R.id.coorY2, R.id.coorY3, R.id.coorY4, R.id.coorY5, R.id.coorY6};

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        parent = (LinearLayout) findViewById(R.id.parent);
        for(int i = 0 ; i < 6; i++){
            View child = getLayoutInflater().inflate(R.layout.city_item, null);
            child.findViewById(R.id.coorX).setId(x[i]);
            child.findViewById(R.id.coorY).setId(y[i]);
            ((TextView) child.findViewById(R.id.number)).setText((i+1) + ") ");
            parent.addView(child);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        boolean flag = true;
        for(int i = 0; i < x.length; i++){
            EditText fieldX = (EditText) findViewById(x[i]);
            EditText fieldY = (EditText) findViewById(y[i]);
            String textX = (fieldX.getText()).toString();
            String textY = (fieldY.getText()).toString();
            if((textX.matches("") || textY.matches("")) && i != 5){
                Toast.makeText(this, "Fist 5 cities are mandatory.", Toast.LENGTH_SHORT).show();
                coordinatesX = new ArrayList();
                coordinatesY = new ArrayList();
                flag = false;
                break;
            }
            if(!textX.matches("") && !textY.matches("")){
                //System.out.println("Boris testing\n-----\n i = " + i);
                coordinatesX.add( Integer.parseInt(textX));
                coordinatesY.add( Integer.parseInt(textY));
            }
        }
        if(flag){
            Intent intent = new Intent(this, GraphActivity.class);
            for(int i = 0; i < coordinatesX.size(); i++){
                intent.putExtra("X" + i, (Integer) coordinatesX.get(i));
                intent.putExtra("Y" + i, (Integer) coordinatesY.get(i));
            }
            intent.putExtra("cityCount", (Integer) coordinatesX.size());
            startActivity(intent);
        }
    }

}

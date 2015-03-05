package com.example.boris.salesman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by boris on 3/4/15.
 */
public class GraphActivity extends Activity{

    int cityCount;
    ArrayList cities = new ArrayList();
    ArrayList<int[]> rout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.graph_page);

        TextView text = (TextView) findViewById(R.id.textView);

        getCities();
        rout = FindingRout.getInstance().find(cities);

        System.out.println("Boris testing\n-------------\nRouts: ");
        for (int i =0; i < rout.size(); i ++){
            System.out.println("---"+rout.get(i)[0] +"  "+ rout.get(i)[1]+ "---\n");
        }


        //text.setText("Some fucking text here");
        setContentView(new Draw(this, rout, cities));
    }

    private void getCities(){
        Intent intent = getIntent();
        //System.out.println("Boris testing\n-------\n Size = " + intent.getStringExtra("cityCount"));
        cityCount = intent.getIntExtra("cityCount", 0);
        for(int i = 0; i < cityCount; i++){
            int x = intent.getIntExtra("X"+i, 0);
            int y = intent.getIntExtra("Y" + i, 0);
            cities.add(new int[]{x,y});
        }
    }

}
class Draw extends View{

    Paint r, p;
    Rect rect;
    int cityRadius;
    ArrayList<int[]> rout;
    ArrayList<int[]> citiesOld;
    ArrayList<int[]> cities;

    public Draw(Context context, ArrayList<int[]> _rout, ArrayList<int[]> _cities) {
        super(context);
        p = new Paint();
        r = new Paint();
        rect = new Rect();
        cityRadius = 30;
        rout = _rout;
        citiesOld = _cities;
        cities = new ArrayList<int[]>();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(80, 102, 204, 255);
        r.setColor(Color.RED);
        r.setStrokeWidth(10);
        p.setColor(Color.BLUE);
        p.setStrokeWidth(10);

        int height = canvas.getHeight();
        int width = canvas.getWidth();
        int margin = 30;
        int k = findKoef(width - margin, height - margin);

        for(int i = 0; i < citiesOld.size(); i++){
            cities.add(new int[]{citiesOld.get(i)[0] * k, citiesOld.get(i)[1] * k});
        }

        for (int i = 0; i < rout.size(); i++){
            int[] start = new int[]{cities.get(rout.get(i)[0])[0] , cities.get(rout.get(i)[0])[1]};
            int[] end = new int[]{cities.get(rout.get(i)[1])[0] , cities.get(rout.get(i)[1])[1]};
            canvas.drawLine(start[0] , start[1] , end[0], end[1] , p);
        }

        for(int i = 0; i < cities.size(); i++){
            canvas.drawCircle(cities.get(i)[0], cities.get(i)[1] , cityRadius, r);
            String number = Integer.toString(i + 1);
            canvas.drawText(number, cities.get(i)[0], cities.get(i)[1] ,p);
        }

    }

    private int findKoef(int width, int height){
        double koef;
        int[] max = findMax();

        double koefX = width / max[0];
        double koefY = height / max[1];

        if(koefX < koefY){koef = koefX;}
        else{koef = koefY;}

        return (int) koef;
    }

    private int[] findMax(){
        int[] max = new int[]{0,0};
        for (int j =0; j< max.length; j++) {
            for (int i = 0; i < citiesOld.size(); i++) {
                if (citiesOld.get(i)[j] > max[j]){
                    max[j] = citiesOld.get(i)[j];
                }
            }
        }
        return max;
    }
}
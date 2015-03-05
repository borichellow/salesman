package com.example.boris.salesman;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Double.compare;

/**
 * Created by boris on 3/4/15.
 */
public class FindingRout {

    private static FindingRout instance;
    private ArrayList<ArrayList> matrix;
    private int baseCity1, baseCity2;
    private double baseLineA, baseLineB;
    ArrayList<int[]> cities;
    boolean isVertical = false;
    ArrayList<Integer> upCities, downCities;
    ArrayList<int[]> ribs;

    public static FindingRout getInstance() {
        if (instance == null) {
            instance = new FindingRout();
        }
        return instance;
    }

    public ArrayList<int[]> find(ArrayList<int[]> _cities){
        ribs = new ArrayList<int[]>();
        cities = _cities;
        distanceMatrix();
        baseLine();
        separaitCities();

        ArrayList<int[]> ribsUp = makingRout(upCities);
        ArrayList<int[]> ribsDown = makingRout(downCities);

        ribs.addAll(ribsDown);
        ribs.addAll(ribsUp);
        return ribs;
    }

    private ArrayList<int[]> makingRout(ArrayList<Integer> someCities){
        ArrayList<int[]> ribs = new ArrayList();

        ArrayList<Double> distansKoef = new ArrayList<Double>();

        // filling Koef array
        for(int i = 0; i < someCities.size(); i ++){
            distansKoef.add(
                    (Double)(matrix.get(someCities.get(i))).get(baseCity1)
                    / (Double)(matrix.get(someCities.get(i))).get(baseCity2));
        }

        int prevCity = baseCity2;

        // finding rids
        for(int i = 0; i < distansKoef.size(); i ++){
            Double max = Collections.max(distansKoef);
            for(int j = 0; j < distansKoef.size(); j ++){
                if (distansKoef.get(j) == max){
                    ribs.add(new int[]{prevCity, someCities.get(j)});
                    distansKoef.set(j, 0.0);
                    prevCity = someCities.get(j);
                }
            }
        }
        ribs.add(new int[]{prevCity, baseCity1});
        return ribs;
    }

    private void separaitCities(){
        downCities = new ArrayList<Integer>();
        upCities = new ArrayList<Integer>();
        for (int i = 0; i < cities.size(); i++){
            if (i != baseCity1 && i != baseCity2){
                if (!isVertical) {
                    double iY = baseLineA * cities.get(i)[0] + baseLineB;
                    if (iY <= cities.get(i)[1]) {
                        upCities.add(i);
                    } else {
                        downCities.add(i);
                    }
                }else{
                    if(baseLineA <= cities.get(i)[0]){
                        upCities.add(i);
                    }else{
                        downCities.add(i);
                    }
                }
            }
        }
    }

    private void baseLine(){
        double max = 0;
        int line = 0;
        for (int i = 0 ; i < matrix.size(); i++){
            ArrayList<Double> ar = matrix.get(i);
            double iMax = Collections.max(ar);
            if (iMax > max){
                max = iMax;
                line = i;
            }
        }
        int stolb = 0;
        for (int i = 0 ; i < matrix.get(line).size(); i++){
            if(compare((double) matrix.get(line).get(i) , (double) max) == 0){
                stolb = i;
                break;
            }
        }
        baseCity1 = line;
        baseCity2 = stolb;

        /*
        * normally line has equation such as:  y = Ax + B
        * we'll find this A -> baseLineA and B -> baseLineB
        */
        if (cities.get(baseCity2)[1] == cities.get(baseCity1)[1]){
            baseLineA = 0;
            baseLineB = cities.get(baseCity2)[1];
        }else if (cities.get(baseCity2)[0] == cities.get(baseCity1)[0]){
            baseLineA = cities.get(baseCity2)[0];
            baseLineB = 0;
            isVertical = true;
        }else{
            baseLineA = (double) (cities.get(baseCity2)[1] - cities.get(baseCity1)[1])/ (double)(cities.get(baseCity2)[0] - cities.get(baseCity1)[0]);
            baseLineB = -cities.get(baseCity1)[0]*baseLineA + cities.get(baseCity1)[1];
        }

    }

    public void distanceMatrix(){
        matrix = new ArrayList<ArrayList>();
        for (int i = 0 ; i < cities.size(); i++){
            matrix.add(new ArrayList());
            for (int j = 0 ; j < cities.size(); j++){
                matrix.get(i).add( distance(cities.get(i), cities.get(j)));
            }
        }
    }

    private double distance(int[] city1, int[] city2){
        int squareD = (int) (Math.pow(city1[0] - city2[0], 2) + Math.pow(city1[1] - city2[1], 2));
        double d = Math.abs(Math.pow(squareD, 1.0 / 2));
        return d;
    }

}

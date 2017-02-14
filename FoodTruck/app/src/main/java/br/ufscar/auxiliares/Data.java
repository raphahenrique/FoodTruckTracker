package br.ufscar.auxiliares;

import com.google.android.gms.maps.model.Marker;

import java.util.LinkedList;

import br.ufscar.foodtruck.Truck;

public class Data {
    private static int truckId = 0;
    public static LinkedList<Truck> truckList = new LinkedList<>();
    public static LinkedList<Marker> markers = new LinkedList<>();

    public static void addTruck(Truck t) {
        if (t.getId() == -1) {
            t.setId(truckId);
            truckId++;
            truckList.add(t);
        } else {
            for (int i = 0; i < truckList.size(); i++) {
                if (t.getId() == truckList.get(i).getId()) {
                    truckList.remove(i);
                    break;
                }

            }
            truckList.add(t);
        }
    }

    public static Truck getTruckById(int id) {
        for (Truck t : truckList) {
            if (t.getId() == id)
                return t;
        }
        return null;
    }
}

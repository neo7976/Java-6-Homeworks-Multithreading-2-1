import avto.CarImp;
import avto.Toyota;
import avto.Volvo;
import thread.Buyer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public final static int ISSUE = 2;
    public final static int PEOPLE = 4;
    public final static int TIME_ADMISSION = 4000;
    public final static int TIME_PEOPLE_BUY = 2000;

    public static void main(String[] args) {
        List<CarImp> auto = new ArrayList<>();
        Random random = new Random();

        Thread carThread = new Thread(() -> {
            CarImp newCar = null;

            int value;
            for (int i = 0; i < ISSUE; i++) {
                value = random.nextInt(2);
                synchronized (auto) {
                    switch (value) {
                        case 0 -> auto.add(newCar = new Toyota("Camry", 2016));
                        case 1 -> auto.add(newCar = new Volvo("XC90", 2021));
                        case 2 -> auto.add(newCar = new Toyota("RAV4", 2019));
                    }
                    newCar.admission();
                    auto.notify();
                }
                try {
                    Thread.sleep(TIME_ADMISSION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        carThread.start();

        Thread buy1 = new Buyer(1, TIME_PEOPLE_BUY, auto, carThread);
        Thread buy2 = new Buyer(2, TIME_PEOPLE_BUY, auto, carThread);
        Thread buy3 = new Buyer(3, TIME_PEOPLE_BUY, auto, carThread);
        Thread buy4 = new Buyer(4, TIME_PEOPLE_BUY, auto, carThread);
        buy1.start();
        buy2.start();
        buy3.start();
        buy4.start();

    }
}

package avto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public final static int ISSUE = 10;
    public final static int PEOPLE = 4;

    public static void main(String[] args) {
        List<CarImp> auto = new ArrayList<>();

        new Thread(() -> {
            CarImp newCar;
            Random random = new Random();
            int value;
            for (int i = 0; i < ISSUE; i++) {
                value = random.nextInt(2);
                synchronized (auto) {
                    switch (value) {
                        case 0 -> {
                            auto.add(newCar = new Toyota("Camry", 2016));
                            newCar.admission();
                        }
                        case 1 -> {
                            auto.add(newCar = new Volvo("XC90", 2021));
                            newCar.admission();
                        }
                        case 2 -> {
                            auto.add(newCar = new Toyota("RAV4", 2019));
                            newCar.admission();
                        }
                    }
                    auto.notify();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            for (int i = 1; i < PEOPLE; i++) {
                synchronized (auto) {
                    if (auto.isEmpty()) {
                        try {
                            auto.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.printf("Покупатель %d купил %s.\n", i, auto.remove(0));
                }
            }
        }).start();
    }


}

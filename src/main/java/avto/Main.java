package avto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public final static int ISSUE = 10;
    public final static int PEOPLE = 4;
    public final static int TIME_ADMISSION = 4000;
    public final static int TIME_PEOPLE_BUY = 500;

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
                        case 0 -> {
                            auto.add(newCar = new Toyota("Camry", 2016));
                        }
                        case 1 -> {
                            auto.add(newCar = new Volvo("XC90", 2021));
                        }
                        case 2 -> {
                            auto.add(newCar = new Toyota("RAV4", 2019));
                        }
                    }
                    newCar.admission();
                }
                try {
                    Thread.sleep(TIME_ADMISSION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        carThread.start();


        new Thread(() -> {
            int i;
            while (true) {
                i = (int) (Math.random() * PEOPLE + 1);
                System.out.println("Покупатель " + i + " зашёл в магазин");
                if (!auto.isEmpty()) {
                    synchronized (auto) {
                        System.out.printf("Покупатель %d купил %s.\n",
                                i,
                                auto.remove(0));
                    }
                } else
                    System.out.println("Машин нет");

                try {
                    Thread.sleep(TIME_PEOPLE_BUY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!carThread.isAlive())
                    break;
            }
        }).start();
    }
}

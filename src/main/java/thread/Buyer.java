package thread;

import avto.CarImp;

import java.util.List;

public class Buyer extends Thread {
    private int number;
    private int time;
    private final List<CarImp> list;
    private Thread thread;


    public Buyer(int number, int time, List<CarImp> list, Thread thread) {
        this.number = number;
        this.time = time;
        this.list = list;
        this.thread = thread;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void run() {

        do {
            synchronized (list) {
                System.out.println("Покупатель " + getNumber() + " зашёл в магазин");
                if (list.isEmpty()) {
                    System.out.println("Машин нет");
                } else {
                    System.out.printf("Покупатель %d купил %s.\n",
                            getNumber(),
                            list.remove(0));
                }
                list.notify();
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (thread.isAlive());
    }
}

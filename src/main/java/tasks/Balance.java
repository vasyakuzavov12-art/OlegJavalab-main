package tasks;

public class Balance {
    private double left = 0;
    private double right = 0;

    public void addLeft(double weight) {
        left += weight;
    }

    public void addRight(double weight) {
        right += weight;
    }

    public void result() {
        if (left > right) {
            System.out.println("Левая чаша тяжелее");
        } else if (right > left) {
            System.out.println("Правая чаша тяжелее");
        } else {
            System.out.println("Весы в равновесии");
        }
    }
}
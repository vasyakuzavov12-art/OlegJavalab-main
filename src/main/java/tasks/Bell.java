package tasks;

public class Bell {
    private boolean ding = true;

    public void sound() {
        if (ding) {
            System.out.println("ding");
        } else {
            System.out.println("dong");
        }
        ding = !ding;
    }
}
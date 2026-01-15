package tasks;

public class MyButton {
    private int clickCount = 0;

    public void click() {
        clickCount++;
        System.out.println("Клик! Всего кликов: " + clickCount);
    }

    public int getClickCount() {
        return clickCount;
    }
}
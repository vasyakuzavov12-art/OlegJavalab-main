package geometry3d;

import geometry2d.Circle;
import geometry2d.Rectangle;
import geometry3d.exceptions.*;

public class Cylinder {
    private Object base;
    private double height;

    public Cylinder(Circle base, double height) throws NegativeValueException {
        if (height < 0) {
            throw new NegativeValueException("Высота цилиндра не может быть отрицательной: " + height);
        }
        this.base = base;
        this.height = height;
    }

    public Cylinder(Rectangle base, double height) throws NegativeValueException {
        if (height < 0) {
            throw new NegativeValueException("Высота цилиндра не может быть отрицательной: " + height);
        }
        this.base = base;
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public double volume() {
        if (base instanceof Circle) {
            Circle circle = (Circle) base;
            return Math.PI * circle.getRadius() * circle.getRadius() * height;
        } else if (base instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) base;
            return rectangle.getWidth() * rectangle.getHeight() * height;
        }
        return 0;
    }

    @Override
    public String toString() {
        if (base instanceof Circle) {
            Circle circle = (Circle) base;
            return String.format("Цилиндр[основание: круг r=%.2f, высота=%.2f, объем=%.2f]",
                    circle.getRadius(), height, volume());
        } else {
            Rectangle rectangle = (Rectangle) base;
            return String.format("Цилиндр[основание: прямоугольник %.2fx%.2f, высота=%.2f, объем=%.2f]",
                    rectangle.getWidth(), rectangle.getHeight(), height, volume());
        }
    }
}
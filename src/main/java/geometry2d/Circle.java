package geometry2d;

import geometry2d.exceptions.NegativeValueException;

public class Circle {
    private double radius;

    public Circle(double radius) throws NegativeValueException {
        if (radius < 0) {
            throw new NegativeValueException("Радиус не может быть отрицательным: " + radius);
        }
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("Круг[радиус=%.2f, площадь=%.2f, периметр=%.2f]",
                radius, area(), perimeter());
    }
}
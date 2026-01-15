package geometry2d;

import geometry2d.exceptions.GeometryException;

public class Rectangle {
    private double width;
    private double height;

    public Rectangle(double width, double height) throws GeometryException {
        if (width < 0 || height < 0) {
            throw new GeometryException("Ширина и высота не могут быть отрицательными: " +
                    width + "x" + height);
        }
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return String.format("Прямоугольник[%.2fx%.2f, площадь=%.2f, периметр=%.2f]",
                width, height, area(), perimeter());
    }
}
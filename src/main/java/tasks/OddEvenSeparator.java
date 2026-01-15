package tasks;

import java.util.ArrayList;
import java.util.List;

public class OddEvenSeparator {
    private List<Integer> numbers = new ArrayList<>();
    private List<Integer> evenNumbers = new ArrayList<>();
    private List<Integer> oddNumbers = new ArrayList<>();

    public void addNumber(int number) {
        numbers.add(number);
        if (number % 2 == 0) {
            evenNumbers.add(number);
        } else {
            oddNumbers.add(number);
        }
    }

    public void even() {
        System.out.println(evenNumbers);
    }

    public void odd() {
        System.out.println(oddNumbers);
    }

    public List<Integer> getNumbers() {
        return new ArrayList<>(numbers);
    }

    public List<Integer> getEvenNumbers() {
        return new ArrayList<>(evenNumbers);
    }

    public List<Integer> getOddNumbers() {
        return new ArrayList<>(oddNumbers);
    }
}
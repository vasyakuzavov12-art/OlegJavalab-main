package collections;

import java.util.*;

public class HumanCollectionsDemo {

    public static void demo() {
        System.out.println("Класс Human и коллекции");

        List<Human> humans = new ArrayList<>();
        humans.add(new Human("Иван", 25));
        humans.add(new Human("Мария", 30));
        humans.add(new Human("Алексей", 22));
        humans.add(new Human("Елена", 28));

        System.out.println("Список людей:");
        for (Human human : humans) {
            System.out.println("  " + human);
        }

        // Сортировка по возрасту
        humans.sort(Comparator.comparingInt(Human::getAge));
        System.out.println("\nОтсортировано по возрасту:");
        for (Human human : humans) {
            System.out.println("  " + human);
        }

        // Сортировка по имени
        humans.sort(Comparator.comparing(Human::getName));
        System.out.println("\nОтсортировано по имени:");
        for (Human human : humans) {
            System.out.println("  " + human);
        }
    }

    static class Human {
        private String name;
        private int age;

        public Human(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }

        @Override
        public String toString() {
            return name + " (" + age + " лет)";
        }
    }
}
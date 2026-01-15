package json;

import java.util.*;
import java.util.stream.Collectors;

public class JsonStreamDemo {

    public static void demo() {
        System.out.println("Обработка данных библиотеки (Stream API & JSON)");

        // Имитация данных библиотеки
        List<Book> books = Arrays.asList(
                new Book("Война и мир", "Лев Толстой", 1869, 1225),
                new Book("Преступление и наказание", "Федор Достоевский", 1866, 672),
                new Book("Мастер и Маргарита", "Михаил Булгаков", 1967, 480),
                new Book("1984", "Джордж Оруэлл", 1949, 328),
                new Book("Улисс", "Джеймс Джойс", 1922, 730)
        );

        // Использование Stream API
        System.out.println("\n1. Книги, изданные после 1900 года:");
        books.stream()
                .filter(book -> book.getYear() > 1900)
                .forEach(book -> System.out.println("  " + book));

        System.out.println("\n2. Книги, отсортированные по году издания:");
        books.stream()
                .sorted(Comparator.comparingInt(Book::getYear))
                .forEach(book -> System.out.println("  " + book.getYear() + " - " + book.getTitle()));

        System.out.println("\n3. Среднее количество страниц:");
        double avgPages = books.stream()
                .mapToInt(Book::getPages)
                .average()
                .orElse(0);
        System.out.println("  Среднее: " + String.format("%.1f", avgPages) + " страниц");

        System.out.println("\n4. Книги сгруппированы по автору:");
        Map<String, List<Book>> booksByAuthor = books.stream()
                .collect(Collectors.groupingBy(Book::getAuthor));

        booksByAuthor.forEach((author, authorBooks) -> {
            System.out.println("  " + author + ":");
            authorBooks.forEach(book -> System.out.println("    - " + book.getTitle()));
        });
    }

    static class Book {
        private String title;
        private String author;
        private int year;
        private int pages;

        public Book(String title, String author, int year, int pages) {
            this.title = title;
            this.author = author;
            this.year = year;
            this.pages = pages;
        }

        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public int getYear() { return year; }
        public int getPages() { return pages; }

        @Override
        public String toString() {
            return String.format("%s - %s (%d год, %d стр.)", title, author, year, pages);
        }
    }
}
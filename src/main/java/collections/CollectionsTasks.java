package collections;

import java.util.*;

public class CollectionsTasks {

    public static void demo() {
        System.out.println("Демонстрация Collections API");

        // Пример работы с коллекциями
        List<String> list = new ArrayList<>();
        list.add("Яблоко");
        list.add("Банан");
        list.add("Апельсин");

        System.out.println("Список: " + list);

        Collections.sort(list);
        System.out.println("Отсортированный список: " + list);

        Collections.reverse(list);
        System.out.println("Обратный порядок: " + list);

        Collections.shuffle(list);
        System.out.println("Перемешанный список: " + list);
    }

    public static <K, V> Map<V, K> invertMap(Map<K, V> map) {
        Map<V, K> inverted = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            inverted.put(entry.getValue(), entry.getKey());
        }
        return inverted;
    }
}
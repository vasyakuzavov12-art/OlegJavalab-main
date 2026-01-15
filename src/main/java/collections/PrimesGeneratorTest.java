package collections;

import java.util.*;

public class PrimesGeneratorTest {

    public static void demo() {
        System.out.println("Генератор простых чисел");

        List<Integer> primes = new ArrayList<>();
        int count = 0;
        int number = 2;

        while (count < 20) {
            if (isPrime(number)) {
                primes.add(number);
                count++;
            }
            number++;
        }

        System.out.println("Первые 20 простых чисел: " + primes);
    }

    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
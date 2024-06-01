package ru.basejava;

/*реализовать метод через стрим int minValue(int[] values).
Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число,
составленное из этих уникальных цифр. Не использовать преобразование в строку и обратно.
Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89*/

/*реализовать метод List<Integer> oddOrEven(List<Integer> integers) если сумма всех чисел нечетная - удалить все нечетные,
если четная - удалить все четные. Сложность алгоритма должна быть O(N). Optional - решение в один стрим.*/

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class StreamHomeWork12 {
    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (acc, num) -> acc * 10 + num);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, Integer::sum);
        boolean sumOddOrEven = sum % 2 == 0;
        return integers.stream().filter(num -> (sumOddOrEven && num % 2 != 0) || (!sumOddOrEven && num % 2 == 0)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{7,3,2,5,1,2,5,3,9}));
        System.out.println(oddOrEven(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)));
    }
}

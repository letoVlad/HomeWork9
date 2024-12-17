package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        List<Person> someCollection = Arrays.asList(
                new Person("Vlad", 25),
                new Person("Maks", 18),
                new Person("Alisa", 35),
                new Person("Alex", 33)
        );

        Map<String, Person> result = Streams.of(someCollection)
                .filter(p -> p.getAge() > 20)
                .transform(p -> new Person(p.getName(), p.getAge() + 30))
                .toMap(Person::getName, Function.identity());

        result.forEach((key, value) ->
                System.out.println("Key: " + key + ", Value: " + value));

        System.out.println("Original collection: " + someCollection);
    }
}

package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StreamsTest {

    List<Person> testList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        testList.add(new Person("Vlad", 55));
        testList.add(new Person("Maks", 26));
        testList.add(new Person("Alisa", 34));
        testList.add(new Person("Alex", 45));
    }

    @DisplayName("Проверка метода : Streams.of()")
    @Test
    public void shouldCreateACollectionStream() {
        Streams<Person> personStream = Streams.of(testList);

        assertEquals(testList.size(), personStream.toList().size());
        assertTrue(personStream.toList().containsAll(testList));
    }


    @DisplayName("Проверка метода : filter")
    @Test
    public void shouldReturnFilteredCollectionElements() {
        Streams<Person> personStreams = Streams.of(testList);
        List<Person> personList = personStreams.filter(p -> p.getAge() > 30).toList();

        assertEquals(3, personList.size());
        assertTrue(personList.containsAll(Arrays.asList(
                new Person("Vlad", 55),
                new Person("Alisa", 34),
                new Person("Alex", 45)
        )));
    }

    @DisplayName("Проверка метода : transform")
    @Test
    public void shouldReturnFilteredTransformedCollectionElements() {
        Streams<Person> personStreams = Streams.of(testList);
        List<Person> personList = personStreams.transform(p -> new Person(p.getName(), p.getAge() + 30)).toList();

        assertEquals(4, personList.size());
        assertTrue(personList.containsAll(Arrays.asList(
                new Person("Maks", 56),
                new Person("Vlad", 85),
               new Person("Alex", 75)
        )));
    }

    @DisplayName("Проверка метода : toMap")
    @Test
    public void shouldReturnFilteredMapElements() {
        Streams<Person> personStreams = Streams.of(testList);
        Map<String, Integer> ageMap = personStreams.toMap(Person::getName, Person::getAge);

        assertEquals(testList.size(), ageMap.size());
        assertEquals(ageMap.get("Vlad"), 55);
        assertEquals(ageMap.get("Maks"), 26);
        assertEquals(ageMap.get("Alisa"), 34);
    }

    @DisplayName("Проверка метода : toList")
    @Test
    void shouldMakeListFromGivenCollectionAndReturnIt() {
        Streams<Person> personStreams = Streams.of(testList);
        List<Person> checkList = personStreams.toList();

        assertEquals(testList.size(), checkList.size());
        assertTrue(checkList.containsAll(testList));
    }

    @DisplayName("Проверка, что при использовании Streams исходная коллекция не меняется")
    @Test
    void shouldReturnNewCollectionFromOriginal() {
        Streams<Person> personStreams = Streams.of(testList);
        List<Person> notFiltered = personStreams.toList();
        List<Person> filtered = personStreams.filter(p -> p.getAge() > 30).toList();

        assertEquals(4, testList.size());
        assertEquals(3, filtered.size());
        assertEquals(notFiltered, testList);
    }
}
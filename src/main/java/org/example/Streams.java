package org.example;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {
    private final List<T> source;

    public Streams(List<T> source) {
        this.source = new ArrayList<>(source);
    }

    /**
     * Статический метод для создания экземпляра Streams.
     * @param collection коллекция, из которой создается Streams
     * @param <E> тип элементов коллекции
     * @return новый объект Streams
     */
    public static <E> Streams<E> of(Collection<? extends E> collection) {
        return new Streams<>(new ArrayList<>(collection));
    }

    /**
     * Метод filter позволяет отфильтровать элементы коллекции.
     * @param predicate условие для фильтрации элементов
     * @return новый Streams с отфильтрованными элементами
     */
    public Streams<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        List<T> filtered = new ArrayList<>();
        for (T item : source) {
            if (predicate.test(item)) {
                filtered.add(item);
            }
        }
        return new Streams<>(filtered);
    }

    /**
     * Метод transform преобразует элементы коллекции с помощью функции преобразования (Function).
     * @param mapper функция преобразования элементов
     * @param <R> тип результата после преобразования
     * @return новый Streams с элементами преобразованного типа
     */
    public <R> Streams<R> transform(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        List<R> transformed = new ArrayList<>();
        for (T item : source) {
            transformed.add(mapper.apply(item));
        }
        return new Streams<>(transformed);
    }

    /**
     * Метод toMap преобразует элементы Streams в карту.
     * @param keyMapper функция для создания ключей
     * @param valueMapper функция для создания значений
     * @param <K> тип ключей
     * @param <V> тип значений
     * @return Map, где ключи и значения определены переданными функциями
     */
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends V> valueMapper) {
        Objects.requireNonNull(keyMapper);
        Objects.requireNonNull(valueMapper);
        Map<K, V> map = new HashMap<>();
        for (T item : source) {
            map.put(keyMapper.apply(item), valueMapper.apply(item));
        }
        return map;
    }

    /**
     * Метод toList возвращает текущие элементы Streams в виде нового списка.
     * @return новый список элементов Streams
     */
    public List<T> toList() {
        return new ArrayList<>(source);
    }
}

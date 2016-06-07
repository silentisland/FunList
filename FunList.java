package pl.com.bottega.photostock.sales.fun;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Admin on 04.06.2016.
 */
public interface FunList<T> {
    FunList<T> add(T el);
    FunList<T> remove(T el);
    boolean contains(T el);
    int size();
    T find(Predicate<T> predicate);
    T get(int i);
    boolean isEmpty();

    <R> FunList<R> map(Function<T, R> mapper);

    static <T> FunList<T> create(){
        return new EmptyList<>();
    }

    <R> R reduce(R initial, BiFunction<R, T, R> reductor);

    FunList<T> filter(Predicate<T> predicate); // wszystkie elementy

    void each (Consumer<T> consumer); // iterates over all elements and calls consumer with each element

    FunList<T> concat(FunList<T> other);

    FunList<T> sublist (int startIdnex, int endIndex);
}

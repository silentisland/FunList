package pl.com.bottega.photostock.sales.fun;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Admin on 04.06.2016.
 */
public class EmptyList<T> implements FunList<T> {

    @Override
    public FunList<T> add(T el) {
        return new NonEmptyList<>(el);
    }

    @Override
    public FunList<T> remove(T el) {
        return this;
    }

    @Override
    public boolean contains(T el) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T find(Predicate<T> predicate) {
        return null;
    }

    @Override
    public T get(int i) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public <R> FunList<R> map(Function<T, R> mapper) {
        return new EmptyList<>();
    }

    @Override
    public <R> R reduce(R initial, BiFunction<R, T, R> reductor) {
        return initial;
    }

    @Override
    public FunList<T> filter(Predicate<T> predicate) {
        return this;
    }

    @Override
    public void each(Consumer<T> consumer) {}

    @Override
    public FunList<T> concat(FunList<T> other) {
        return other;
    }

    @Override
    public FunList<T> sublist(int startIdnex, int endIndex) {
        return this;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof FunList && ((FunList) other).isEmpty();
    }
}

package pl.com.bottega.photostock.sales.fun;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Admin on 04.06.2016.
 */
public class NonEmptyList<T> implements FunList<T> {

    private T head;
    private FunList<T> tail;

    public NonEmptyList(T el) {
        head = el;
        tail = new EmptyList<>();
    }

    public NonEmptyList(T head, FunList<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public FunList add(T el) {
        return new NonEmptyList<>(head, tail.add(el));
    }

    @Override
    public boolean contains(T el) {
        return head.equals(el) || tail.contains(el);
    }

    @Override
    public int size() {
        return tail.size() + 1;
    }

    @Override
    public T find(Predicate<T> predicate) {
        if (predicate.test(head))
            return head;
        else
            return tail.find(predicate);
    }

    @Override
    public T get(int i) {
        return i == 0 ? head : tail.get(i - 1);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public <R> FunList<R> map(Function<T, R> mapper) {
        return new NonEmptyList<>(mapper.apply(head), tail.map(mapper));
    }

    @Override
    public <R> R reduce(R initial, BiFunction<R, T, R> reductor) {
        R partialResult = reductor.apply(initial, head);
        return tail.reduce(partialResult, reductor);
    }

    @Override
    public String toString() {
        if (tail.isEmpty())
            return head.toString();
        else
            return head.toString() + ", " + tail.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof NonEmptyList))
            return false;
        NonEmptyList otherList = (NonEmptyList) other;
        return head.equals(otherList.head) && tail.equals(otherList.tail);
    }

    @Override
    public FunList<T> remove(T el) {
        return head.equals(el) ? tail : new NonEmptyList<T>(head, tail.remove(el));
    }

    @Override
    public FunList<T> filter(Predicate<T> predicate) {
        if (predicate.test(head))
            return new NonEmptyList<T>(head, tail.filter(predicate));
        return tail.filter(predicate);
    }

    @Override
    public void each(Consumer<T> consumer) {
        consumer.accept(head);
        tail.each(consumer);
    }

    @Override
    public FunList<T> concat(FunList<T> other) {
        return new NonEmptyList<T>(head, tail.concat(other));
    }

    @Override
    public FunList<T> sublist(int startIdnex, int endIndex) {
        if (startIdnex >= 0 && startIdnex < tail.size() - 1 && endIndex >= startIdnex) {
            return startIdnex == 0 ? new NonEmptyList<T>(head, tail.sublist(0, endIndex - 1)) :
                    tail.sublist(startIdnex - 1, endIndex - 1);
        }
        return new EmptyList<>();
    }
}

package cn.com.qjun.either;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author RenQiang
 * @date 2019-03-30
 */
final class Left<R, L> extends Either<R, L> {
    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public R getRight() {
        throw new IllegalStateException("Either.getRight() cannot be called on a Left value");
    }

    @Override
    public L getLeft() {
        return value;
    }

    @Override
    public <T> T fold(Function<R, T> rightTransformer, Function<L, T> leftTransformer) {
        Objects.requireNonNull(rightTransformer);
        return Objects.requireNonNull(leftTransformer).apply(value);
    }

    @Override
    public <TR, TL> Either<TR, TL> map(Function<R, TR> rightMapper, Function<L, TL> leftMapper) {
        Objects.requireNonNull(rightMapper);
        return ofLeft(Objects.requireNonNull(leftMapper).apply(value));
    }

    @Override
    public void consume(Consumer<R> rightConsumer, Consumer<L> leftConsumer) {
        Objects.requireNonNull(rightConsumer);
        Objects.requireNonNull(leftConsumer).accept(value);
    }

    @Override
    public R orNull() {
        return null;
    }

    @Override
    public R or(R defaultRightValue) {
        return defaultRightValue;
    }

    @Override
    public R or(Supplier<R> rightSupplier) {
        return Objects.requireNonNull(rightSupplier).get();
    }

    @Override
    public Either<R, L> or(Either<R, L> secondChoice) {
        return Objects.requireNonNull(secondChoice);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Left) {
            Left<?, ?> otherLeft = (Left<?, ?>) obj;
            return value.equals(otherLeft.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0xeee + value.hashCode();
    }

    @Override
    public String toString() {
        return "Left value of Either (" + value + ")";
    }

    private static final long serialVersionUID = 5664720718252692940L;

    private final L value;

    Left(L value) {
        this.value = Objects.requireNonNull(value);
    }
}

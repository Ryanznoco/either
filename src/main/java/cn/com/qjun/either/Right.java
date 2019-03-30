package cn.com.qjun.either;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author RenQiang
 * @date 2019-03-30
 */
final class Right<R, L> extends Either<R, L> {
    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public R getRight() {
        return value;
    }

    @Override
    public L getLeft() {
        throw new IllegalStateException("Either.getLeft() cannot be called on a Right value");
    }

    @Override
    public <T> T fold(Function<R, T> rightTransformer, Function<L, T> leftTransformer) {
        Objects.requireNonNull(leftTransformer);
        return Objects.requireNonNull(rightTransformer).apply(value);
    }

    @Override
    public <TR, TL> Either<TR, TL> map(Function<R, TR> rightMapper, Function<L, TL> leftMapper) {
        Objects.requireNonNull(leftMapper);
        return ofRight(Objects.requireNonNull(rightMapper).apply(value));
    }

    @Override
    public void consume(Consumer<R> rightConsumer, Consumer<L> leftConsumer) {
        Objects.requireNonNull(leftConsumer);
        Objects.requireNonNull(rightConsumer).accept(value);
    }

    @Override
    public R orNull() {
        return value;
    }

    @Override
    public R or(R defaultRightValue) {
        return value;
    }

    @Override
    public R or(Supplier<R> rightSupplier) {
        Objects.requireNonNull(rightSupplier);
        return value;
    }

    @Override
    public Either<R, L> or(Either<R, L> secondChoice) {
        Objects.requireNonNull(secondChoice);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Right) {
            Right<?, ?> otherRight = (Right<?, ?>) obj;
            return value.equals(otherRight.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0xccc + value.hashCode();
    }

    @Override
    public String toString() {
        return "Right value of Either (" + value + ")";
    }

    private static final long serialVersionUID = 5664720718252692940L;

    private final R value;

    Right(R value) {
        this.value = Objects.requireNonNull(value);
    }
}

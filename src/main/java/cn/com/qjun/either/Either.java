package cn.com.qjun.either;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @param <R> 正确或有效值
 * @param <L> 错误或无效值
 * @author RenQiang
 * @date 2019-03-30
 */
public abstract class Either<R, L> implements Serializable {
    public static <R, L> Either<R, L> of(R rightValue, L leftValue) {
        return of(() -> rightValue, () -> leftValue);
    }

    public static <R, L> Either<R, L> of(Supplier<R> rightSupplier, Supplier<L> leftSupplier) {
        return Optional.ofNullable(Objects.requireNonNull(rightSupplier).get())
                .map(Either::<R, L>ofRight)
                .orElseGet(() -> Either.ofLeft(Objects.requireNonNull(leftSupplier).get()));
    }

    public static <R, L> Either<R, L> ofRight(R rightValue) {
        return new Right<>(rightValue);
    }

    public static <R, L> Either<R, L> ofLeft(L leftValue) {
        return new Left<>(leftValue);
    }

    public abstract boolean isRight();

    public abstract boolean isLeft();

    public abstract R getRight();

    public abstract L getLeft();

    public abstract <T> T fold(Function<R, T> rightTransformer, Function<L, T> leftTransformer);

    public abstract <TR, TL> Either<TR, TL> map(Function<R, TR> rightMapper, Function<L, TL> leftMapper);

    public abstract void consume(Consumer<R> rightConsumer, Consumer<L> leftConsumer);

    public abstract R orNull();

    public abstract R or(R defaultRightValue);

    public abstract R or(Supplier<R> rightSupplier);

    public abstract Either<R, L> or(Either<R, L> secondChoice);

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    private static final long serialVersionUID = 5664720718252692940L;
}

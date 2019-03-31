package cn.com.qjun.either;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @param <R> 正确或有效值
 * @param <L> 错误或无效值
 * @author RenQiang
 * @date 2019-03-30
 */
public class EitherChain<R, L> implements Serializable {
    public static <R, L> EitherChain<R, L> of(Either<R, L> value) {
        return new EitherChain<>(value);
    }

    public boolean isRight() {
        return valueChain.stream().anyMatch(Either::isRight);
    }

    public boolean isLeft() {
        return valueChain.stream().allMatch(Either::isLeft);
    }

    public boolean isAllRight() {
        return valueChain.stream().allMatch(Either::isRight);
    }

    public <TR, TL> EitherChain<TR, TL> map(Function<R, TR> rightMapper, Function<L, TL> leftMapper) {
        EitherChain<TR, TL> eitherChain = null;
        for (Either<R, L> either : valueChain) {
            if (eitherChain == null) {
                eitherChain = new EitherChain<>(either.map(rightMapper, leftMapper));
            } else {
                eitherChain.addValue(either.map(rightMapper, leftMapper));
            }
        }
        return eitherChain;
    }

    public void consume(Consumer<R> rightConsumer, Consumer<L> leftConsumer) {
        valueChain.forEach(either -> either.consume(rightConsumer, leftConsumer));
    }

    public EitherChain<R, L> or(Either<R, L> secondChoice) {
        return isRight() ? this : this.addValue(secondChoice);
    }

    public EitherChain<R, L> and(Either<R, L> otherValue) {
        return valueChain.stream().anyMatch(Either::isLeft) ? this : this.addValue(otherValue);
    }

    public EitherChain<R, L> add(Either<R, L> newValue) {
        return addValue(newValue);
    }

    public List<R> collectRights() {
        return valueChain.stream().filter(Either::isRight).map(Either::getRight).collect(Collectors.toList());
    }

    public List<L> collectLefts() {
        return valueChain.stream().filter(Either::isLeft).map(Either::getLeft).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EitherChain) {
            EitherChain<?, ?> otherChain = (EitherChain<?, ?>) obj;
            if (valueChain.size() == otherChain.valueChain.size()) {
                for (int i = 0; i < valueChain.size(); i++) {
                    Either<?, ?> either = valueChain.get(i);
                    if (!either.equals(otherChain.valueChain.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 7;
        for (Either<R, L> either :
                valueChain) {
            hashCode = 7 * hashCode + either.hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return "EitherChain [" + valueChain.stream().map(Either::toString).collect(Collectors.joining(", ")) + "]";
    }

    private EitherChain<R, L> addValue(Either<R, L> newValue) {
        valueChain.add(Objects.requireNonNull(newValue));
        return this;
    }

    private static final long serialVersionUID = -5554926961838531873L;

    private final List<Either<R, L>> valueChain = new ArrayList<>();

    private EitherChain(Either<R, L> value) {
        addValue(value);
    }
}

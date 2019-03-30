package cn.com.qjun.either;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author RenQiang
 * @date 2019-03-30
 */
public class EitherTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testStatic() {
        LocalDateTime rightValue = LocalDateTime.now();
        Date leftValue = new Date();

        Either<LocalDateTime, Date> either = Either.of(rightValue, leftValue);
        assertTrue(either.isRight());
        assertFalse(either.isLeft());
        assertTrue(either instanceof Right);

        either = Either.of(null, leftValue);
        assertFalse(either.isRight());
        assertTrue(either.isLeft());
        assertTrue(either instanceof Left);

        either = Either.of(rightValue, null);
        assertTrue(either.isRight());
        assertFalse(either.isLeft());
        assertTrue(either instanceof Right);

        expectedEx.expect(NullPointerException.class);
        either = Either.of(null, null);

        either = Either.of(() -> rightValue, () -> leftValue);
        assertTrue(either.isRight());
        assertFalse(either.isLeft());
        assertTrue(either instanceof Right);

        either = Either.of(() -> null, () -> leftValue);
        assertFalse(either.isRight());
        assertTrue(either.isLeft());
        assertTrue(either instanceof Left);

        either = Either.of(() -> rightValue, () -> null);
        assertTrue(either.isRight());
        assertFalse(either.isLeft());
        assertTrue(either instanceof Right);

        expectedEx.expect(NullPointerException.class);
        either = Either.of(() -> null, () -> null);

        either = Either.ofRight(rightValue);
        assertTrue(either.isRight());
        assertFalse(either.isLeft());
        assertTrue(either instanceof Right);

        either = Either.ofLeft(leftValue);
        assertFalse(either.isRight());
        assertTrue(either.isLeft());
        assertTrue(either instanceof Left);
    }
}

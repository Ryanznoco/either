package cn.com.qjun.either;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author RenQiang
 * @date 2019-03-30
 */
public class EitherChainTest {

    @Test
    public void testStatic() {
        LocalDateTime rightValue = LocalDateTime.now();
        Date leftValue = new Date();

        EitherChain<LocalDateTime, Date> eitherChain = EitherChain.empty();
        assertTrue(eitherChain.isEmpty());
        assertFalse(eitherChain.isLeft());
        assertFalse(eitherChain.isRight());

        eitherChain = EitherChain.of(Either.of(rightValue, leftValue));
        assertFalse(eitherChain.isEmpty());
        assertTrue(eitherChain.isRight());
        assertFalse(eitherChain.isLeft());

        eitherChain = EitherChain.of(Either.of(() -> rightValue, () -> leftValue));
        assertFalse(eitherChain.isEmpty());
        assertTrue(eitherChain.isRight());
        assertFalse(eitherChain.isLeft());

        eitherChain.add(Either.of(() -> rightValue, () -> leftValue));

        System.out.println(eitherChain.toString());
    }
}

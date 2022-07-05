package util;

import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Retry {
    public static void retry(@NonNull RetryCallback callable,
                             int maxRetries,
                             int pauseInSec) {
        retry(callable, Throwable.class, null, maxRetries, pauseInSec);
    }

    public static void retry(@NonNull RetryCallback callable,
                             String errorMessage,
                             int maxRetries,
                             int pauseInSec) {
        retry(callable, Throwable.class, errorMessage, maxRetries, pauseInSec);
    }

    public static void retry(@NonNull RetryCallback callable,
                             String errorMessage,
                             int maxRetries) {
        retry(callable, Throwable.class, errorMessage, maxRetries, 0);
    }

    public static void retry(@NonNull RetryCallback callable,
                             int maxRetries) {
        retry(callable, Throwable.class, null, maxRetries, 0);
    }

    @SneakyThrows
    public static void retry(@NonNull RetryCallback callable,
                             @NonNull Class<? extends Throwable> expectedException,
                             String message,
                             int maxRetries,
                             int pauseInSec
    ) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        int retriesLeft = maxRetries;
        while (retriesLeft >= 0) {
            --retriesLeft;
            try {
                callable.call();
                return;
            } catch (Throwable e) {
                if (!expectedException.isInstance(e)) {
                    log.error("Unexpected error", e);
                    throw new RuntimeException("Unexpected error", e);
                } else {
                    log.warn(String.format("Retry operation failed. Retries left: %d", retriesLeft), e);
                }
                if (retriesLeft <= 0) rethrow(e, message, stopwatch, maxRetries);
                Thread.sleep(pauseInSec * 1000L);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> RuntimeException rethrow(Throwable throwable, String message, Stopwatch stopwatch, int maxRetries) throws T {
        long elapsed = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        String fullMessage = ("Failed after retried " + maxRetries + "time(s) for " +
                String.format("%02d min, %02d sec", TimeUnit.MILLISECONDS.toMinutes(elapsed),
                        TimeUnit.MILLISECONDS.toSeconds(elapsed) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed))));
        if (message != null) {
            fullMessage = message + ". " + fullMessage;
        }

        if (throwable instanceof AssertionError) {
            fullMessage = throwable.getMessage() + ". " + fullMessage;
            throw new AssertionError(fullMessage, throwable);
        } else {
            throwable.addSuppressed(new AssertionError(fullMessage));
            throw (T) throwable;
        }
    }

    @FunctionalInterface
    public interface RetryCallback {
        void call() throws Exception;
    }

}
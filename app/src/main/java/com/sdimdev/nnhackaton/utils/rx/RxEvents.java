package com.sdimdev.nnhackaton.utils.rx;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import io.reactivex.Completable;
import io.reactivex.ObservableTransformer;

/**
 * Use it only in Repository layer
 */
public class RxEvents {
    private final static Relay<String> events = PublishRelay.create();

    public final static io.reactivex.Observable<String> observe(@NotNull String... keys) {
        return events.compose(predicate(keys));
    }

    public static ObservableTransformer<String, String> predicate(@NotNull String... keys) {
        return upstream -> upstream.filter(any -> Arrays.asList(keys).contains(any));
    }

    public static Completable event(String... keys) {
        return Completable.fromAction(() -> {
            if (keys != null) {
                for (String key : keys)
                    events.accept(key);
            }
        });
    }
}

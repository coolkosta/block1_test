package com.coolkosta.simbirsofttestapp.rxtraining.rx;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 20.11.18
 */
public class RxMaybeTraining {

    /* Тренировочные методы */

    /**
     * Эммит только 1 положительного элемента либо пустая последовательность
     *
     * @param value любое произвольное число
     * @return {@code Maybe} который эммитит значение {@code value} если оно положительное,
     * либо не эммитит ничего, если {@code value} отрицательное
     */
    public Maybe<Integer> positiveOrEmpty(Integer value) {
        return Maybe.create(emmiter -> {
            if (value > 0) {
                emmiter.onSuccess(value);
            } else emmiter.onComplete();
        });
    }

    /**
     * Эммит только 1 положительного элемента либо пустая последовательность
     *
     * @param valueSingle {@link Single} который эммитит любое произвольное число
     * @return {@code Maybe} который эммитит значение из {@code valueSingle} если оно эммитит
     * положительное число, иначе не эммитит ничего
     */
    public Maybe<Integer> positiveOrEmptySingle(Single<Integer> valueSingle) {
        return valueSingle.flatMapMaybe(value -> {
            if (value >= 0) {
                return Maybe.just(value);
            } else {
                return Maybe.empty();
            }
        });
    }

    /**
     * Сумма всех элементов последовательности
     *
     * @param integerObservable {@link Observable} произвольная последовательность чисел
     * @return {@link Maybe} который эммитит сумму всех элементов, либо не эммитит ничего если
     * последовательность пустая
     */
    public Maybe<Integer> calculateSumOfValues(Observable<Integer> integerObservable) {
        return integerObservable.reduce(Integer::sum);
    }

    /**
     * Если {@code integerMaybe} не эммитит элемент, то возвращать {@code defaultValue}
     *
     * @param defaultValue произвольное число
     * @return {@link Single} который эммитит значение из {@code integerMaybe}, либо
     * {@code defaultValue} если последовательность пустая
     */
    public Single<Integer> leastOneElement(Maybe<Integer> integerMaybe, int defaultValue) {
        return integerMaybe.toSingle().onErrorReturnItem(defaultValue);
    }

}

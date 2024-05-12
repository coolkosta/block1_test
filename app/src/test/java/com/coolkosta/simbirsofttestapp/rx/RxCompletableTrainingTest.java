package com.coolkosta.simbirsofttestapp.rx;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import com.coolkosta.simbirsofttestapp.rxtraining.exceptions.ExpectedException;
import com.coolkosta.simbirsofttestapp.rxtraining.rx.RxCompletableTraining;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 20.11.18
 */
public class RxCompletableTrainingTest {

    private RxCompletableTraining mRxCompletableTraining = Mockito.spy(new RxCompletableTraining());
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        reset(mRxCompletableTraining);
        mTestScheduler = new TestScheduler();
        RxJavaPlugins.setComputationSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) {
                return mTestScheduler;
            }
        });
    }

    @Test
    public void callFunction() {
        TestObserver<Void> testObserver = mRxCompletableTraining
                .callFunction()
                .test();

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        verify(mRxCompletableTraining).callFunction();
    }

    @Test
    public void completeWhenTrue_true() {

        TestObserver<Void> testObserver = mRxCompletableTraining
                .completeWhenTrue(Single.just(true))
                .test();

        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void completeWhenTrue_false() {

        TestObserver<Void> testObserver = mRxCompletableTraining
                .completeWhenTrue(Single.just(false))
                .test();

        testObserver.assertNotComplete();
        testObserver.assertError(ExpectedException.class);
    }
}
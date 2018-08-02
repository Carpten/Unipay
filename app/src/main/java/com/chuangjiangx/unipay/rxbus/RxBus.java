package com.chuangjiangx.unipay.rxbus;

import android.util.Log;

import com.chuangjiangx.unipay.model.rxbus.RxBean;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {

    public static final int EVENT_SUCCESS_CLOSE = 1;


//    private static final Subject<Pair<Integer, Object>, Pair<Integer, Object>> RX_BUS
//            = new SerializedSubject<>(PublishSubject.<Pair<Integer, Object>>create());

    private static final Subject<Object> RX_BUS = PublishSubject.create().toSerialized();

    //
    public static void send(int id, Object data) {
        RX_BUS.onNext(new RxBean(id, data));
    }

    //
    public static Flowable<Object> getSuccessClose() {
        return RX_BUS.toFlowable(BackpressureStrategy.BUFFER)
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        RxBean rxBean = (RxBean) o;
                        return rxBean.getId() == EVENT_SUCCESS_CLOSE;
                    }
                }).flatMap(new Function<Object, Publisher<Object>>() {
                    @Override
                    public Publisher<Object> apply(Object o) throws Exception {
                        return Flowable.just(new Object());
                    }
                });
    }

}

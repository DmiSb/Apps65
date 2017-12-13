package test.dmisb.apps65.data.network.core;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import retrofit2.Response;

public class RestCallTransformer<R> implements ObservableTransformer<Response<R>, R> {
    @Override
    public ObservableSource<R> apply(Observable<Response<R>> upstream) {
        return NetworkStatusChecker.isInternetAvailable()
                .flatMap(aBoolean -> aBoolean ? upstream : Observable.error(new NetworkAvailableError()))
                .flatMap(rResponse -> {
                    switch (rResponse.code()) {
                        case 200:
                            return Observable.just(rResponse.body());
                        default:
                            return Observable.error(new Throwable(rResponse.errorBody().string()));
                    }
                });
    }
}

package test.dmisb.apps65.data.network;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import test.dmisb.apps65.data.network.res.BaseRes;

public interface RestService {

    @GET("65gb/static/raw/master/testTask.json")
    Observable<Response<BaseRes>> getUsers();
}

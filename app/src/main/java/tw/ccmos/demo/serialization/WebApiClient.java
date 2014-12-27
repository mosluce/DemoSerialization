package tw.ccmos.demo.serialization;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by mosluce on 14/12/26.
 */
public class WebApiClient<TData> extends AsyncHttpClient {
    private final String ENDPOINT_URL = "YOUR WEB API ENDPOINT URL";
    private String mApiUri;
    private Class<TData> mResultCls;

    public WebApiClient(String path, Class<TData> resultCls) {
        super();

        mApiUri = ENDPOINT_URL + path;
        mResultCls = resultCls;
    }

    public void post(RequestParams params, final IWebApiResponseHandler<TData> responseHandler) {
        post(mApiUri, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                WebApiClientResponse<TData> responseObj = new WebApiClientResponse<TData>();

                try {
                    responseObj.success = response.getBoolean("success");

                    if (!responseObj.success) {
                        responseObj.message = response.getString("message");
                    } else {
                        responseObj.data = new GsonBuilder().create().fromJson(response.getString("data"), mResultCls);
                    }

                    responseHandler.success(responseObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                responseHandler.failure(statusCode, throwable.getMessage());
            }

            @Override
            public void onFinish() {
                Log.d("TEST", "FINISH");
            }
        });
    }

    public interface IWebApiResponseHandler<TData> {
        public void success(WebApiClientResponse<TData> response);
        public void failure(int statusCode, String errorMessage);
    }
}

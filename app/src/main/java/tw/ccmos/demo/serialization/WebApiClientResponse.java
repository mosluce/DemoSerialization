package tw.ccmos.demo.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by mosluce on 14/12/26.
 */
public class WebApiClientResponse<TData> {
    public boolean success;
    public String message;
    public TData data;
}

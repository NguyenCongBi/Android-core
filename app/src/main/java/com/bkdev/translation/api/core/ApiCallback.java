package com.bkdev.translation.api.core;

import android.text.TextUtils;

import com.bkdev.translation.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Api Callback.
 *
 * @param <T> object call back
 * @author BiNC
 */

public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void success(T t);

    public abstract void failure(ApiError apiError);

    public ApiCallback() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            success(response.body());
        } else {
            try {
                ApiError errorMessage = getErrorBodyAs(ApiError.class, response);
                if (errorMessage != null && !TextUtils.isEmpty(errorMessage.getMessage())) {
                    failure(new ApiError(errorMessage.getCode(), errorMessage.getMessage()));
                } else {
                    failure(new ApiError(HttpURLConnection.HTTP_CLIENT_TIMEOUT,
                            ApiClient.getInstance().getContext().getString(R.string.valid_error_retrofit_network_error)));
                }
            } catch (IOException e) {
                failure(new ApiError(HttpURLConnection.HTTP_CLIENT_TIMEOUT,
                        ApiClient.getInstance().getContext().getString(R.string.valid_error_retrofit_network_error)));
            }
        }
    }

    private <S> S getErrorBodyAs(Class<S> type, Response<?> response) throws IOException {
        if (response == null || response.errorBody() == null) {
            return null;
        }
        Converter<ResponseBody, S> converter = ApiClient.getInstance().getRetrofit().responseBodyConverter(type, new Annotation[0]);
        return converter.convert(response.errorBody());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        failure(new ApiError(HttpURLConnection.HTTP_CLIENT_TIMEOUT,
                ApiClient.getInstance().getContext().getString(R.string.valid_error_retrofit_network_error)));
    }
}

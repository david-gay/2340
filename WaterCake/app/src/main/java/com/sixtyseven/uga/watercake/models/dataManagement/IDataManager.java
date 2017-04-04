package com.sixtyseven.uga.watercake.models.dataManagement;

import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.user.User;

import java.lang.reflect.Type;

public interface IDataManager {
    <T> void getAllWaterSourceReports(final Callback<T> callback, Type type);

    <T> void getAllWaterPurityReports(final Callback<T> callback, Type type);

    void registerUser(User user, final UserSession.RegisterCallback registerCallback);

    void getUser(final String username, final UserSession.UserCallback userCallback);

    interface Callback<T> {
        void onSuccess(T response);

        void onFailure(String errorMessage);
    }
}

package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.user.UserProfileError;
import com.sixtyseven.uga.watercake.models.user.UserProfileField;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class UserPropertiesFragment extends Fragment {
    TextInputLayout passwordInput;
    TextInputLayout repeatPasswordInput;
    TextInputLayout emailInput;
    TextInputLayout cityInput;
    TextInputLayout titleInput;

    /**
     * Factory method for getting new UserPropertiesFragment. Required for Android Fragments
     * @return a new UserPropertiesFragment
     */
    public static UserPropertiesFragment newInstance() {
        return new UserPropertiesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_properties, container, false);
        passwordInput = (TextInputLayout) view.findViewById(R.id
                .passwordInputLayout);
        repeatPasswordInput = (TextInputLayout) view.findViewById(R.id
                .repeatPasswordInputLayout);
        emailInput = (TextInputLayout) view.findViewById(R.id
                .emailInputLayout);
        cityInput = (TextInputLayout) view.findViewById(R.id
                .cityInputLayout);
        titleInput = (TextInputLayout) view.findViewById(R.id
                .titleInputLayout);

        return view;
    }

    /**
     * Sets the text for a given UserProfileField. Does nothing if that field is not part of this
     * fragment.
     * @param field the target UserProfileField
     * @param text the new text to set
     */
    public void setTextFieldContents(UserProfileField field, String text) {
        if (field == UserProfileField.PASSWORD) {
            passwordInput.getEditText().setText(text);
        } else if (field == UserProfileField.REPEAT_PASSWORD) {
            repeatPasswordInput.getEditText().setText(text);
        } else if (field == UserProfileField.EMAIL) {
            emailInput.getEditText().setText(text);
        } else if (field == UserProfileField.CITY) {
            cityInput.getEditText().setText(text);
        } else if (field == UserProfileField.TITLE) {
            titleInput.getEditText().setText(text);
        }
    }

    /**
     * Gets the current text in the corresponding UserProfileField
     * @param field the field to get
     * @return the contents corresponding to the UserProfileField, as a string. Null if that field
     * is not present.
     */
    public String getTextFieldContents(UserProfileField field) {
        if (field == UserProfileField.PASSWORD) {
            return passwordInput.getEditText().getText().toString();
        } else if (field == UserProfileField.REPEAT_PASSWORD) {
            return repeatPasswordInput.getEditText().getText().toString();
        } else if (field == UserProfileField.EMAIL) {
            return emailInput.getEditText().getText().toString();
        } else if (field == UserProfileField.CITY) {
            return cityInput.getEditText().getText().toString();
        } else if (field == UserProfileField.TITLE) {
            return titleInput.getEditText().getText().toString();
        }

        return null;
    }

    /**
     * Sets error messages on each text field with an error. If requestFocus is true, then the first
     * field with an error will get focus.
     * @param errors the set of all validation errors
     * @param requestFocus true if the first error should cause the field to take focus
     * @return true if focus was set within this method
     */
    public boolean setErrors(EnumSet<UserProfileError> errors, boolean requestFocus) {

        boolean focusSet = !requestFocus;

        for (UserProfileError error : errors) {
            setError(error, !focusSet);
            focusSet = true;
        }

        return focusSet && requestFocus;
    }

    /**
     * Sets an error message on the text field associated with the error.
     * @param error the error to set
     * @param shouldFocus true if focus should be set on the field with the error
     */
    public void setError(UserProfileError error, boolean shouldFocus) {
        Log.d("userPropertiesFragment", "setting error: " + error.getMessage());

        TextInputLayout target = null;
        if (error.getField() == UserProfileField.PASSWORD) {
            target = passwordInput;
        } else if (error.getField() == UserProfileField.REPEAT_PASSWORD) {
            target = repeatPasswordInput;
        }

        if (target != null) {
            target.setError(error.getMessage());
            if (shouldFocus) {
                target.getEditText().requestFocus();
            }
        }
    }

    /**
     * Gets the bottom TextInputLayout in this layout.
     * @return the bottom TextInputLayout
     */
    public TextInputLayout getBottomTextInputLayout() {
        ViewGroup layout = (ViewGroup) getView();
        int length = layout.getChildCount();
        TextInputLayout output = null;

        int i = length - 1;
        while (output == null && i > -1) {
            View v = layout.getChildAt(i);
            if (v instanceof TextInputLayout) {
                output = (TextInputLayout) v;
            }
            i--;
        }
        return output;
    }

    /**
     * Returns a map of UserProfileFields to Strings with that field's associated data.
     * @return a map of UserProfileFields to Strings with that field's associated data
     */
    public Map<UserProfileField, String> getFieldMap() {
        Map<UserProfileField, String> map = new HashMap<>();

        for (UserProfileField f : UserProfileField.values()) {
            String fieldValue = getTextFieldContents(f);
            if (fieldValue != null) {
                map.put(f, fieldValue);
            }
        }

        return map;
    }
}

package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.report.SourceReportError;
import com.sixtyseven.uga.watercake.models.report.SourceReportField;
import com.sixtyseven.uga.watercake.models.user.UserProfileError;
import com.sixtyseven.uga.watercake.models.user.UserProfileField;
import com.sixtyseven.uga.watercake.models.report.Location;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class ReportPropertiesFragment extends Fragment {
    TextInputLayout latitudeInput;
    TextInputLayout longitudeInput;
    Spinner waterTypeInput;
    Spinner waterConditionInput;
    Location location;

    /**
     * Factory method for getting new ReportPropertiesFragment. Required for Android Fragments
     * @return a new ReportPropertiesFragment
     */
    public static ReportPropertiesFragment newInstance() {
        return new ReportPropertiesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_water_source_report, container,
                false);
        latitudeInput = (TextInputLayout) view.findViewById(R.id.latitudeInput);
        longitudeInput = (TextInputLayout) view.findViewById(R.id.longitudeInput);
        waterTypeInput = (Spinner) view.findViewById(R.id.waterTypeSpinner);
        waterConditionInput = (Spinner) view.findViewById(R.id.waterConditionSpinner);

  /*      if (latitudeInput != null && longitudeInput != null) {
            double latitude = Double.parseDouble(latitudeInput.getEditText().toString());
            double longitude = Double.parseDouble(longitudeInput.getEditText().toString());
            location = new Location(latitude, longitude);
        }
*/

        return view;
    }

    /**
     * Sets the text for a given SourceReportField. Does nothing if that field is not part of this
     * fragment.
     * @param field the target SourceReportField
     * @param text the new text to set
     */
    public void setTextFieldContents(SourceReportField field, String text) {
        if (field == SourceReportField.LATITUDE) {
            latitudeInput.getEditText().setText(text);
        } else if (field == SourceReportField.LONGITUDE) {
            longitudeInput.getEditText().setText(text);
        }
    }

    /**
     * Gets the current text in the corresponding UserProfileField
     * @param field the field to get
     * @return the contents corresponding to the UserProfileField, as a string. Null if that field
     * is not present.
     */
    public String getTextFieldContents(SourceReportField field) {
        if (field == SourceReportField.LATITUDE) {
            return latitudeInput.getEditText().getText().toString();
        } else if (field == SourceReportField.LONGITUDE) {
            return longitudeInput.getEditText().getText().toString();
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
    public boolean setErrors(EnumSet<SourceReportError> errors, boolean requestFocus) {

        boolean focusSet = !requestFocus;

        for (SourceReportError error : errors) {
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
    public void setError(SourceReportError error, boolean shouldFocus) {
        Log.d("ReportProp...Fragment", "setting error: " + error.getMessage());

        TextInputLayout target = null;
        if (error.getField() == SourceReportField.LATITUDE) {
            target = latitudeInput;
        } else if (error.getField() == SourceReportField.LONGITUDE) {
            target = longitudeInput;
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
     * Returns a map of SourceReportFields to Strings with that field's associated data.
     * @return a map of SourceReportFields to Strings with that field's associated data
     */
    public Map<SourceReportField, String> getFieldMap() {
        Map<SourceReportField, String> map = new HashMap<>();

        for (SourceReportField f : SourceReportField.values()) {
            String fieldValue = getTextFieldContents(f);
            if (fieldValue != null) {
                map.put(f, fieldValue);
            }
        }

        return map;
    }
}

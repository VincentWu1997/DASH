package uk.co.dashorg.dash.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.dashorg.dash.AuthUtils;
import uk.co.dashorg.dash.MainActivity;
import uk.co.dashorg.dash.R;

public class AccountFragment extends Fragment {
    private ViewGroup layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setupAccountCreationForm(inflater, container);
        return layout;
    }

    private void setupAccountCreationForm(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_account_form, container, false);
        MainActivity.setTitle(this, "Account creation");

        EditText passwordInput = layout.findViewById(R.id.password_input);
        EditText passwordConfirmInput = layout.findViewById(R.id.password_confirm_input);

        if (!passwordInput.getText().toString().equals(passwordConfirmInput.getText().toString())) {
            throw new IllegalArgumentException();
        }

        layout.findViewById(R.id.create_account_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("JSON", getFormJSON());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getFormJSON() throws JSONException {
        JSONObject formJSON = new JSONObject();
        String salt = AuthUtils.getSaltString(16);
        formJSON.put("forename", ((EditText) layout.findViewById(R.id.forename_input)).getText().toString());
        formJSON.put("surname", ((EditText) layout.findViewById(R.id.surname_input)).getText().toString());
        formJSON.put("email", ((EditText) layout.findViewById(R.id.email_input)).getText().toString());
        formJSON.put("phone", ((EditText) layout.findViewById(R.id.phone_input)).getText().toString());
        formJSON.put("salt", salt);
        formJSON.put("password", AuthUtils.hashPassword(((EditText) layout.findViewById(R.id.phone_input)).getText().toString(), salt));
        return formJSON.toString(4);
    }

    public boolean isLoggedIn() {
        return false;
    }
}

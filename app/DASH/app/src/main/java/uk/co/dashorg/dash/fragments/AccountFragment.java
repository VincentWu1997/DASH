package uk.co.dashorg.dash.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.dashorg.dash.MainActivity;
import uk.co.dashorg.dash.R;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_account, container, false);
        if (loggedIn()) {
            MainActivity.setTitle(this, "Login");
        } else {
            MainActivity.setTitle(this, "Login");
        }
        return layout;
    }

    public boolean loggedIn() {
        return false;
    }
}

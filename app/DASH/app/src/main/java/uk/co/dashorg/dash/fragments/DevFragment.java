package uk.co.dashorg.dash.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.dashorg.dash.MainActivity;
import uk.co.dashorg.dash.R;
import uk.co.dashorg.dash.ReferralFormActivity;
import uk.co.dashorg.dash.RequestCodes;

public class DevFragment extends Fragment {
    private ViewGroup layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_dev, container, false);
        MainActivity.setTitle(this, "Developer");

        layout.findViewById(R.id.open_form_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent referralFormActivityIntent = new Intent(getContext(), ReferralFormActivity.class);
                startActivityForResult(referralFormActivityIntent, RequestCodes.DEV_FRAGMENT_FORM_REQUEST);
            }
        });

        return layout;
    }
}
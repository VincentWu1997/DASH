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

public class FeedbackFragment extends Fragment {
    private ViewGroup layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_feedback, container, false);
        MainActivity.setTitle(this, "Feedback");

        return layout;
    }
}

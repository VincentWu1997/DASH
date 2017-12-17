package uk.co.dashorg.dash.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.dashorg.dash.MainActivity;
import uk.co.dashorg.dash.R;
import uk.co.dashorg.dash.RequestCodes;

public class InfoFragment extends Fragment {
    private enum SocialMediaAction {
        FACEBOOK  (R.id.fab_facebook,  "fb://facewebmodal/f?href=https://www.facebook.com/DASHorganisation"),
        INSTAGRAM (R.id.fab_instagram, "https://www.instagram.com/_u/dashorg/"),
        TWITTER   (R.id.fab_twitter,   "https://twitter.com/DASHorg"),
        YOUTUBE   (R.id.fab_youtube,   "https://www.youtube.com/channel/UCYumHQ37FiwPiSl3DWKdSEw");

        public int id;
        public String pkg;
        public String url;

        SocialMediaAction(int id, String url) {
            this.id = id;
            this.pkg = pkg;
            this.url = url;
        }
    }
    private ViewGroup layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_info, container, false);
        MainActivity.setTitle(this, "Useful info");

        for (SocialMediaAction socialMediaAction : SocialMediaAction.values()) {
            final SocialMediaAction action = socialMediaAction;
            layout.findViewById(action.id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(action.url));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(action.url)));
                    }
                }
            });
        }

        layout.findViewById(R.id.fab_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).updateFragment(R.id.nav_contact);
            }
        });

        layout.findViewById(R.id.fab_directions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=Durham+Action+On+Single+Housing,+Hudson+House,+17+Gort+Place");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        layout.findViewById(R.id.fab_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, RequestCodes.INFO_FRAGMENT_CALL_PHONE_REQUEST);
                } else {
                    dial();
                }
            }
        });

        return layout;
    }

    public void dial() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 0191 3845073"));
        startActivity(intent);
    }
}

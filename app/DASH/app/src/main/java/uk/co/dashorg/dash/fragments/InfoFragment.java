package uk.co.dashorg.dash.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uk.co.dashorg.dash.MainActivity;
import uk.co.dashorg.dash.R;

public class LinksFragment extends Fragment {
    private enum SocialMediaAction {
        FACEBOOK  (R.id.fab_facebook,  "com.facebook.katana",  "fb://facewebmodal/f?href=https://www.facebook.com/DASHorganisation"),
        INSTAGRAM (R.id.fab_instagram, "com.instagram.android", "https://www.instagram.com/_u/dashorg/"),
        TWITTER   (R.id.fab_twitter,   "com.twitter.android",   "https://twitter.com/DASHorg"),
        YOUTUBE   (R.id.fab_youtube,  "com.youtube.android",   "https://www.youtube.com/channel/UCYumHQ37FiwPiSl3DWKdSEw");

        public int id;
        public String pkg;
        public String url;

        SocialMediaAction(int id, String pkg, String url) {
            this.id = id;
            this.pkg = pkg;
            this.url = url;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_links, container, false);
        MainActivity.setTitle(this, "Useful links");

        for (SocialMediaAction socialMediaAction : SocialMediaAction.values()) {
            final SocialMediaAction action = socialMediaAction;
            layout.findViewById(action.id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(action.url));
//                    intent.setPackage(action.pkg);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(action.url)));
                    }
                }
            });
        }

        WebView webView = layout.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.dashorg.co.uk/");

        return layout;
    }
}

package uk.co.dashorg.dash.fragments;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import uk.co.dashorg.dash.MainActivity;
import uk.co.dashorg.dash.R;
import uk.co.dashorg.dash.ReferralFormActivity;
import uk.co.dashorg.dash.RequestCodes;

public class TrackFragment extends Fragment {
    private final static int[] indicatorFabIds = new int[] { R.id.account_indicator_fab, R.id.fill_form_indicator_fab, R.id.send_indicator_fab, R.id.review_indicator_fab, R.id.done_indicator_fab};
    private final static int[] indicatorProgressIds = new int[] { R.id.account_indicator_progress, R.id.fill_form_indicator_progress, R.id.send_indicator_progress, R.id.review_indicator_progress, R.id.done_indicator_progress };
    private final static int[] indicatorTextViewIds = new int[] { R.id.account_indicator_text, R.id.fill_form_indicator_text, R.id.send_indicator_text, R.id.review_indicator_text, R.id.done_indicator_text};
    private final static int[] titleIds = new int[] { R.id.account_title, R.id.fill_form_title, R.id.send_title, R.id.review_title, R.id.done_title};
    private final static int[] contentsIds = new int[] { R.id.account_contents, R.id.fill_form_contents, R.id.send_contents, R.id.review_contents, R.id.done_contents};
    private final static int[] separatorIds = new int[] { R.id.separator_1_2, R.id.separator_2_3, R.id.separator_3_4, R.id.separator_4_5};

    private final static int NUM_STAGES = 5;
    private final static int NUM_SEPARATORS = NUM_STAGES - 1;
    private final static int ACCOUNT_STAGE = 0;
    private final static int FORM_STAGE = 1;
    private final static int SEND_STAGE = 2;
    private final static int REVIEW_STAGE = 3;
    private final static int DONE_STAGE = 4;

    private int openStageIndex = ACCOUNT_STAGE;
    private ViewGroup layout;

    private FABProgressCircle[] indicatorProgressCircles = new FABProgressCircle[NUM_STAGES];
    private TextView[] indicatorTextViews = new TextView[NUM_STAGES];
    private View[] contentGroups = new View[NUM_STAGES];
    private View[] separators = new View[NUM_SEPARATORS];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_track, container, false);
        MainActivity.setTitle(this, "Track application");

        for (int i = 0; i < NUM_STAGES; i++) {
            indicatorProgressCircles[i] = layout.findViewById(indicatorProgressIds[i]);
            indicatorTextViews[i] = layout.findViewById(indicatorTextViewIds[i]);
            contentGroups[i] = layout.findViewById(contentsIds[i]);
        }
        for (int i = 0; i < NUM_SEPARATORS; i++) {
            separators[i] = layout.findViewById(separatorIds[i]);
        }

        layout.findViewById(R.id.create_account_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity activity = (MainActivity) getActivity();
//                if (activity != null) {
//                    activity.updateFragment(R.id.nav_account);
//                }
                Toast.makeText(TrackFragment.this.getContext(), "Open account creation page", Toast.LENGTH_SHORT).show();
                layout.findViewById(R.id.create_account_button).setVisibility(View.INVISIBLE);
                layout.findViewById(R.id.account_status_message).setVisibility(View.VISIBLE);
                indicatorProgressCircles[ACCOUNT_STAGE].show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try { Thread.sleep(2500); } catch (InterruptedException e) { e.printStackTrace(); }
                        TrackFragment.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onAccountCreated();
                            }
                        });
                    }
                }).start();
            }
        });

        layout.findViewById(R.id.open_form_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent referralFormActivityIntent = new Intent(getContext(), ReferralFormActivity.class);
//                startActivityForResult(referralFormActivityIntent, RequestCodes.TRACK_FRAGMENT_FORM_REQUEST);
                Toast.makeText(TrackFragment.this.getContext(), "Open form activity", Toast.LENGTH_SHORT).show();
                layout.findViewById(R.id.open_form_button).setVisibility(View.INVISIBLE);
                layout.findViewById(R.id.form_status_message).setVisibility(View.VISIBLE);
                indicatorProgressCircles[FORM_STAGE].show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try { Thread.sleep(2500); } catch (InterruptedException e) { e.printStackTrace(); }
                        TrackFragment.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onFormExited();
                            }
                        });
                    }
                }).start();
            }
        });

        layout.findViewById(R.id.send_form_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionDrawable crossfader = new TransitionDrawable(new Drawable[] {
                        getResources().getDrawable(R.drawable.track_edit),
                        getResources().getDrawable(R.drawable.track_done)
                });
                layout.findViewById(R.id.fill_form_indicator_fab).setBackground(crossfader);
                crossfader.startTransition(200);

//                layout.findViewById(R.id.fill_form_indicator).setBackgroundResource(R.drawable.track_done);
                layout.findViewById(R.id.send_form_button).setVisibility(View.INVISIBLE);
                layout.findViewById(R.id.send_status_message).setVisibility(View.VISIBLE);
                ((FABProgressCircle) layout.findViewById(R.id.send_indicator_progress)).show();
                new Thread(new SendFormProcess()).start();
            }
        });

        layout.findViewById(R.id.review_contact_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TrackFragment.this.getContext(), "Open contact page", Toast.LENGTH_SHORT).show();
            }
        });

        setupBehaviour();

        return layout;
    }

    public void onFormExited() {
        smoothOpenNext();
    }

    public void onAccountCreated() {
        smoothOpenNext();
    }

    private void smoothOpenNext() {
        final int index = openStageIndex;
        indicatorProgressCircles[openStageIndex].attachListener(new FABProgressListener() {
            @Override
            public void onFABProgressAnimationEnd() {
                if (TrackFragment.this.isVisible()) {
                    openStage(openStageIndex + 1, false);
                    indicatorProgressCircles[index].attachListener(null);
                }
            }
        });
        Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(2500);
        out.setFillAfter(true);
        indicatorTextViews[openStageIndex].startAnimation(out);
        indicatorProgressCircles[openStageIndex].beginFinalAnimation();
    }

    private void openStage(final int stage, boolean animationComplete) {
        if (openStageIndex != stage) {

            for (int j = 0; j < NUM_SEPARATORS; j++) {
                int background = R.color.track_separator_todo;
                if (j < stage - 1 || (j == NUM_SEPARATORS - 1 && j == stage - 1)) {
                    background = R.color.track_separator_done;
                } else if (j == stage - 1) {
                    background = R.drawable.track_seperator_pending;
                }
                separators[j].setBackgroundResource(background);
            }

            indicatorTextViews[openStageIndex].setText("");
            if (animationComplete) {
                indicatorProgressCircles[openStageIndex].onArcAnimationComplete();
            }
            if (stage == REVIEW_STAGE) {
                indicatorProgressCircles[REVIEW_STAGE].show();
            } else if (stage == DONE_STAGE) {
                indicatorTextViews[DONE_STAGE].setText("");
                indicatorProgressCircles[DONE_STAGE].onArcAnimationComplete();
            }

            contentGroups[openStageIndex].setVisibility(View.GONE);
            contentGroups[stage].setVisibility(View.VISIBLE);
            openStageIndex = stage;
        }
    }

    private void setupBehaviour() {
        ViewGroup constraint = layout.findViewById(R.id.track_constraint_main);
        LayoutTransition layoutTransition = constraint.getLayoutTransition();
        layoutTransition.setAnimateParentHierarchy(false);
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        layoutTransition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);

        for (int i = 0; i < NUM_STAGES; i++) {
            final int index = i;
            OnClickListener listener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    openStage(index, true);
                }
            };
            layout.findViewById(titleIds[index]).setOnClickListener(listener);
            layout.findViewById(indicatorFabIds[index]).setOnClickListener(listener);
        }
    }

    private class SendFormProcess implements Runnable {
        private final static int MESSAGE_UPDATE_INTERVAL = 500;
        private String message = "Connecting to DASH";
        private int dots = 3;
        private ScheduledExecutorService executorService;

        @Override
        public void run() {
            connectToDASH();
            sendForm();
            verify();
            TrackFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    smoothOpenNext();
                }
            });
            executorService.shutdown();
        }

        private void updateMessage() {
            dots = (dots + 1) % 4;
            final StringBuilder msg = new StringBuilder(message);
            for (int i = 0; i < dots; i++) {
                msg.append('.');
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView) layout.findViewById(R.id.send_status_message)).setText(msg.toString());
                }
            });
        }

        private void connectToDASH() {
            setMessage("Connecting to DASH");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void sendForm() {
            setMessage("Sending form");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void verify() {
            setMessage("Verifying");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setMessage("Done!");
        }

        private void setMessage(String message) {
            this.message = message;
            this.dots = 0;
        }

        SendFormProcess() {
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    updateMessage();
                }
            }, 0, MESSAGE_UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
        }
    }
}

/*
            *

            for (int j = 0; j < NUM_STAGES; j++) {
//                if (j == FORM_STAGE && stage == SEND_STAGE) {
//                    ((TextView) layout.findViewById(indicatorTextIds[j])).setText("");
//                    layout.findViewById(indicatorFabIds[j]).setBackgroundResource(R.drawable.track_edit);
//                } else
                if (j < stage || (j == DONE_STAGE && j == stage)) {
                    if (j <= openStageIndex) {
                        ((TextView) layout.findViewById(indicatorTextIds[j])).setText("");
//                    layout.findViewById(indicatorFabIds[j]).setBackgroundResource(R.drawable.track_done);
                        indicatorProgressCircles[j].onArcAnimationComplete();
                    }

                } else {
                    ((TextView) layout.findViewById(indicatorTextIds[j])).setText(String.valueOf(j + 1));
//                    layout.findViewById(indicatorFabIds[j]).setBackgroundResource(R.drawable.circle_light);
                    indicatorProgressCircles[j].hide();
                }
            }
            * */

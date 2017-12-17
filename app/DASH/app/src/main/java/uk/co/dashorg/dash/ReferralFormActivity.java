package uk.co.dashorg.dash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

public class ReferralFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_referral_form);
    }

    public String getFormAsJSON() {
        JSONObject form = new JSONObject();


        try {
            return form.toString(4);
        } catch (JSONException e) {
            return form.toString();
        }
    }

    public void attachFormToResult() {
        Intent result = new Intent();
        result.putExtra("form", getFormAsJSON());
        setResult(RESULT_OK, result);
    }

    public boolean formChanged() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (formChanged()) {

        } else {
            super.onBackPressed();
        }
    }
}

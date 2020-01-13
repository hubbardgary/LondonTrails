package com.hubbardgary.londontrails.view;

import android.os.Bundle;
import android.app.Activity;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.presenter.AboutPresenter;
import com.hubbardgary.londontrails.proxy.AndroidFrameworkProxy;
import com.hubbardgary.londontrails.view.interfaces.IAboutView;

public class AboutActivity extends Activity implements IAboutView {

    private AboutPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        presenter = new AboutPresenter(this, new AndroidFrameworkProxy());
        presenter.initializeView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            presenter.menuItemSelected(item.getItemId());
            return true;
        }
        catch(Exception e) {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void setDisplayText(Spanned text) {
        TextView htmlTextView = findViewById(R.id.aboutTextView);
        htmlTextView.setText(text);
        htmlTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

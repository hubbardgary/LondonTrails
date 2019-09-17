package com.hubbardgary.londontrails.presenter;

import android.text.Spanned;

import com.hubbardgary.londontrails.BuildConfig;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.proxy.interfaces.IAndroidFrameworkProxy;
import com.hubbardgary.londontrails.view.GoogleMapsLicenceActivity;
import com.hubbardgary.londontrails.view.interfaces.IAboutView;

import java.util.HashMap;

public class AboutPresenter {

    private IAboutView view;
    private IAndroidFrameworkProxy proxy;

    public AboutPresenter(IAboutView view, IAndroidFrameworkProxy proxy) {
        this.view = view;
        this.proxy = proxy;
    }

    public void initializeView() {
        view.setupButtons();
        view.setDisplayText(getAboutText());
    }

    private Spanned getAboutText() {
        final String aboutTextHtml = "<body>" +
                "<h1>Discover a hidden side of London!</h1>" +
                "<h2>Support</h2>" +
                "<p>" +
                "Version " + BuildConfig.VERSION_NAME +
                "</p>" +
                "<p>" +
                "For support and queries, please contact london.trails.app@gmail.com</a>." +
                "</p>" +
                "<h2>Disclaimer</h2>" +
                "<p>" +
                "The information and routes contained in London Trails are provided in good faith. To " +
                "the best of my knowledge, this information is accurate at the time of writing. However, " +
                "things sometimes change, and when following these routes you should always pay attention " +
                "to and follow signs describing temporary or permanent deviations from the routes provided " +
                "within this app." +
                "</p>" +
                "<p>" +
                "No responsibility can be accepted for any issues encountered as a result of " +
                "following these routes." +
                "</p>" +
                "<br />" +
                "<br />" +
                "<br />" +
                "</body>";
        return proxy.fromHtml(aboutTextHtml);
    }

    public void buttonClicked(int id) {
        switch (id) {
            case R.id.btn_legal:
                view.invokeActivity(new HashMap<String, Integer>(), GoogleMapsLicenceActivity.class);
                break;
        }
    }

    public void menuItemSelected(int itemId) {
        switch (itemId) {
            case android.R.id.home:
                view.endActivity();
        }
    }
}

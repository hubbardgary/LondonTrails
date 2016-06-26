package com.hubbardgary.londontrails.presenter;

import android.text.Html;
import android.text.Spanned;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.view.GoogleMapsLicenceActivity;
import com.hubbardgary.londontrails.view.interfaces.IAboutView;

import java.util.HashMap;

public class AboutPresenter {

    private IAboutView view;

    private final String aboutTextHtml =
            "<body>" +
            "<h1>Discover a hidden side of London!</h1>" +
            "<h2>Support</h2>" +
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

    public AboutPresenter(IAboutView view) {
        this.view = view;
    }

    public void initializeView() {
        view.setupButtons();
        view.setDisplayText(getAboutText());
    }

    public Spanned getAboutText() {
        return Html.fromHtml(aboutTextHtml);
    }

    public void buttonClicked(int id) {
        switch (id) {
            case R.id.btn_legal:
                view.invokeActivity(new HashMap<String, Integer>(), GoogleMapsLicenceActivity.class);
                break;
        }
    }
}

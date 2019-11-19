package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.proxy.interfaces.IAndroidFrameworkProxy;
import com.hubbardgary.londontrails.view.interfaces.IGoogleMapsLicenceView;

public class GoogleMapsLicencePresenter {

    private IGoogleMapsLicenceView view;
    private IAndroidFrameworkProxy proxy;

    public GoogleMapsLicencePresenter(IGoogleMapsLicenceView view, IAndroidFrameworkProxy proxy) {
        this.view = view;
        this.proxy = proxy;
    }

    public void initializeView() {
        String licenceInfo = getGoogleLicenseInfo();
        view.setText(licenceInfo);
    }

    private String getGoogleLicenseInfo() {
        return proxy.getGoogleLicenseInfo(view);
    }

    public void menuItemSelected(int itemId) {
        if (itemId == android.R.id.home) {
            view.endActivity();
        }
    }
}

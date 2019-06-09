package com.hubbardgary.londontrails.presenter;

import com.google.android.gms.common.GoogleApiAvailability;
import com.hubbardgary.londontrails.view.interfaces.IGoogleMapsLicenceView;

public class GoogleMapsLicencePresenter {

    private IGoogleMapsLicenceView view;

    public GoogleMapsLicencePresenter(IGoogleMapsLicenceView view) {
        this.view = view;
    }

    public void initializeView() {
        String licenceInfo = getGoogleLicenseInfo();
        view.setText(licenceInfo);
    }

    private String getGoogleLicenseInfo() {
        return GoogleApiAvailability.getInstance().getOpenSourceSoftwareLicenseInfo(view.getContext());
    }

    public void menuItemSelected(int itemId) {
        switch (itemId) {
            case android.R.id.home:
                view.endActivity();
                return;
        }
    }
}

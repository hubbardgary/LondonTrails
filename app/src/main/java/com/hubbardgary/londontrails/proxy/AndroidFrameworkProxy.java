package com.hubbardgary.londontrails.proxy;

import android.text.Html;

import com.google.android.gms.common.GoogleApiAvailability;
import com.hubbardgary.londontrails.proxy.interfaces.IAndroidFrameworkProxy;
import com.hubbardgary.londontrails.view.interfaces.IGoogleMapsLicenceView;

public class AndroidFrameworkProxy implements IAndroidFrameworkProxy {
    @Override
    public CharSequence fromHtml(String text) {
        return Html.fromHtml(text);
    }

    @Override
    public String getGoogleLicenseInfo(IGoogleMapsLicenceView view) {
        return GoogleApiAvailability.getInstance().getOpenSourceSoftwareLicenseInfo(view.getContext());
    }
}

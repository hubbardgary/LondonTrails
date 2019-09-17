package com.hubbardgary.londontrails.proxy.interfaces;

import android.text.Spanned;

import com.hubbardgary.londontrails.view.interfaces.IGoogleMapsLicenceView;

public interface IAndroidFrameworkProxy {
    Spanned fromHtml(String text);
    String getGoogleLicenseInfo(IGoogleMapsLicenceView view);
}

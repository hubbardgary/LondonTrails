package com.hubbardgary.londontrails.proxy.interfaces;

import com.hubbardgary.londontrails.view.interfaces.IGoogleMapsLicenceView;

public interface IAndroidFrameworkProxy {
    CharSequence fromHtml(String text);
    String getGoogleLicenseInfo(IGoogleMapsLicenceView view);
}

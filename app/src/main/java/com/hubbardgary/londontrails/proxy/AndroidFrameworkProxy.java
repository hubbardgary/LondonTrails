package com.hubbardgary.londontrails.proxy;

import android.text.Html;

import com.hubbardgary.londontrails.proxy.interfaces.IAndroidFrameworkProxy;

public class AndroidFrameworkProxy implements IAndroidFrameworkProxy {
    @Override
    public CharSequence fromHtml(String text) {
        return Html.fromHtml(text);
    }
}

package com.hubbardgary.londontrails.view.interfaces;

import android.text.Spanned;
import java.util.HashMap;

public interface IAboutView {
    void endActivity();
    void setupButtons();
    void setDisplayText(Spanned text);
    void invokeActivity(HashMap<String, Integer> intents, Class<?> activity);
}

package com.hubbardgary.londontrails.view.interfaces;

import com.hubbardgary.londontrails.viewmodel.ButtonViewModel;

import java.util.HashMap;
import java.util.List;

public interface IMainView {
    void invokeActivity(HashMap<String, Integer> intents, Class<?> activity);
    void displayButtons(List<ButtonViewModel> buttonsVm);
    void checkGooglePlayAvailability();
}

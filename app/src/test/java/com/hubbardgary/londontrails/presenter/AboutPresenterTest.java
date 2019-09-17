package com.hubbardgary.londontrails.presenter;

import android.text.Spanned;
import android.text.SpannedString;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.proxy.interfaces.IAndroidFrameworkProxy;
import com.hubbardgary.londontrails.view.GoogleMapsLicenceActivity;
import com.hubbardgary.londontrails.view.interfaces.IAboutView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AboutPresenterTest {
    private IAboutView mockView;
    private AboutPresenter sut;

    @Before
    public void setUp() {
        IAndroidFrameworkProxy mockProxy = Mockito.mock(IAndroidFrameworkProxy.class);
        mockView = Mockito.mock(IAboutView.class);
        when(mockProxy.fromHtml(anyString())).thenReturn(new SpannedString(""));
        sut = new AboutPresenter(mockView, mockProxy);
    }

    @Test
    public void initializeView_InvokesCorrectMethodsOnView() {
        // Act
        sut.initializeView();

        // Assert
        verify(mockView).setupButtons();
        verify(mockView).setDisplayText((Spanned) anyObject());
    }

    @Test
    public void buttonClicked_LegalNoticesButton_InvokesGoogleMapsLicenceActivity() {
        // Act
        sut.buttonClicked(R.id.btn_legal);

        // Assert
        verify(mockView).invokeActivity(new HashMap<String, Integer>(), GoogleMapsLicenceActivity.class);
    }

    @Test
    public void menuItemSelected_Home_EndsActivity() {
        // Act
        sut.menuItemSelected(android.R.id.home);

        // Assert
        verify(mockView).endActivity();
    }
}

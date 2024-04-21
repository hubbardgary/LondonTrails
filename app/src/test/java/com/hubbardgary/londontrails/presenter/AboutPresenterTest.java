package com.hubbardgary.londontrails.presenter;

import android.text.Spanned;

import com.hubbardgary.londontrails.proxy.interfaces.IAndroidFrameworkProxy;
import com.hubbardgary.londontrails.view.interfaces.IAboutView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class AboutPresenterTest {
    private IAboutView mockView;
    private AboutPresenter sut;

    @Before
    public void setUp() {
        IAndroidFrameworkProxy mockProxy = Mockito.mock(IAndroidFrameworkProxy.class);
        mockView = Mockito.mock(IAboutView.class);
        sut = new AboutPresenter(mockView, mockProxy);
    }

    @Test
    public void initializeView_InvokesCorrectMethodsOnView() {
        // Act
        sut.initializeView();

        // Assert
        verify(mockView).setDisplayText((Spanned) any());
    }

    @Test
    public void menuItemSelected_Home_EndsActivity() {
        // Act
        sut.menuItemSelected(android.R.id.home);

        // Assert
        verify(mockView).endActivity();
    }
}

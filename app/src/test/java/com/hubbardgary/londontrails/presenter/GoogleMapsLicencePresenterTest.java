package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.proxy.interfaces.IAndroidFrameworkProxy;
import com.hubbardgary.londontrails.view.interfaces.IGoogleMapsLicenceView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GoogleMapsLicencePresenterTest {
    private IGoogleMapsLicenceView mockView;
    private GoogleMapsLicencePresenter sut;

    @Before
    public void setUp() {
        IAndroidFrameworkProxy mockProxy = Mockito.mock(IAndroidFrameworkProxy.class);
        mockView = Mockito.mock(IGoogleMapsLicenceView.class);
        when(mockProxy.getGoogleLicenseInfo((IGoogleMapsLicenceView)anyObject())).thenReturn("Test licence text");
        sut = new GoogleMapsLicencePresenter(mockView, mockProxy);
    }

    @Test
    public void initializeView_InvokesCorrectMethodsOnView() {
        // Act
        sut.initializeView();

        // Assert
        verify(mockView).setText("Test licence text");
    }

    @Test
    public void menuItemSelected_Home_EndsActivity() {
        // Act
        sut.menuItemSelected(android.R.id.home);

        // Assert
        verify(mockView).endActivity();
    }
}

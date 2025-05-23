package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.proxy.interfaces.IAndroidFrameworkProxy;
import com.hubbardgary.londontrails.view.AboutActivity;
import com.hubbardgary.londontrails.view.DisjointedRouteOptionsActivity;
import com.hubbardgary.londontrails.view.RouteOptionsActivity;
import com.hubbardgary.londontrails.view.interfaces.IMainView;
import com.hubbardgary.londontrails.viewmodel.ButtonViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    private IMainView mockView;
    private MainPresenter sut;

    @Before
    public void setUp() {
        mockView = Mockito.mock(IMainView.class);
        IAndroidFrameworkProxy mockProxy = Mockito.mock(IAndroidFrameworkProxy.class);
        when(mockProxy.fromHtml(anyString())).thenAnswer(new Answer<CharSequence>() {
            public CharSequence answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return (CharSequence)(args[0]);
            }
        });

        sut = new MainPresenter(mockView, mockProxy);
    }

    @Captor
    private ArgumentCaptor<List<ButtonViewModel>> captor;

    @Test
    public void InitializeView_PassesCorrectButtonsToActivity() {
        // Act
        sut.initializeView();

        // Assert
        verify(mockView).displayButtons(captor.capture());
        List capturedButtonVMs = captor.getValue();
        assertNotNull(capturedButtonVMs);
        assertEquals(3, capturedButtonVMs.size());
        assertTrue(((ButtonViewModel)capturedButtonVMs.get(0)).label.toString().contains("Green Chain Walk"));
        assertTrue(((ButtonViewModel)capturedButtonVMs.get(1)).label.toString().contains("Capital Ring"));
        assertTrue(((ButtonViewModel)capturedButtonVMs.get(2)).label.toString().contains("London Loop"));
    }

    @Test
    public void InitializeView_InvokesCorrectMethodsOnView() {
        // Act
        sut.initializeView();

        // Assert
        verify(mockView).displayButtons(any(List.class));
        verify(mockView).checkGooglePlayAvailability();
    }

    @Test
    public void routeButtonClicked_GreenChainWalk_InvokesCorrectActivityForRoute() {
        // Arrange
        HashMap<String, Integer> expectedIntent = new HashMap<>();
        expectedIntent.put("routeSections", R.array.green_chain_walk_sections);

        // Act
        sut.routeButtonClicked(R.id.rte_green_chain_walk);

        // Assert
        verify(mockView).invokeActivity(expectedIntent, DisjointedRouteOptionsActivity.class);
    }

    @Test
    public void routeButtonClicked_CapitalRing_InvokesCorrectActivityForRoute() {
        // Arrange
        HashMap<String, Integer> expectedIntent = new HashMap<>();
        expectedIntent.put("routeSections", R.array.capital_ring_sections);

        // Act
        sut.routeButtonClicked(R.id.rte_capital_ring);

        // Assert
        verify(mockView).invokeActivity(expectedIntent, RouteOptionsActivity.class);
    }

    @Test
    public void routeButtonClicked_LondonLoop_InvokesCorrectActivityForRoute() {
        // Arrange
        HashMap<String, Integer> expectedIntent = new HashMap<>();
        expectedIntent.put("routeSections", R.array.london_loop_sections);

        // Act
        sut.routeButtonClicked(R.id.rte_london_loop);

        // Assert
        verify(mockView).invokeActivity(expectedIntent, RouteOptionsActivity.class);
    }

    @Test
    public void menuItemSelected_About_InvokesAboutActivity() {
        // Act
        sut.menuItemSelected(R.id.view_option_about);

        // Assert
        verify(mockView).invokeActivity(new HashMap<String, Integer>(), AboutActivity.class);
    }

    @Test
    public void menuItemSelected_Home_EndsCurrentActivity() {
        // Act
        sut.menuItemSelected(android.R.id.home);

        // Assert
        verify(mockView).endActivity();
    }
}

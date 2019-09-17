package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;
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
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    private IMainView mockView;
    private MainPresenter sut;

    @Before
    public void setUp() {
        GlobalObjects mockGlobals;
        mockView = Mockito.mock(IMainView.class);
        mockGlobals = Mockito.mock(GlobalObjects.class);
        when(mockGlobals.getButtonText(anyString(), anyString())).thenAnswer(new Answer<String>() {
            public String answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return args[0] + "|" + args[1];
            }
        });

        sut = new MainPresenter(mockView, mockGlobals);
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
        assertTrue(((ButtonViewModel)capturedButtonVMs.get(0)).label.toString().startsWith("Green Chain Walk"));
        assertTrue(((ButtonViewModel)capturedButtonVMs.get(1)).label.toString().startsWith("Capital Ring"));
        assertTrue(((ButtonViewModel)capturedButtonVMs.get(2)).label.toString().startsWith("London Loop"));
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

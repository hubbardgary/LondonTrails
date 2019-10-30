package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.model.interfaces.ISection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RouteTest {

    private Route _sut;

    @Before
    public void setUp() throws Exception {
        _sut = new LondonLoop();
    }

    @Test
    public void setSections_correctlySetsSections() {
        ISection[] sections = new ISection[2];
        ISection section0 = new Section(_sut);
        ISection section1 = new Section(_sut);
        sections[0] = section0;
        sections[1] = section1;
        _sut.setSections(sections);
        ISection[] result = _sut.getSections();
        assertSame(sections, result);
    }

    @Test
    public void setSection_correctlySetsSection() {
        ISection[] sections = new ISection[2];
        _sut.setSections(sections);

        ISection section0 = new Section(_sut);
        ISection section1 = new Section(_sut);
        _sut.setSection(0, section0);
        _sut.setSection(1, section1);

        assertSame(section0, _sut.getSection(0));
        assertSame(section1, _sut.getSection(1));
    }

    @Test
    public void setCircularToTrue_correctlySetsCircular() {
        boolean circular = true;
        _sut.setCircular(circular);
        boolean result = _sut.isCircular();
        assertEquals(circular, result);
    }

    @Test
    public void setCircularToFalse_correctlySetsCircular() {
        boolean circular = false;
        _sut.setCircular(circular);
        boolean result = _sut.isCircular();
        assertEquals(circular, result);
    }

    @Test
    public void setLinearToTrue_correctlySetsLinear() {
        boolean linear = true;
        _sut.setLinear(linear);
        boolean result = _sut.isLinear();
        assertEquals(linear, result);
    }

    @Test
    public void setLinearToFalse_correctlySetsLinear() {
        boolean linear = false;
        _sut.setLinear(linear);
        boolean result = _sut.isLinear();
        assertEquals(linear, result);
    }

    @Test
    public void setName_correctlySetsName() {
        final String name = "Test Name";
        _sut.setName(name);
        String result = _sut.getName();
        assertEquals(name, result);
    }

    @Test
    public void setShortName_correctlySetsShortName() {
        final String shortName = "tn";
        _sut.setShortName(shortName);
        String result = _sut.getShortName();
        assertEquals(shortName, result);
    }

    @Test
    public void setDistanceInKm_correctlySetsDistanceInKm() {
        double distance = 253.56;
        _sut.setDistanceInKm(distance);
        double result = _sut.getDistanceInKm();
        assertEquals(distance, result, 0.0001);
    }
}
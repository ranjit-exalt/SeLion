package com.paypal.selion.grid.internal;

import static org.testng.AssertJUnit.assertEquals;
import io.selendroid.common.SelendroidCapabilities;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.Test;
import org.uiautomation.ios.IOSCapabilities;

public class MobileCapabilityMatcherTest {

    @Test
    public void matchAppiumNode() {
        MobileCapabilityMatcher matcher = new MobileCapabilityMatcher();

        Map<String, Object> nodeCapability = new HashMap<String, Object>();
        nodeCapability.put("platformName", "Android");
        nodeCapability.put("platformVersion", "4.4.2");
        nodeCapability.put("mobileNodeType", "appium");

        //Success scenario1
        Map<String, Object> requestedCapability = new HashMap<String, Object>();
        requestedCapability.put("mobileNodeType", "appium");
        requestedCapability.put("platformName", "Android");
        requestedCapability.put("platformVersion", "4.4.2");
        requestedCapability.put("deviceName", "Android Emulator");
        assertEquals(matcher.matches(nodeCapability, requestedCapability), true);

        //Success scenario2
        requestedCapability.clear();
        requestedCapability.put("mobileNodeType", "appium");
        requestedCapability.put("platformName", "Android");
        assertEquals(matcher.matches(nodeCapability, requestedCapability), true);

        //Failure scenario
        requestedCapability.clear();
        requestedCapability.put("mobileNodeType", "appium");
        requestedCapability.put("platformName", "Android");
        requestedCapability.put("platformVersion", "4.2.2");
        requestedCapability.put("deviceName", "Android Emulator");
        assertEquals(matcher.matches(nodeCapability, requestedCapability), false);
    }

    @Test
    public void matchSelendroid() {
        MobileCapabilityMatcher matcher = new MobileCapabilityMatcher();

        Map<String, Object> nodeCapability = new HashMap<String, Object>();
        nodeCapability.put(CapabilityType.BROWSER_NAME, "selendroid");
        nodeCapability.put("mobileNodeType", "selendroid");

        //Success scenario
        Map<String, Object> requestedCapability = new HashMap<String, Object>();
        requestedCapability.put("mobileNodeType", "selendroid");
        requestedCapability.put(CapabilityType.BROWSER_NAME, "selendroid");
        requestedCapability.put(SelendroidCapabilities.AUT, "appname");
        requestedCapability.put(SelendroidCapabilities.LOCALE, "en_us");
        assertEquals(matcher.matches(nodeCapability, requestedCapability), true);

        //Failure scenario
        requestedCapability.clear();
        requestedCapability.put("mobileNodeType", "selendroid");
        requestedCapability.put(CapabilityType.BROWSER_NAME, "firefox");
        assertEquals(matcher.matches(nodeCapability, requestedCapability), false);
    }

    @Test
    public void matchIOSNode() {
        MobileCapabilityMatcher matcher = new MobileCapabilityMatcher();

        //Failure scenario
        Map<String, Object> requestedCapability = new HashMap<String, Object>();
        requestedCapability.put("mobileNodeType", "ios-driver");
        requestedCapability.put(IOSCapabilities.DEVICE, "iphone");
        requestedCapability.put(IOSCapabilities.LANGUAGE, "english");
        requestedCapability.put(IOSCapabilities.LOCALE, "en_us");
        requestedCapability.put(IOSCapabilities.BUNDLE_NAME, "appname");
        assertEquals(matcher.matches(new HashMap<String, Object>(), requestedCapability), false);
    }

    @Test
    public void matchBrowser() {
        MobileCapabilityMatcher matcher = new MobileCapabilityMatcher();

        Map<String, Object> nodeCapability = new HashMap<String, Object>();
        nodeCapability.put(CapabilityType.BROWSER_NAME, "chrome");

        //Success scenario
        Map<String, Object> requestedCapability = new HashMap<String, Object>();
        requestedCapability.put(CapabilityType.BROWSER_NAME, "chrome");

        assertEquals(matcher.matches(nodeCapability, requestedCapability), true);
    }

    @Test
    public void matchBrowserWithNonMatchingMobileNodeType() {
        MobileCapabilityMatcher matcher = new MobileCapabilityMatcher();

        Map<String, Object> nodeCapability = new HashMap<String, Object>();
        nodeCapability.put(CapabilityType.BROWSER_NAME, "ff");

        //Success scenario
        Map<String, Object> requestedCapability = new HashMap<String, Object>();
        requestedCapability.put("mobileNodeType", "");
        requestedCapability.put(CapabilityType.BROWSER_NAME, "ff");

        assertEquals(matcher.matches(nodeCapability, requestedCapability), true);
    }
}

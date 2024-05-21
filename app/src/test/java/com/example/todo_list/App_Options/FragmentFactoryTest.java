package com.example.todo_list.App_Options;

import androidx.fragment.app.Fragment;

import org.junit.Test;
import static org.junit.Assert.*;

public class FragmentFactoryTest {

    @Test
    public void getFragment_ReturnsPrivacyFragment_WhenTypeIsPrivacy() {
        Fragment result = FragmentFactory.getFragment("privacy");
        assertTrue("Factory should return a PrivacyFragment", result instanceof PrivacyFragment);
    }

    @Test
    public void getFragment_ReturnsHelpFragment_WhenTypeIsHelp() {
        Fragment result = FragmentFactory.getFragment("help");
        assertTrue("Factory should return a HelpFragment", result instanceof HelpFragment);
    }

    @Test
    public void getFragment_ReturnsAboutFragment_WhenTypeIsAbout() {
        Fragment result = FragmentFactory.getFragment("about");
        assertTrue("Factory should return an AboutFragment", result instanceof AboutFragment);
    }

    @Test
    public void getFragment_ReturnsNull_WhenTypeIsAccount() {
        // Assuming AccountFragment is not implemented yet and should return null
        Fragment result = FragmentFactory.getFragment("account");
        assertNull("Factory should return null for account type", result);
    }

    @Test
    public void getFragment_ReturnsNull_WhenTypeIsUnknown() {
        Fragment result = FragmentFactory.getFragment("unknown");
        assertNull("Factory should return null for unknown types", result);
    }
}

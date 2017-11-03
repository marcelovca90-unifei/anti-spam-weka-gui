package io.github.marcelovca90.gui;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UserInterfaceTest
{
    @Test
    public void constructor_shouldReturnNotNullInstance()
    {
        assertThat(new UserInterface(), notNullValue());
    }
}

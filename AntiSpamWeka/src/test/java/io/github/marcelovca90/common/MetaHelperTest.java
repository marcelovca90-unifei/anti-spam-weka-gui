package io.github.marcelovca90.common;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.marcelovca90.helper.MetaHelper;

@RunWith(MockitoJUnitRunner.class)
public class MetaHelperTest
{
    @Test(expected = IllegalAccessException.class)
    public void privateConstructor_shouldThrowException() throws Exception
    {
        Constructor<MetaHelper> constructor = MetaHelper.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers()), equalTo(true));

        constructor.setAccessible(true);
        constructor.newInstance();
        MetaHelper.class.newInstance();
    }
}

package xyz.marcelo.common;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import weka.classifiers.AbstractClassifier;

public class MethodConfigurationTest
{
    @Test
    public void enum_MethodConfiguration_shouldReturnTwentyOneValues()
    {
        assertThat(MethodConfiguration.values().length, equalTo(21));
        Arrays.stream(MethodConfiguration.values()).forEach(v -> assertThat(MethodConfiguration.valueOf(v.name()), notNullValue()));
    }

    @Test
    public void getName_allValues_shouldReturnNotNullString()
    {
        Arrays.stream(MethodConfiguration.values()).forEach(v -> assertThat(v.getName(), notNullValue()));
    }

    @Test
    public void getConfig_allValues_shouldReturnNotNullString()
    {
        Arrays.stream(MethodConfiguration.values()).forEach(v -> assertThat(v.getConfig(), notNullValue()));
    }

    @Test
    public void getClazz_allValues_shouldReturnNotNullClass()
    {
        Arrays.stream(MethodConfiguration.values()).forEach(v -> assertThat(v.getClazz(), notNullValue()));
    }

    @Test
    public void buildClassifierFor_invalidMethodConfiguration_shouldReturnNullClassifier()
    {
        assertThat(MethodConfiguration.buildClassifierFor((MethodConfiguration) null), nullValue());
    }

    @Test
    public void buildClassifierFor_validMethodConfiguration_shouldReturnNotNullClassifier()
    {
        Arrays.stream(MethodConfiguration.values()).forEach(v ->
        {
            AbstractClassifier classifier = MethodConfiguration.buildClassifierFor(v);

            assertThat(classifier, notNullValue());
            assertThat(classifier, instanceOf(v.getClazz()));
            assertThat(classifier.getOptions(), notNullValue());
        });
    }
}
//**********************************************************************
// Copyright (c) 2017 Telefonaktiebolaget LM Ericsson, Sweden.
// All rights reserved.
// The Copyright to the computer program(s) herein is the property of
// Telefonaktiebolaget LM Ericsson, Sweden.
// The program(s) may be used and/or copied with the written permission
// from Telefonaktiebolaget LM Ericsson or in accordance with the terms
// and conditions stipulated in the agreement/contract under which the
// program(s) have been supplied.
// **********************************************************************
package io.github.marcelovca90.helper;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.math3.primes.Primes;

public class RandomHelper
{
    private final AtomicInteger seed = new AtomicInteger(2);

    private final Random random = new Random(seed.get());

    public int getSeed()
    {
        return seed.get();
    }

    public Random getRandom()
    {
        return random;
    }

    public void update()
    {
        seed.set(Primes.nextPrime(seed.get() + 1));

        random.setSeed(seed.get());
    }

    public void reset()
    {
        seed.set(2);

        random.setSeed(seed.get());
    }
}

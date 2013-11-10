/*
 * Copyright 2013 Andrew Kroh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andrewkroh.common.math;

/**
 * Simple implementation of an exponential distribution.
 * 
 * @author akroh
 */
public class ExponentialDistribution 
{
    /**
     * The mean value for this distribution.
     */
    private final double mean;
    
    /**
     * The rate parameter. Lambda is equal to 1 / mean. For example
     * if you expect to receive phone calls at an average rate of
     * 2 per hour, then you can expect to wait half an hour for
     * every call. 
     */
    private final double lambda;
    
    /**
     * Creates a new exponential distribution with the specified mean.
     * 
     * @param mean
     *            the mean (expected value of the distribution)
     * 
     * @throws IllegalArgumentException
     *             if mean <= 0
     */
    public ExponentialDistribution(double mean) 
    {
        if (mean <= 0)
        {
            throw new IllegalArgumentException("Mean must be positive.");
        }
        
        this.mean = mean;
        this.lambda = 1 / mean;
    }
    
    /**
     * Returns the mean of this distribution.
     * 
     * @return the mean for the distribution
     */
    public double getMean()
    {
        return mean;
    }
    
    /**
     * Returns lambda, the rate parameter, of the distribution which
     * is equal to {@code 1 / mean}.
     * 
     * @return lambda value, or rate parameter
     */
    public double getLambda()
    {
        return lambda;
    }
    
    /**
     * Returns a single sample from the distribution.
     * 
     * @return a single sample from the exponential distribution
     */
    public double sample() 
    {
        return Math.log(Math.random()) / -lambda;
    }
    
    /**
     * Returns a the specified number of samples by calling {@link #sample()} in
     * a loop.
     * 
     * @param numSamples
     *            number of samples to return
     * @return an array of samples from the distribution
     */
    public double[] sample(int numSamples)
    {
        if (numSamples <= 0)
        {
            throw new IllegalArgumentException("Number of samples must be positive.");
        }
        
        double samples[] = new double[numSamples];
        for (int i = 0; i < numSamples; i++)
        {
            samples[i] = sample();
        }
        
        return samples;
    }
}

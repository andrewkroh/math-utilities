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

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Before;
import org.junit.Test;

import com.andrewkroh.common.math.PoissonProcess;

/**
 * Tests {@link PoissonProcess} to verify that the the returned values are close
 * the mean value that was requested (over a large number of samples).
 * 
 * @author akroh
 */
public class PoissonProcessTester
{
    private static final int NUM_SAMPLES = 10000;
    
    private static final long DEFAULT_MEAN = 5000;
    
    /**
     * Observed means are allowed to differ from requested value by this
     * percentage.
     */
    private static final double ACCEPTED_PERCENTAGE_DIFFERENCE = 3.0;
    
    private PoissonProcess process;
    
    private DescriptiveStatistics stats;
    
    @Before
    public void setup()
    {
        stats = new DescriptiveStatistics();
        process = new PoissonProcess(DEFAULT_MEAN);
    }
    
    @Test
    public void getArrivalIntervalMs_returnedValues_haveMeanValueCloseTo5000()
    {
        observedMeanIsCloseToRequestedMean(DEFAULT_MEAN);
    }
    
    @Test
    public void getArrivalIntervalMs_returnedValues_haveMeanValueCloseTo60000()
    {
        observedMeanIsCloseToRequestedMean(60000);
    }
    
    /**
     * Test that the requested mean is close to the observed mean. Acceptance
     * criteria is defined by {@link #ACCEPTED_PERCENTAGE_DIFFERENCE}.
     * 
     * @param mean
     *            mean value to use for PoissonProcess
     */
    private void observedMeanIsCloseToRequestedMean(long mean)
    {
        process = new PoissonProcess(mean);
        
        for (int i = 0; i < NUM_SAMPLES; i++)
        {
            stats.addValue(process.getArrivalIntervalMs());
        }
        
        // Print some statistics:
        System.out.println(stats.toString());
        
        assertThat(stats.getMean(), 
                closeTo(mean, ACCEPTED_PERCENTAGE_DIFFERENCE / 100 * mean));
    }
}

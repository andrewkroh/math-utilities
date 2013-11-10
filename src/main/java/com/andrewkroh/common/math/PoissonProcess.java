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

import java.util.ArrayList;
import java.util.List;

/**
 * A Poisson process is a random process in which the time between each pair of
 * consecutive events has an exponential distribution and each arrival time is
 * independent of other arrival times.
 * 
 * <p>
 * This is a simple implementation of a Poisson process. It views the process as
 * a fixed number of events occurring at each time interval. Given the mean time
 * interval in milliseconds this class generates random time intervals based on
 * an exponential distribution.
 * 
 * <p>
 * For example if you are trying to simulate a process with an average
 * inter-event arrival time of one minute then you could use this class to
 * simulate the process.
 * 
 * <pre>
 * PoissonProcess process = new PoissonProcess(60 * 1000); // 60 sec in ms
 * 
 * // Simulate arrival time of 20 events. Times are given as
 * // as milliseconds since unix epoch.
 * List<Long> arrivalTimesMs = process.getArrivalTimesMs(10);
 * 
 * // while()
 *     if (System.currentTimeMillis() &gt; arrivalTimeMs[i])
 *     {
 *         fireEvent(i);
 *     }
 * 
 *     // sleep()
 * // end while
 * </pre>
 * 
 * @author akroh
 * 
 */
public class PoissonProcess
{
    /**
     * Mean arrival interval in milliseconds.
     */
    private final long meanArrivalIntervalMs;

    /**
     * Exponential probability distribution used to generate the inter-event
     * arrival times.
     */
    private final ExponentialDistribution poissonDistribution;

    /**
     * Constructs a new PoissonProcess with specified mean inter-event arrival
     * time.
     * 
     * @param meanArrivalIntervalMs
     *            mean inter-event arrival time of the process
     */
    public PoissonProcess(long meanArrivalIntervalMs)
    {
        if (meanArrivalIntervalMs <= 0)
        {
            throw new IllegalArgumentException(
                    "Mean arrival interval (ms) must be positive.");
        }

        this.meanArrivalIntervalMs = meanArrivalIntervalMs;
        this.poissonDistribution = new ExponentialDistribution(
                meanArrivalIntervalMs);
    }

    /**
     * Returns the mean inter-event arrival time used
     * to construct this class.
     * 
     * @return mean inter-event arrival time
     */
    public long getMeanArrivalIntervalMs()
    {
        return meanArrivalIntervalMs;
    }

    /**
     * Returns a single Poisson inter-event arrival <strong>interval</strong>.
     * The value is the exponentially distributed <strong>time interval</strong>
     * between to consecutive events. The value is just an interval and not
     * based on unix epech time.
     * 
     * @return a single exponentially distributed interval value
     */
    public long getArrivalIntervalMs()
    {
        return Math.round(poissonDistribution.sample());
    }

    /**
     * Returns an array of event arrival times given as milliseconds since unix
     * epoch. This method generates the arrival time values using the current
     * system time (from {@link System#currentTimeMillis()}).
     * 
     * @param numArrivalTimes
     *            number of arrival times to generate
     * @return list of arrival times given as milliseconds since unix epoch
     */
    public List<Long> getArrivalTimesMs(int numArrivalTimes)
    {
        return getArrivalTimesMs(0, numArrivalTimes);
    }

    /**
     * Returns an array of event arrival times given as milliseconds since unix
     * epoch. This method generates the arrival time values using the current
     * system time (from {@link System#currentTimeMillis()}) plus the specified
     * initial delay.
     * 
     * @param initialDelayMs
     *            amount of time to delay before events will be scheduled
     * @param numArrivalTimes
     *            number of arrival times to generate
     * @return list of arrival times given as milliseconds since unix epoch
     */
    public List<Long> getArrivalTimesMs(long initialDelayMs, int numArrivalTimes)
    {
        if (initialDelayMs < 0)
        {
            throw new IllegalArgumentException(
                    "Initial delay cannot be negative.");
        }
        
        if (numArrivalTimes <= 0)
        {
            throw new IllegalArgumentException(
                    "Number of arrival times must be positive.");
        }

        List<Long> arrivalTimesMs = new ArrayList<Long>(numArrivalTimes);
        for (int i = 0; i < numArrivalTimes; i++)
        {
            if (i == 0)
            {
                arrivalTimesMs.add(System.currentTimeMillis() + 
                        initialDelayMs + getArrivalIntervalMs());
            }
            else
            {
                long previousArrivalTimeMs = arrivalTimesMs.get(i - 1);
                arrivalTimesMs.add(previousArrivalTimeMs + getArrivalIntervalMs());
            }
        }

        return arrivalTimesMs;
    }
}

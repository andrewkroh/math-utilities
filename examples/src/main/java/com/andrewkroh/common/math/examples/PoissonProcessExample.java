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

package com.andrewkroh.common.math.examples;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.andrewkroh.common.math.PoissonProcess;

/**
 * Simple example that simulates a Poisson process with mean of 5 seconds. It
 * prints to standard out when an "event" occurs.
 * 
 * @author akroh
 */
public class PoissonProcessExample
{
    /**
     * Example using PoissonProcess.
     * 
     * @param args
     *            command line arguments which are ignored
     * 
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        // Create Poisson process with mean arrival time
        // of 5 seconds:
        PoissonProcess process = 
                new PoissonProcess(TimeUnit.SECONDS.toMillis(5));
        
        System.out.println("Mean time between events: " + 
                process.getMeanArrivalIntervalMs() + " ms");
        System.out.println("Staring Poisson process at: " + new Date());
        
        // Get a list of 20 arrival times:
        List<Long> arrivalTimesMs = process.getArrivalTimesMs(20);
        
        // Print events as they occur:
        while (!arrivalTimesMs.isEmpty())
        {
            long currentTimeMs = System.currentTimeMillis();
            
            for (Iterator<Long> itr = arrivalTimesMs.iterator(); itr.hasNext();)
            {
                Long arrivalTimeMs = itr.next();
                
                if (currentTimeMs >= arrivalTimeMs)
                {
                    System.out.println("Firing event at " + new Date(arrivalTimeMs));
                    
                    // Remove the expired time.
                    itr.remove();
                }
                else
                {
                    break;
                }
            }
                
            Thread.sleep(100);
        }
    }

}

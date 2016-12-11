package com.vieira.sudoku.common;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test the cache class and the timeout mechanism.
 * @author Fernando Jose Vieira
 *
 */
public class CacheTests {

    @Test
    public void testPuttingAndGetting() throws InterruptedException{
	TimeoutCache<String, String> cache = new ExecutorServiceTimeoutCache<>();
	cache.put("abc1", "abcd1", 1);
	cache.put("abc2", "abcd2", 1);
	
	Assert.assertEquals(cache.get("abc1"), "abcd1");
	
	Assert.assertEquals(cache.get("abc2"), "abcd2");
	
	Thread.sleep(2000);
	
	Assert.assertEquals(cache.get("abc1"), null);
	
	Assert.assertEquals(cache.get("abc2"), null);
    }
    
}

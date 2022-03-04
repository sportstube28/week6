package com.leszko.calculator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
/**
 * Main Calculator Application.
 *   */
@Service
public class Calculator {
        final static int umlNUMBER1 = A3;  // OK
	@Cacheable("sum")
	public int sum(int a, int b) {
		return a + b;
	}
}

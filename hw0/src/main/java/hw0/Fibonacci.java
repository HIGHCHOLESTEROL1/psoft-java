	/**
	 * This is part of HW0: Environment Setup and Java Introduction.
	 */
	package hw0;
	import java.util.HashMap;
	import java.util.Map;
	
	/**
	 * Fibonacci calculates the <var>n</var>th term in the Fibonacci sequence.
	 *
	 * The first two terms of the Fibonacci sequence are 0 and 1,
	 * and each subsequent term is the sum of the previous two terms.
	 *
	 */
	public class Fibonacci {
	
	    /**
	     * Calculates the desired term in the Fibonacci sequence.
	     *
	     * @param n the index of the desired term; the first index of the sequence is 0
	     * @return the <var>n</var>th term in the Fibonacci sequence
	     * @throws IllegalArgumentException if <code>n</code> is not a nonnegative number
	     */

		 // store the already calculated fibonacci numbers
		private static Map<Integer, Long> memory = new HashMap<>(); 
		
	    public long getFibTerm(int n) {
	        if (n < 0) {
	            throw new IllegalArgumentException(n + " is negative");
	        } else if (memory.containsKey(n)){
				return memory.get(n);
			} else if (n < 2) {
				memory.put(n, (long)n);
	            return n;
	        } else {
				long term = getFibTerm(n - 1) + getFibTerm(n - 2);
				memory.put(n, term);
				return term;
	        }
	    }
	}
package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node a = poly1;
		Node b = poly2;
		Node front = null;
		Node current = null;
		while (a != null || b != null) {
			if (a != null && b == null) {
				if (current == null) {
					Node now = new Node (a.term.coeff, a.term.degree, null);
					current = now;
					front = now;
					a = a.next;
					continue;
				} else {
					Node now = new Node (a.term.coeff, a.term.degree, null);
					current.next = now;
					current = now;
					a = a.next;
					continue;
				}
			}
			else if (b != null && a == null) {
				if (current == null) {
					Node now = new Node (b.term.coeff, b.term.degree, null);
					current = now;
					front = now;
					b = b.next;
					continue;
				} else {
					Node now = new Node (b.term.coeff, b.term.degree, null);
					current.next = now;
					current = now;
					b = b.next;
					continue;
				}
			}
			else if (a.term.degree < b.term.degree) {
				if (current == null) {
					Node now = new Node (a.term.coeff, a.term.degree, null);
					current = now;
					front = now;
					a = a.next;
					continue;
				} else {
					Node now = new Node (a.term.coeff, a.term.degree, null);
					current.next = now;
					current = now;
					a = a.next;
					continue;
				}
			} else if (a.term.degree > b.term.degree) {
				if (current == null) {
					Node now = new Node (b.term.coeff, b.term.degree, null);
					current = now;
					front = now;
					b = b.next;
					continue;
				} else {
					Node now = new Node (b.term.coeff, b.term.degree, null);
					current.next = now;
					current = now;
					b = b.next;
					continue;
				}
			} else {
				if (a.term.coeff + b.term.coeff == 0) {
					a = a.next;
					b = b.next;
				}
				else if (current == null) {
					Node now = new Node (a.term.coeff + b.term.coeff, a.term.degree, null);
					current = now;
					front = now;
					a = a.next;
					b = b.next;
				} else {
					Node now = new Node(a.term.coeff + b.term.coeff, a.term.degree, null);
					current.next = now;
					current = now;
					a = a.next;
					b = b.next;
				}
			}
		}
		return front;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if (poly1 == null || poly2 == null) {
			return null;
		}
		Node a = poly1;
		Node b = poly2;
		Node result = null;
		while (a != null) {
			b = poly2;
			Node front = null;
			Node current = null;
			while (b != null) {
				Node x = new Node(a.term.coeff * b.term.coeff, a.term.degree + b.term.degree, null);
				if (current == null) {
					current = x;
					front = current;
					b = b.next;
					continue;
				} else {
					current.next = x;
					current = x;
					b = b.next;
					continue;
				}
			}
			Node temp = add(result, front);
			result = temp;	
			a = a.next;
		}
		return result;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node a = poly;
		float sum = 0;
		while (a != null) {
			sum += a.term.coeff * ((float)(Math.pow(x, (float)a.term.degree)));
			a = a.next;
		}
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}

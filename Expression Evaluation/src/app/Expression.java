package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	StringTokenizer hi = new StringTokenizer(expr, " /+*-()]0123456789");
    	if(hi.hasMoreTokens() == false) {
    		return;
    	}
		String hello = hi.nextToken();
		boolean hasArray = false;
		while(hi.hasMoreTokens() == true) {
			hasArray = false;
			for (int i = 0; i < hello.length(); i++) {
				if (hello.charAt(i) == '[') {
					hasArray = true;
				}
			}
			if (hasArray == true) {
				if (hello.charAt(hello.length() - 1) == '[') {
					int i = 0;
					int j = 0;
					while (j < hello.length()) {
						if (hello.charAt(j) == '[') {
							String input = hello.substring(i, j);
							Array x = new Array(input);
							arrays.add(x);
							i = j+1;
							j++;
							continue;					
						}
						j++;
					}
				} else {
					int i = 0;
					int j = 0;
					while (j < hello.length()) {
						if (hello.substring(i).indexOf('[') == -1) {
							String input = hello.substring(i);
							Variable x = new Variable(input);
							vars.add(x);
							break;
						} else {
							while (j < hello.length()) {
								if (hello.charAt(j) == '[') {
									break;
								}
								j++;
							}
							String input = hello.substring(i, j);
							Array x = new Array(input);
							arrays.add(x);
							i = j+1;
							j++;
							continue;					
						}
					}
				}
				hello = hi.nextToken();
				continue;
			} else {
				Variable y = new Variable(hello);
				vars.add(y);
				hello = hi.nextToken();
				continue;
			}
		}
		hasArray = false;
		for (int i = 0; i < hello.length(); i++) {
			if (hello.charAt(i) == '[') {
				hasArray = true;
			}
		}
		if (hasArray == true) {
			if (hello.charAt(hello.length() - 1) == '[') {
				int i = 0;
				int j = 0;
				while (j < hello.length()) {
					if (hello.charAt(j) == '[') {
						String input = hello.substring(i, j);
						Array x = new Array(input);
						arrays.add(x);
						i = j+1;
						j++;
						continue;					
					}
					j++;
				}
			} else {
				int i = 0;
				int j = 0;
				while (j < hello.length()) {
					if (hello.substring(i).indexOf('[') == -1) {
						String input = hello.substring(i);
						Variable x = new Variable(input);
						vars.add(x);
						break;
					} else {
						while (j < hello.length()) {
							if (hello.charAt(j) == '[') {
								break;
							}
							j++;
						}
						String input = hello.substring(i, j);
						Array x = new Array(input);
						arrays.add(x);
						i = j+1;
						j++;
						continue;					
					}
				}
			}
		} else {
			Variable y = new Variable(hello);
			vars.add(y);
		}
		for (int i = 0; i < vars.size(); i++) {
			for (int j = 0 ; j < vars.size(); j++) {
				if (j == i) {
					continue;
				} else {
					if (vars.get(i).name.contentEquals(vars.get(j).name)) {
						vars.remove(j);
						j--;
					} else {
						continue;
					}
				}
			}
		}
		for (int i = 0; i < arrays.size(); i++) {
			for (int j = 0 ; j < arrays.size(); j++) {
				if (j == i) {
					continue;
				} else {
					if (arrays.get(i).name.contentEquals(arrays.get(j).name)) {
						arrays.remove(j);
						j--;
					} else {
						continue;
					}
				}
			}
		}
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	// following line just a placeholder for compilation
    	Stack <Float> hi = new Stack<>();
		for (int i = 0; i < expr.length();) {
			if (expr.charAt(i) == '(') {
				int j = i+1;
				int count = 1;
				while (count > 0) {
					char w = expr.charAt(j);
					if (w == '(') {
						j++;
						count++;
					} else if (w == ')') {
						j++;
						count--;
					} else {
						j++;
						continue;
					}
				}
				j -= 1;
				float x = evaluate(expr.substring(i+1, j), vars, arrays);
				hi.push(x);
				i = j++;
				continue;
			}
			if (Character.isLetter(expr.charAt(i))) {
				int a = i;
				while (a < expr.length()) {
					if (Character.isLetter(expr.charAt(a)) != true) {
						break;
					}
					a++;
				}
				if (a == expr.length()) {
					String e = expr.substring(i);
					int t = 0;
					for (int r = 0; r < vars.size(); r++) {
						if (e.contentEquals(vars.get(r).name)) {
							t = r;
							break;
						}
					}
					float o = 0;
					o = vars.get(t).value;
					hi.push(o);
				} else {
					if (expr.charAt(a) == '[') {
						int j = a+1;
						int count = 1;
						while (count > 0) {
							char w = expr.charAt(j);
							if (w == '[') {
								j++;
								count++;
								continue;
							} else if (w == ']') {
								j++;
								count--;
								continue;
							} else {
								j++;
								continue;
							}
						}
						j--;
						String e = expr.substring(i, a);
						int t = 0;
						for (int r = 0; r < arrays.size(); r++) {
							if (e.contentEquals(arrays.get(r).name)) {
								t = r;
								break;
							}
						}
						Array u = arrays.get(t);
						float o = 0;
						o = u.values[(int)evaluate(expr.substring(a+1, j), vars, arrays)];
						hi.push(o);
						a = j;
					} else {
						String e = expr.substring(i, a);
						int t = 0;
						for (int r = 0; r < vars.size(); r++) {
							if (e.contentEquals(vars.get(r).name)) {
								t = r;
								break;
							}
						}
						float o = 0;
						o = vars.get(t).value;
						hi.push(o);
					}
				}
				i = a++;
				continue;
			}
			if (Character.isDigit(expr.charAt(i))) {
				int a = i;
				while (a < expr.length()) {
					if(Character.isDigit(expr.charAt(a)) != true) {
						break;
					}
					a++;
				}
				if (a == expr.length()) {
					float w = Integer.parseInt(expr.substring(i));
					hi.push(w);
					i = a++;
					continue;
				} else {
					float w = Integer.parseInt(expr.substring(i, a));
					hi.push(w);
					i = a++;
					continue;
				}
			}
			if (expr.charAt(i) == '*') {
				if (expr.charAt(i+1) == ' ') {
					while (expr.charAt(i+1) == ' ') {
						i++;
					}
				}
				if (expr.charAt(i+1) == '(') {
					int j = i+2;
					int count = 1;
					while (count > 0) {
						char w = expr.charAt(j);
						if (w == '(') {
							j++;
							count++;
						} else if (w == ')') {
							j++;
							count--;
						} else {
							j++;
							continue;
						}
					}
					j -= 1;
					float x = evaluate(expr.substring(i+2, j), vars, arrays);
					float y = hi.pop();
					float z = x*y;
					hi.push(z);
					i = j++;
					continue;
				}
				if (Character.isLetter(expr.charAt(i+1))) {
					int a = i+1;
					while (a < expr.length()) {
						if (Character.isLetter(expr.charAt(a)) != true) {
							break;
						}
						a++;
					}
					if (a == expr.length()) {
						String e = expr.substring(i+1);
						int t = 0;
						for (int r = 0; r < vars.size(); r++) {
							if (e.contentEquals(vars.get(r).name)) {
								t = r;
								break;
							}
						}
						float o = vars.get(t).value;
						float x = hi.pop();
						float y = x * o;
						hi.push(y);
					} else {
						if (expr.charAt(a) == '[') {
							int j = a+1;
							int count = 1;
							while (count > 0) {
								char w = expr.charAt(j);
								if (w == '[') {
									j++;
									count++;
									continue;
								} else if (w == ']') {
									j++;
									count--;
									continue;
								} else {
									j++;
									continue;
								}
							}
							j--;
							String e = expr.substring(i+1, a);
							int t = 0;
							for (int r = 0; r < arrays.size(); r++) {
								if (e.contentEquals(arrays.get(r).name)) {
									t = r;
									break;
								}
							}
							Array u = arrays.get(t);
							float o = 0;
							o = u.values[(int)evaluate(expr.substring(a+1, j), vars, arrays)];
							float x = hi.pop();
							float y = x * o;
							hi.push(y);
							a = j;
						} else {
							String e = expr.substring(i+1, a);
							int t = 0;
							for (int r = 0; r < vars.size(); r++) {
								if (e.contentEquals(vars.get(r).name)) {
									t = r;
									break;
								}
							}
							float x = hi.pop();
							float o = 0;
							o = vars.get(t).value;
							float y = x * o;
							hi.push(y);
						}
					}
					i = a++;
					continue;
				}
				if (Character.isDigit(expr.charAt(i+1))) {
					int a = i+1;
					while (a < expr.length()) {
						if(Character.isDigit(expr.charAt(a)) != true) {
							break;
						}
						a++;
					}
					if (a == expr.length()) {
						float w = Integer.parseInt(expr.substring(i+1));
						float x = hi.pop();
						float y = w * x;
						hi.push(y);
						i = a++;
						continue;
					} else {
						float w = Integer.parseInt(expr.substring(i+1, a));
						float x = hi.pop();
						float y = w * x;
						hi.push(y);
						i = a++;
						continue;
					}
				}
			}
			if (expr.charAt(i) == '/') {
				if (expr.charAt(i+1) == ' ') {
					while (expr.charAt(i+1) == ' ') {
						i++;
					}
				}
				if (expr.charAt(i+1) == '(') {
					int j = i+2;
					int count = 1;
					while (count > 0) {
						char w = expr.charAt(j);
						if (w == '(') {
							j++;
							count++;
						} else if (w == ')') {
							j++;
							count--;
						} else {
							j++;
							continue;
						}
					}
					j -= 1;
					float x = evaluate(expr.substring(i+2, j), vars, arrays);
					float y = hi.pop();
					float z = y/x;
					hi.push(z);
					i = j++;
					continue;
				}
				if (Character.isLetter(expr.charAt(i+1))) {
					int a = i+1;
					while (a < expr.length()) {
						if (Character.isLetter(expr.charAt(a)) != true) {
							break;
						}
						a++;
					}
					if (a == expr.length()) {
						String e = expr.substring(i+1);
						int t = 0;
						for (int r = 0; r < vars.size(); r++) {
							if (e.contentEquals(vars.get(r).name)) {
								t = r;
								break;
							}
						}
						float o = vars.get(t).value;
						float x = hi.pop();
						float y = x/o;
						hi.push(y);
					} else {
						if (expr.charAt(a) == '[') {
							int j = a+1;
							int count = 1;
							while (count > 0) {
								char w = expr.charAt(j);
								if (w == '[') {
									j++;
									count++;
									continue;
								} else if (w == ']') {
									j++;
									count--;
									continue;
								} else {
									j++;
									continue;
								}
							}
							j--;
							String e = expr.substring(i+1, a);
							int t = 0;
							for (int r = 0; r < arrays.size(); r++) {
								if (e.contentEquals(arrays.get(r).name)) {
									t = r;
									break;
								}
							}
							Array u = arrays.get(t);
							float o = 0;
							o = u.values[(int)evaluate(expr.substring(a+1, j), vars, arrays)];
							float x = hi.pop();
							float y = x/o;
							hi.push(y);
							a = j;
						} else {
							String e = expr.substring(i+1, a);
							int t = 0;
							for (int r = 0; r < vars.size(); r++) {
								if (e.contentEquals(vars.get(r).name)) {
									t = r;
									break;
								}
							}
							float x = hi.pop();
							float o = 0;
							o = vars.get(t).value;
							float y = x/o;
							hi.push(y);
						}
					}
					i = a++;
					continue;
				}
				if (Character.isDigit(expr.charAt(i+1))) {
					int a = i+1;
					while (a < expr.length()) {
						if(Character.isDigit(expr.charAt(a)) != true) {
							break;
						}
						a++;
					}
					if (a == expr.length()) {
						float w = Integer.parseInt(expr.substring(i+1));
						float x = hi.pop();
						float y = x/w;
						hi.push(y);
						i = a++;
						continue;
					} else {
						float w = Integer.parseInt(expr.substring(i+1, a));
						float x = hi.pop();
						float y = x/w;
						hi.push(y);
						i = a++;
						continue;
					}
				}
			}
			i++;
		}
		Stack<Float> hello = new Stack<>();
		while (!hi.isEmpty()) {
			hello.push(hi.pop());
		}
		for (int j = 0; j < expr.length();) {
			if (expr.charAt(j) == '(') {
				int h = j+1;
				int count = 1;
				while (count > 0) {
					char w = expr.charAt(h);
					if (w == '(') {
						h++;
						count++;
						continue;
					} else if (w == ')') {
						h++;
						count--;
						continue;
					} else {
						h++;
						continue;
					}
				}
				h -= 1;
				j = h++;
				continue;
			}
			if (expr.charAt(j) == '[') {
				int h = j;
				int count = 1;
				while (count > 0) {
					h++;
					char w = expr.charAt(h);
					if (w == '[') {
						count++;
					} else if (w == ']') {
						count--;
					} else {
						continue;
					}
				}
				h -= 1;
				j = h++;
				continue;
			}
			if (expr.charAt(j) == '+') {
				float x = hello.pop();
				float y = hello.pop();
				float z = x + y;
				hello.push(z);
				j++;
				continue;
			}
			if (expr.charAt(j) == '-') {
				float x = hello.pop();
				float y = hello.pop();
				float z = x - y;
				hello.push(z);
				j++;
				continue;
			}
			j++;
			continue;
		}
		float result = hello.pop();
		return result;
    }
}

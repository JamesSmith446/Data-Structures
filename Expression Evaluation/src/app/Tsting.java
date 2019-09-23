package app;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Tsting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String yo = "ab*3/abc";
		ArrayList <Variable> x = new ArrayList<>();
		ArrayList <Array> y = new ArrayList<>();
		makeVariableLists(yo, x, y);
		Array eh = y.get(0);
		System.out.print(eh);
	}
	public static void makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
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
	
	public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
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
				while (Character.isLetter(expr.charAt(a))) {
					a++;
				}
				if (expr.charAt(a) == '[') {
					int j = i+1;
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
						}
					}
					j--;
					String e = expr.substring(i, j);
					int t = 0;
					for (int r = 0; r < arrays.size(); r++) {
						if (e.contentEquals(arrays.get(r).name)) {
							t = r;
							break;
						}
					}
					Array u = arrays.get(t);
					float o = 0;
					o = u.values[(int)evaluate(expr.substring(i+1, j), vars, arrays)];
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
				i = a++;
				continue;
			}
			if (expr.charAt(i) >= 0 && expr.charAt(i) <= 9) {
				int a = i;
				while (expr.charAt(a) >= 0 && expr.charAt(a) <= 9) {
					a++;
				}
				float w = Integer.parseInt(expr.substring(i, a));
				hi.push(w);
				i = a++;
				continue;
			}
			if (expr.charAt(i) == '*') {
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
					while (Character.isLetter(expr.charAt(a))) {
						a++;
					}
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
							}
						}
						j--;
						String e = expr.substring(i+1, j);
						int t = 0;
						for (int r = 0; r < arrays.size(); r++) {
							if (e.contentEquals(arrays.get(r).name)) {
								t = r;
								break;
							}
						}
						Array u = arrays.get(t);
						float o = 0;
						o = u.values[(int)evaluate(expr.substring(i+2, j), vars, arrays)];
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
					i = a++;
					continue;
				}
				if (expr.charAt(i+1) >= 0 && expr.charAt(i+1) <= 9) {
					int a = i+1;
					while (expr.charAt(a) >= 0 && expr.charAt(a) <= 9) {
						a++;
					}
					float w = Integer.parseInt(expr.substring(i+1, a));
					float x = hi.pop();
					float y = w * x;
					hi.push(y);
					i = a++;
					continue;
				}
			}
			if (expr.charAt(i) == '/') {
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
					while (Character.isLetter(expr.charAt(a))) {
						a++;
					}
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
							}
						}
						j--;
						String e = expr.substring(i+1, j);
						int t = 0;
						for (int r = 0; r < arrays.size(); r++) {
							if (e.contentEquals(arrays.get(r).name)) {
								t = r;
								break;
							}
						}
						Array u = arrays.get(t);
						float o = 0;
						o = u.values[(int)evaluate(expr.substring(i+2, j), vars, arrays)];
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
					i = a++;
					continue;
				}
				if (expr.charAt(i+1) >= 0 && expr.charAt(i+1) <= 9) {
					int a = i+1;
					while (expr.charAt(a) >= 0 && expr.charAt(a) <= 9) {
						a++;
					}
					float w = Integer.parseInt(expr.substring(i+1, a));
					float x = hi.pop();
					float y = x/w;
					hi.push(y);
					i = a++;
					continue;
				}
			}
			i++;
		}
		Stack<Float> hello = new Stack();
		while (!hi.isEmpty()) {
			hello.push(hi.pop());
		}
		for (int j = 0; j < expr.length();) {
			if (expr.charAt(j) == '(') {
				int h = j+1;
				int count = 1;
				while (count > 0) {
					char w = expr.charAt(j);
					if (w == '(') {
						h++;
						count++;
					} else if (w == ')') {
						h++;
						count--;
					}
				}
				h -= 1;
				j = h++;
				continue;
			}
			if (expr.charAt(j) == '[') {
				int h = j+1;
				int count = 1;
				while (count > 0) {
					char w = expr.charAt(j);
					if (w == '[') {
						h++;
						count++;
					} else if (w == ']') {
						h++;
						count--;
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
				float z = y - x;
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

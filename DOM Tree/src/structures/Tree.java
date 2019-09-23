package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		/** COMPLETE THIS METHOD **/
		String first = sc.nextLine();
		TagNode htmlNode = new TagNode("html", null, null);
		root = htmlNode;
		String second = sc.nextLine();
		TagNode bodyNode = new TagNode("body", null, null);
		htmlNode.firstChild = bodyNode;
		String currentScanner = sc.nextLine();
		TagNode currentbodyNode = bodyNode;
		while (sc.hasNextLine()) {
			if (currentScanner.contentEquals("<p>")) {
				TagNode pNode = new TagNode("p", null, null);
				if (currentbodyNode == bodyNode) {
					currentbodyNode.firstChild = pNode;
				} else {
					currentbodyNode.sibling = pNode;
				}
				currentScanner = sc.nextLine();
				TagNode currentPNode = pNode;
				while(!currentScanner.contentEquals("</p>")) {
					if (currentScanner.contentEquals("<em>")) {
						TagNode emNode = new TagNode("em", null, null);
						if (currentPNode == pNode) {
							currentPNode.firstChild = emNode;
						} else {
							currentPNode.sibling = emNode;
						}
						TagNode text = new TagNode(sc.nextLine(), null, null);
						emNode.firstChild = text;
						currentScanner = sc.nextLine();
						currentScanner = sc.nextLine();
						currentPNode = emNode;
						continue;
					}
					if (currentScanner.contentEquals("<b>")) {
						TagNode boldNode = new TagNode("em", null, null);
						if (currentPNode == pNode) {
							currentPNode.firstChild = boldNode;
						} else {
							currentPNode.sibling = boldNode;
						}
						TagNode text = new TagNode(sc.nextLine(), null, null);
						boldNode.firstChild = text;
						currentScanner = sc.nextLine();
						currentScanner = sc.nextLine();
						currentPNode = boldNode;
						continue;
					}
					TagNode text = new TagNode (currentScanner, null, null);
					if (currentPNode == pNode) {
						currentPNode.firstChild = text;
					} else {
						currentPNode.sibling = text;
					}
					currentPNode = text;
					currentScanner = sc.nextLine();
					continue;
				}
				currentbodyNode = pNode;
				currentScanner = sc.nextLine();
				continue;
			}
			if (currentScanner.contentEquals("<table>")) {
				TagNode tableNode = new TagNode("table", null, null);
				if(currentbodyNode == bodyNode) {
					currentbodyNode.firstChild = tableNode;
				} else {
					currentbodyNode.sibling = tableNode;
				}
				currentScanner = sc.nextLine();
				TagNode currenttableNode = tableNode;
				while (!currentScanner.contentEquals("</table>")) {
					if (currentScanner.contentEquals("<tr>")) {
						TagNode trNode = new TagNode("tr", null, null);
						if (currenttableNode == tableNode) {
							currenttableNode.firstChild = trNode;
						} else {
							currenttableNode.sibling = trNode;
						}
						TagNode currenttrNode = trNode;
						currentScanner = sc.nextLine();
						while (!currentScanner.contentEquals("</tr>")) {
							if (currentScanner.contentEquals("<td>")) {
								TagNode tdNode = new TagNode("td", null, null);
								if (currenttrNode == trNode) {
									currenttrNode.firstChild = tdNode;
								} else {
									currenttrNode.sibling = tdNode;
								}
								TagNode currenttdNode = tdNode;
								currentScanner = sc.nextLine();
								while (!currentScanner.contentEquals("</td>")) {
									if (currentScanner.contentEquals("<em>")) {
										TagNode emNode = new TagNode("em", null, null);
										if (currenttdNode == tdNode) {
											currenttdNode.firstChild = emNode;
										} else {
											currenttdNode.sibling = emNode;
										}
										TagNode text = new TagNode(sc.nextLine(), null, null);
										emNode.firstChild = text;
										currenttdNode = emNode;
										currentScanner = sc.nextLine();
										currentScanner = sc.nextLine();
										continue;
									}
									if (currentScanner.contentEquals("<b>")) {
										TagNode boldNode = new TagNode("b", null, null);
										if (currenttdNode == tdNode) {
											currenttdNode.firstChild = boldNode;
										} else {
											currenttdNode.sibling = boldNode;
										}
										currenttableNode.firstChild = boldNode;
										TagNode text = new TagNode(sc.nextLine(), null, null);
										boldNode.firstChild = text;
										currenttdNode = boldNode;
										currentScanner = sc.nextLine();
										currentScanner = sc.nextLine();
										continue;
									}
									TagNode text = new TagNode(currentScanner, null, null);
									if (currenttdNode == tdNode) {
										currenttdNode.firstChild = text;
									} else {
										currenttdNode.sibling = text;
									}
									currenttdNode = text;
									currentScanner = sc.nextLine();
									continue;
								}
								currentScanner = sc.nextLine();
								currenttrNode = tdNode;
								continue;
							}
						}
						currentScanner = sc.nextLine();
						currenttableNode = trNode;
						continue;
					}
				}
				currentScanner = sc.nextLine();
				currentbodyNode = tableNode;
				continue;
			}
			if(currentScanner.contentEquals("<ol>")) {
				if (currentbodyNode == bodyNode) {
					currentbodyNode.firstChild = makeOl();
					currentbodyNode = currentbodyNode.firstChild;
					currentScanner = sc.nextLine();
					continue;
				} else {
					currentbodyNode.sibling = makeOl();
					currentbodyNode = currentbodyNode.sibling;
					currentScanner = sc.nextLine();
					continue;
				}
			}
			if(currentScanner.contentEquals("<ul>")) {
				if (currentbodyNode == bodyNode) {
					currentbodyNode.firstChild = makeUl();
					currentbodyNode = currentbodyNode.firstChild;
					currentScanner = sc.nextLine();
					continue;
				} else {
					currentbodyNode.sibling = makeUl();
					currentbodyNode = currentbodyNode.sibling;
					currentScanner = sc.nextLine();
					continue;
				}
			}
			if(currentScanner.contentEquals("</body>")) {
				return;
			}
			TagNode text = new TagNode(currentScanner, null, null); //
			if(currentbodyNode == bodyNode) {
				currentbodyNode.firstChild = text;
				currentbodyNode = text;
			} else {
				currentbodyNode.sibling = text;
				currentbodyNode = text;
			}
			currentScanner = sc.nextLine();
			continue;
		}
	}
	
	
	private TagNode makeOl() {
		TagNode olNode = new TagNode("ol", null, null);
		TagNode currentolNode = olNode;
		String s = sc.nextLine();
		while (!s.contentEquals("</ol>")) {
			if (s.contentEquals("<li>")) {
				TagNode liNode = new TagNode("li", null, null);
				if (currentolNode == olNode) {
					currentolNode.firstChild = liNode;
				} else {
					currentolNode.sibling = liNode;
				}
				TagNode currentliNode = liNode;
				s = sc.nextLine();
				while (!s.contentEquals("</li>")) {
					if (s.contentEquals("<ol>")) {
						if(currentliNode == liNode) {
							currentliNode.firstChild = makeOl();
							currentliNode = currentliNode.firstChild;
							s = sc.nextLine();
							continue;
						} else {
							currentliNode.sibling = makeOl();
							currentliNode = currentliNode.sibling;
							s = sc.nextLine();
							continue;
						}
					} else if (s.contentEquals("<ul>")) {
						if(currentliNode == liNode) {
							currentliNode.firstChild = makeUl();
							currentliNode = currentliNode.firstChild;
							s = sc.nextLine();
							continue;
						} else {
							currentliNode.sibling = makeUl();
							currentliNode = currentliNode.sibling;
							s = sc.nextLine();
							continue;
						}
					}
					TagNode text = new TagNode(s, null, null);
					if(currentliNode == liNode) {
						currentliNode.firstChild = text;
					} else {
						currentliNode.sibling = text;
					}
					currentliNode = text;
					s = sc.nextLine();
					continue;
				}
				currentolNode = liNode;
				s = sc.nextLine();
				continue;
			} else if (s.contentEquals("<ol>")) {
				if (currentolNode == olNode) {
					currentolNode.firstChild = makeOl();
					currentolNode = currentolNode.firstChild;
					s = sc.nextLine();
					continue;
				} else {
					currentolNode.sibling = makeOl();
					currentolNode = currentolNode.sibling;
					s = sc.nextLine();
					continue;
				}
			} else if (s.contentEquals("<ul>")) {
				if (currentolNode == olNode) {
					currentolNode.firstChild = makeUl();
					currentolNode = currentolNode.firstChild;
					s = sc.nextLine();
					continue;
				} else {
					currentolNode.sibling = makeUl();
					currentolNode = currentolNode.sibling;
					s = sc.nextLine();
					continue;
				}
			}
			s = sc.nextLine();
			continue;
		}
		return olNode;
	}
	
	private TagNode makeUl() {
		TagNode ulNode = new TagNode("ul", null, null);
		TagNode currentulNode = ulNode;
		String s = sc.nextLine();
		while (!s.contentEquals("</ul>")) {
			if (s.contentEquals("<li>")) {
				TagNode liNode = new TagNode("li", null, null);
				if (currentulNode == ulNode) {
					currentulNode.firstChild = liNode;
				} else {
					currentulNode.sibling = liNode;
				}
				TagNode currentliNode = liNode;
				s = sc.nextLine();
				while (!s.contentEquals("</li>")) {
					if (s.contentEquals("<ol>")) {
						if(currentliNode == liNode) {
							currentliNode.firstChild = makeOl();
							currentliNode = currentliNode.firstChild;
							s = sc.nextLine();
							continue;
						} else {
							currentliNode.sibling = makeOl();
							currentliNode = currentliNode.sibling;
							s = sc.nextLine();
							continue;
						}
					} else if (s.contentEquals("<ul>")) {
						if(currentliNode == liNode) {
							currentliNode.firstChild = makeUl();
							currentliNode = currentliNode.firstChild;
							s = sc.nextLine();
							continue;
						} else {
							currentliNode.sibling = makeUl();
							currentliNode = currentliNode.sibling;
							s = sc.nextLine();
							continue;
						}
					}
					TagNode text = new TagNode(s, null, null);
					if(currentliNode == liNode) {
						currentliNode.firstChild = text;
					} else {
						currentliNode.sibling = text;
					}
					currentliNode = text;
					s = sc.nextLine();
					continue;
				}
				currentulNode = liNode;
				s = sc.nextLine();
				continue;
			} else if (s.contentEquals("<ol>")) {
				if (currentulNode == ulNode) {
					currentulNode.firstChild = makeOl();
					currentulNode = currentulNode.firstChild;
					s = sc.nextLine();
					continue;
				} else {
					currentulNode.sibling = makeOl();
					currentulNode = currentulNode.sibling;
					s = sc.nextLine();
					continue;
				}
			} else if (s.contentEquals("<ul>")) {
				if (currentulNode == ulNode) {
					currentulNode.firstChild = makeUl();
					currentulNode = currentulNode.firstChild;
					s = sc.nextLine();
					continue;
				} else {
					currentulNode.sibling = makeUl();
					currentulNode = currentulNode.sibling;
					s = sc.nextLine();
					continue;
				}
			}
			s = sc.nextLine();
			continue;
		}
		return ulNode;
	}
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
		replaceTagHelper(root, oldTag);
	}
	
	private void replaceTagHelper(TagNode x, String old) {
		if (x == null) {
			return;
		}
		if(x.tag.contentEquals(old)) {
			if(old.contentEquals("em")) {
				x.tag = "b";
			}
			if(old.contentEquals("b")) {
				x.tag = "em";
			}
			if(old.contentEquals("ol")) {
				x.tag = "ul";
			}
			if (old.contentEquals("ul")) {
				x.tag = "ol";
			}
		}
		replaceTagHelper(x.firstChild, old);
		replaceTagHelper(x.sibling, old);
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		/** COMPLETE THIS METHOD **/
		boldRowHelper(root, row);
	}
	
	private void boldRowHelper(TagNode x, int y) {
		if(x == null) {
			return;
		}
		if(x.tag.contentEquals("table")) {
			TagNode trav = x.firstChild;
			int count = 1;
			while (count < y) {
				trav = trav.sibling;
				count++;
			}
			TagNode columns = trav.firstChild;
			while(columns != null) {
				TagNode boldNode = new TagNode("b", null, null);
				boldNode.firstChild = columns.firstChild;
				columns.firstChild = boldNode;
				columns = columns.sibling;
			}
			return;
		}
		boldRowHelper(x.firstChild, y);
		boldRowHelper(x.sibling, y);
	}
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		/** COMPLETE THIS METHOD **/
		deleteHelper(root, null, true, tag);
	}
	
	private void deleteHelper(TagNode x, TagNode prev, boolean isParent, String delete) {
		if (x == null) {
			return;
		}
		if (delete.contentEquals("p")) {
			if (x.tag.contentEquals("p")) {
				if(isParent) {
					prev.firstChild = x.firstChild;
				} else {
					prev.sibling = x.firstChild;
				}
				TagNode connect = x.firstChild;
				TagNode previous = null;
				while (connect != null) {
					previous = connect;
					connect = connect.sibling;
				}
				previous.sibling = x.sibling;
				deleteHelper(x.sibling, previous, false, delete);
				return;
			}
		}
		if (delete.contentEquals("em")) {
			if (x.tag.contentEquals("em")) {
				if(isParent) {
					prev.firstChild = x.firstChild;
				} else {
					prev.sibling = x.firstChild;
				}
				x.firstChild.sibling = x.sibling;
				deleteHelper(x.sibling, x.firstChild, false, delete);
			}
		}
		if (delete.contentEquals("b")) {
			if (x.tag.contentEquals("b")) {
				if(isParent) {
					prev.firstChild = x.firstChild;
				} else {
					prev.sibling = x.firstChild;
				}
				x.firstChild.sibling = x.sibling;
				deleteHelper(x.sibling, x.firstChild, false, delete);
			}
		}
		if(delete.contentEquals("ol")) {
			if (x.tag.contentEquals("ol")) {
				TagNode trav = x.firstChild;
				TagNode previous = x.firstChild;
				while (trav != null) {
					trav.tag = "p";
					previous = trav;
					trav = trav.sibling;
				}
				if (isParent) {
					prev.firstChild = x.firstChild;
				} else {
					prev.sibling = x.firstChild;
				}
				previous.sibling = x.sibling;
				deleteHelper(x.sibling, previous, false, delete);
			}
		}
		if(delete.contentEquals("ul")) {
			if (x.tag.contentEquals("ul")) {
				TagNode trav = x.firstChild;
				TagNode previous = x.firstChild;
				while (trav != null) {
					trav.tag = "p";
					previous = trav;
					trav = trav.sibling;
				}
				if (isParent) {
					prev.firstChild = x.firstChild;
				} else {
					prev.sibling = x.firstChild;
				}
				previous.sibling = x.sibling;
				deleteHelper(x.sibling, previous, false, delete);
			}
		}
		deleteHelper(x.firstChild, x, true, delete);
		deleteHelper(x.sibling, x, false, delete);
	}
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
		addTaghelper(root, word, tag);
	}
	
	private TagNode addTaghelper(TagNode x, String target, String tag) {
		if (x == null) {
			return null;
		}
		TagNode current = null;
		TagNode root = null;
		int createdNode = 0;
		int lastNode = 0;
		boolean wasCreated = false;
		for (int i = 0; i < x.tag.length();) {
			if(x.tag.charAt(i) == '.' || x.tag.charAt(i) == ',' || x.tag.charAt(i) == '?' || x.tag.charAt(i) == '!' || x.tag.charAt(i) == ':' || x.tag.charAt(i) == ';') {
				i+= 2;
				continue;
			}
			if (Character.toLowerCase(x.tag.charAt(i)) == Character.toLowerCase(target.charAt(0))) {
				int j = i;
				int k = 0;
				while (k < target.length() && j < x.tag.length()) {
					if (Character.toLowerCase(x.tag.charAt(j)) != Character.toLowerCase(target.charAt(k))) {
						i = j;
						break;
					}
					j++;
					k++;
				}
				if (k == target.length()) {
					if (j >= x.tag.length()) {
						wasCreated = true;
						TagNode y = null;
						if(tag.contentEquals("em")) {
							y = new TagNode("em", null, null);
						} else {
							y = new TagNode("b", null, null);
						}
						TagNode text = new TagNode(x.tag.substring(i), null, null);
						y.firstChild = text;
						if(i > createdNode) {
							TagNode before = new TagNode(x.tag.substring(createdNode, i), null, null);
							before.sibling = y;
							if(current == null) {
								root = before;
								current = y;
							} else {
								current.sibling = before;
								current = y;
							}
							createdNode = j;
						} else {
							if(current == null) {
								current = y;
								root = y;
							} else {
								current.sibling = y;
								current = y;
							}
						}
						createdNode = j;
						lastNode = j;
						i = j;
						continue;
					}
					if(x.tag.charAt(j) == ' ') {
						wasCreated = true;
						TagNode y = null;
						if(tag.contentEquals("em")) {
							y = new TagNode("em", null, null);
						} else {
							y = new TagNode("b", null, null);
						}
						TagNode text = new TagNode(x.tag.substring(i, j), null, null);
						y.firstChild = text;
						if(i > createdNode) {
							TagNode before = new TagNode(x.tag.substring(createdNode, i), null, null);
							before.sibling = y;
							if(current == null) {
								root = before;
								current = y;
							} else {
								current.sibling = before;
								current = y;
							}
						} else {
							if(current == null) {
								current = y;
								root = y;
							} else {
								current.sibling = y;
								current = y;
							}
						}
						createdNode = j;
						lastNode = j;
						i = j;
						continue;
					}
					if(x.tag.charAt(j) == '.' || x.tag.charAt(j) == ',' || x.tag.charAt(j) == '?' || x.tag.charAt(j) == '!' || x.tag.charAt(j) == ':' || x.tag.charAt(j) == ';') {
						if (j+1 >= x.tag.length()) {
							wasCreated = true;
							TagNode y = null;
							if(tag.contentEquals("em")) {
								y = new TagNode("em", null, null);
							} else {
								y = new TagNode("b", null, null);
							}
							TagNode text = new TagNode(x.tag.substring(i), null, null);
							y.firstChild = text;
							if(i > createdNode) {
								TagNode before = new TagNode(x.tag.substring(createdNode, i), null, null);
								before.sibling = y;
								if(current == null) {
									root = before;
									current = y;
								} else {
									current.sibling = before;
									current = y;
								}
								createdNode = j+1;
							} else {
								if(current == null) {
									current = y;
									root = y;
								} else {
									current.sibling = y;
									current = y;
								}
							}
							lastNode = j+1;
							createdNode = j+1;
							i = j+1;
							continue;
						}
						if(x.tag.charAt(j+1) == ' ') {
							wasCreated = true;
							TagNode y = null;
							if(tag.contentEquals("em")) {
								y = new TagNode("em", null, null);
							} else {
								y = new TagNode("b", null, null);
							}
							TagNode text = new TagNode(x.tag.substring(i, j+1), null, null);
							y.firstChild = text;
							if(i > createdNode) {
								TagNode before = new TagNode(x.tag.substring(createdNode, i), null, null);
								before.sibling = y;
								if(current == null) {
									root = before;
									current = y;
								} else {
									current.sibling = before;
									current = y;
								}
								createdNode = j+1;
							} else {
								if(current == null) {
									current = y;
									root = y;
								} else {
									current.sibling = y;
									current = y;
								}
							}
							lastNode = j+1;
							createdNode = j+1;
							i = j+1;
							continue;
						} else {
							i = j;
							continue;
						}
					}
					i = j;
					continue;
				}	
			}
			i++;
			continue;
		}
		if (lastNode < x.tag.length() && wasCreated == true) {
			TagNode lastTag = new TagNode(x.tag.substring(lastNode), null, null);
			current.sibling = lastTag;
			current = lastTag;
		}
		if (wasCreated == false) {
			x.firstChild = addTaghelper(x.firstChild, target, tag);
			x.sibling = addTaghelper(x.sibling, target, tag);
			return x;
		}
		current.sibling = addTaghelper(x.sibling, target, tag);
		return root;
	}
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|----");
			} else {
				System.out.print("     ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}

package avl;
/* Author: Kevon Hyde
 * Date: 07/14/2025
 * Description: This program can either generate a given search tree */
public class AVL {

  public Node root;

  private int size;

  public int getSize() {
      return size;
  }

  /** return height of the tree */
  public int getHeight() {
      return getHeight(root);
  }

  /** return height of given subtree */
  private int getHeight(Node n) {
      if (n == null) {
          return -1;
      }
      return 1 + max(getHeight(n.left), getHeight(n.right));
  }

  /** helper method that recursiely updates the height of a given node and
  * its ancestors */
  private void updateHeight(Node n) {
      n.height = getHeight(n);
      if (n.parent != null) {
          updateHeight(n.parent);
      }
  }

  /** helper method that takes two ints and returns the greater value */
  private int max(int n1, int n2) {
      if (n1 >= n2) {
          return n1;
      } else {
          return n2;
      }
  }

  /** set of helper methods that return the balance factor of a node
  * if no input, then it takes the balance factor of the root instead */
  private int getBalance() {
      return getBalance(root);
  }

  private int getBalance(Node n) {
      return getHeight(n.right) - getHeight(n.left);
  }

  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
      return search(root, w);
    }
    private Node search(Node n, String w) {
      if (n == null) {
        return null;
      }
      if (w.equals(n.word)) {
        return n;
      } else if (w.compareTo(n.word) < 0) {
        return search(n.left, w);
      } else {
        return search(n.right, w);
      }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  private void bstInsert(Node n, String w) {
      if (w.length() == 0) {
          return;
      } //lexicographically compare words to see which ones are "greater" or "lesser"
      if (w.compareTo(n.word) < 0) {
          if (n.left == null) {
              n.left = new Node(w);
              n.left.parent = n;
              size++;
              updateHeight(n);
              return;
          }
          bstInsert(n.left, w);
      } else if (w.compareTo(n.word) > 0) {
          if (n.right == null) {
              n.right = new Node(w);
              n.right.parent = n;
              size++;
              updateHeight(n);
              return;
          }
          bstInsert(n.right, w);
      } else {  //if word is a duplicate, don't do anything
          return;
      }
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced and any prior insertions have been
  *  performed by this method. */
  public void avlInsert(String w) {
      if (root == null) {
          root = new Node(w);
          size = 1;
          return;
      }
      avlInsert(root, w);
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
      if (w.compareTo(n.word) < 0) {
          if (n.left == null) {
              n.left = new Node(w);
              n.left.parent = n;
              size++;
              updateHeight(n);
              return;
          }
        avlInsert(n.left, w);
        rebalance(n);
    } else if (w.compareTo(n.word) > 0) {
          if (n.right == null) {
              n.right = new Node(w);
              n.right.parent = n;
              size++;
              updateHeight(n);
              return;
          }
        avlInsert(n.right, w);
        rebalance(n);
    } else {
        return;
      }
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
      Node y = x.right;
      //x's right subtree becomes y's old left subtree
      x.right = y.left;
      if (y.left != null) {
          y.left.parent = x;
      }
      //transfer parents, check for root
      y.parent = x.parent;
      if (x.parent == null) {
          root = y;
      } else if (x == x.parent.left) {//set x's old parent's left or right to y
          x.parent.left = y;
      } else {
          x.parent.right = y;
      }
      y.left = x;
      x.parent = y;
      updateHeight(x);
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
      Node x = y.left;
      //y's left subtree becomes x's old right subtree
      y.left = x.right;
      if (x.right != null) {
          x.right.parent = y;
      }
      //transfer parents, check for root
      x.parent = y.parent;
      if (y.parent == null) {
          root = x;
      } else if (y == y.parent.right) {//set y's old parent's left or right to x
          y.parent.right = x;
      } else {
          y.parent.left = x;
      }
      x.right = y;
      y.parent = x;
      updateHeight(y);
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
      if (getBalance(n) < -1) {  //imbalance on left side of tree
          if (getBalance(n.left) < 0) {
              //if node was added to left child's left
              rightRotate(n);
          } else {
              //if node was added to left child's right
              leftRotate(n.left);
              rightRotate(n);
          }
      } else if (getBalance(n) > 1) { //imbalance on right side of tree
          if (getBalance(n.right) > 0) {  //like first two cases in reverse
              leftRotate(n);
          } else {
              rightRotate(n.right);
              leftRotate(n);
          }
      }
  }

  /** remove the word w from the tree */
  public void remove(String w) {
    remove(root, w);
  }

  /* remove w from the tree rooted at n */
  private void remove(Node n, String w) {
    return; // (enhancement TODO - do the base assignment first)
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
    public static void main(String[] args) {
        AVL a = new AVL();
        a.avlInsert("a");
        a.avlInsert("b");
        a.avlInsert("c");
    }
}

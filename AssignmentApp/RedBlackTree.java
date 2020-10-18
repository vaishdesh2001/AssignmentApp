// --== CS400 File Header Information ==--
// Name: Vaishnavi Deshpande
// Email: vvdeshpande@wisc.edu email address
// Team: your team name: LB
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;

/**
 * Binary Search Tree implementation with a Node inner class for representing the nodes within a
 * binary search tree. You can use this class' insert method to build a binary search tree, and its
 * toString method to display the level order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

  /**
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always be maintained.
   */
  protected static class Node<T> {
    public T data;
    public Node<T> parent; // null for root node
    public Node<T> leftChild;
    public Node<T> rightChild;
    public boolean isBlack;

    public Node(T data) {
      this.data = data;
      this.isBlack = false;
    }

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

    /**
     * This method performs a level order traversal of the tree rooted at the current node. The
     * string representations of each data value within this tree are assembled into a comma
     * separated string within brackets (similar to many implementations of java.util.Collection).
     * 
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() { // display subtree in order traversal
      String output = "[";
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.leftChild != null)
          q.add(next.leftChild);
        if (next.rightChild != null)
          q.add(next.rightChild);
        output += next.data.toString();
        if (!q.isEmpty())
          output += "\n";
      }
      return output + "]";
    }
  }

  protected Node<T> root; // reference to root node of tree, null when empty

  /**
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the tree already contains data
   */
  public void insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");

    Node<T> newNode = new Node<>(data);
    if (root == null) {
      root = newNode;
    } // add first node to an empty tree
    else
      insertHelper(newNode, root); // recursively insert into subtree
    root.isBlack = true;
  }

  /**
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   * 
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descenedent beneath
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references (as
   *                                  defined by Comparable.compareTo())
   */
  private void insertHelper(Node<T> newNode, Node<T> subtree) {
    int compare = newNode.data.compareTo(subtree.data);
    // do not allow duplicate values to be stored within this tree
    if (compare == 0)
      throw new IllegalArgumentException("This RedBlackTree already contains that value.");

    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.leftChild);
    }

    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.rightChild);
    }
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection, like java.util.ArrayList, LinkedList,
   * etc).
   * 
   * @return string containing the values of this tree in level order
   */
  @Override
  public String toString() {
    return root.toString();
  }

  /**
   * Performs the rotation operation on the provided nodes within this BST. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation (sometimes
   * called a left-right rotation). When the provided child is a rightChild of the provided parent,
   * this method will perform a left rotation (sometimes called a right-left rotation). When the
   * provided nodes are not related in one of these ways, this method will throw an
   * IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    // Implement this method.
    // child is the left-subtree of parent, and will perform right rotation
    if (parent != null && parent.leftChild == child) {
      // finding the grandparent of the child
      Node<T> grandParent = parent.parent;
      // storing the right sub-tree of the child
      Node<T> rightChildOfChild = child.rightChild;
      // setting the right child of the child to null, it gets a new assignment later
      child.rightChild = null;
      // setting left child of the parent to null, it gets a new assignment later
      parent.leftChild = null;
      // if the parent has a parent, then assigning the child as the appropriate left/right child
      if (grandParent != null) {
        if (parent.isLeftChild()) {
          grandParent.leftChild = child;
        } else {
          grandParent.rightChild = child;
        }
      } else { // if the parent is the root of the BST, then updating the root to be the child
        root = child;
      }
      // updating the right child of the child node
      child.rightChild = parent;
      // updating the left child of what was the parent node
      parent.leftChild = rightChildOfChild;
      if (rightChildOfChild != null) {
        rightChildOfChild.parent = parent;
      }
      child.parent = parent.parent;
      parent.parent = child;
    }

    else if (parent != null && parent.rightChild == child) {
      // child is the right-subtree of parent, and will perform left rotation
      // finding the grandparent of the child
      Node<T> grandParent = parent.parent;
      // storing the left sub-tree of the child
      Node<T> leftChildOfChild = child.leftChild;
      // setting the left child of the child to null, it gets a new assignment later
      child.leftChild = null;
      // setting right child of the parent to null, it gets a new assignment later
      parent.rightChild = null;
      // if the parent has a parent, then assigning the child as the appropriate left/right child
      if (grandParent != null) {
        if (parent.isLeftChild()) {
          grandParent.leftChild = child;
        } else {
          grandParent.rightChild = child;
        }
      } else { // if the parent is the root, update the root of the bst
        root = child;
      }
      // setting the left child of the new root of the localized tree
      child.leftChild = parent;
      // updating the right child of what was the parent node
      parent.rightChild = leftChildOfChild;
      if (leftChildOfChild != null) {
        leftChildOfChild.parent = parent;
      }
      child.parent = parent.parent;
      parent.parent = child;
    }
    // if the two nodes are not directly related in a way that the rotation is possible
    else {
      // throwing an illegal argument exception
      throw new IllegalArgumentException(
          "Given child & parent references aren't related in a way that rotation is possible");
    }
  }

  /**
   * Method to resolve red child under red parent red black tree property violations that are
   * introduced by inserting new nodes into a red black tree. While doing so, all other red black
   * tree properties are also preserved.
   * 
   * @param newlyAdded
   */
  private void enforceRBTreePropertiesAfterInsert(Node<T> newlyAdded) {

    // case 4: adding the root for the first time or the parent is black
    if (newlyAdded.parent == null || newlyAdded.parent.isBlack) {
      return;
    }

    // initializing various variables with the values of the parent and grandparent of the node that
    // is to be added
    Node<T> parentOfAdded = newlyAdded.parent;
    Node<T> grandParent = parentOfAdded.parent;
    Node<T> auntNode = null;

    // finding the aunt/uncle node of the child
    if (parentOfAdded.isLeftChild()) {
      auntNode = grandParent.rightChild;
    } else {
      auntNode = grandParent.leftChild;
    }
    // case 1: parent's sibling(aunt) is red
    if (auntNode != null && !auntNode.isBlack) {
      // recoloring nodes
      // setting parent and aunt to be black
      parentOfAdded.isBlack = true;
      auntNode.isBlack = true;
      // setting grandparent/ root of subtree to red
      grandParent.isBlack = false;
      if (grandParent == root) {
        // if the grandParent is the root, setting is value to black and exiting the function
        grandParent.isBlack = true;
        return;
      }
      // recursively calling enforceRBTreePropertiesAfterInsert to solve any possible conflicts that
      // might have been introduced
      enforceRBTreePropertiesAfterInsert(grandParent);
    }

    // case 2 or case 3: when parent's sibling is black
    else {
      // case 2: parent's sibling is black and is on the opposite side as the new node
      if ((newlyAdded.isLeftChild() && parentOfAdded.isLeftChild())
          || (!newlyAdded.isLeftChild() && !parentOfAdded.isLeftChild())) {
        // rotating the parent and grandparent
        rotate(parentOfAdded, grandParent);
        // swapping the colors of the grandparent and parent to preserve black height
        grandParent.isBlack = false;
        parentOfAdded.isBlack = true;
      } else { // case 3: parent's sibling is black and is on the same side as the new node
        // rotating the node to be added and its parent 
        rotate(newlyAdded, parentOfAdded);
        // bringing it to case 3 as described above
        rotate(newlyAdded, grandParent);
        // swapping the colors to preserve black height
        grandParent.isBlack = false;
        newlyAdded.isBlack = true;
      }
    }
  }
}

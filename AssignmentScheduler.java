import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;

// --== CS400 File Header Information ==--
// Name: Ayushi Mishra
// Email: mishra37@wisc.edu
// Team: LB
// TA: Divyanshu Saxena
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
/**
 * 
 * @author ayushi
 *
 *         This class implements Red Black Tree data structure, which stores objects of Assignment
 *         class as nodes
 * @param <T>
 */
public class AssignmentScheduler extends RedBlackTree<Assignment> {

  private int size;
  private boolean isDeleted = false;

  /**
   * method checks if a particular key is present in the RBT
   * 
   * @return true if key if present in RBT
   */
  public boolean contains(Assignment o) {
    return containsHelper(root, o);
  }

  /**
   * Helper for contains method
   * 
   * @param node - root
   * @param key  - key to lookup
   * @return true if the node can be found with specified key, false if marked deleted
   */
  private boolean containsHelper(Node<Assignment> node, Assignment o) {
    // if root node is null, we return false
    if (node == null)
      return false;
    // uses compareTo method to store resulting positive or negative value in compare
    int compare = node.data.compareTo(o);

    if (compare > 0)
      return containsHelper(node.leftChild, o);

    else if (compare < 0)
      return containsHelper(node.rightChild, o);

    else if (node.isDeleted)
      return false;

    return true;
  }

  /**
   * Get Value for a given key
   * 
   * @return value for the key
   */
  public Node<Assignment> get(Assignment key) {
    return getHelper(root, key);
  }

  /**
   * Helper for get method
   * 
   * @param key  key to lookup
   * @param node root
   * @return node for the key
   */
  private Node<Assignment> getHelper(Node<Assignment> node, Assignment key) {
    // if root node is null, we return false
    if (node == null)
      return null;

    // uses compareTo method to store resulting positive or negative value in compare
    int compare = root.data.compareTo(key);

    // if the value stored at root is greater then the key to lookup
    // we recurse in the left subtree until we find the node
    if (compare > 0)
      getHelper(node.leftChild, key);
    // else check recursively in the right-subtree
    else if (compare < 0)
      getHelper(node.rightChild, key);
    // if the node found is marked as deleted the method will return null
    else if (node.isDeleted)
      return null;
    // returns the node for the key
    return node;
  }

  /**
   * InOrder traversal
   * 
   * @return inOrder linked-list keys
   */
  public LinkedList<Node<Assignment>> getInOrderTraversal() {
    LinkedList<Node<Assignment>> keys = new LinkedList<>();
    inOrder(root, keys);
    return keys;
  }

  /**
   * helper for inOrder
   * 
   * @param node root of tree
   * @param keys list of keys
   */
  private void inOrder(Node<Assignment> node, LinkedList<Node<Assignment>> keys) {
    if (node != null && !node.isDeleted) {
      inOrder(node.leftChild, keys);
      keys.add(node);
      inOrder(node.rightChild, keys);
    }
  }

  /**
   * This method performs an in-order traversal of the tree. The string representations of each data
   * value within this tree are assembled into a comma separated string within brackets (similar to
   * many implementations of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
   * 
   * @return string containing the values of this tree in order
   * 
   */
  @Override
  public String toString() {
    return root.toString();
  }

  /**
   * This method adds a new element to the tree by calling insert function. Does not allow user
   * cannot insert a value which has been set to delete
   * 
   * @param node
   * @param value
   */
  public void add(Assignment value) {
    // calls insert only if RBT does not already contains the value to be added
    // to avoid duplicates
    if (!this.contains(value)) {
      this.insert(value);
    }

    // if the element exists in the tree but has been marked deleted
    if (!this.contains(value)) {
      if (this.isDeleted) {
        this.insert(value);
      }
    }
    // increments size on adding a new element
    size++;
  }

  /**
   * This method returns the number of key-value pair stored in the red black tree
   * 
   * @return returns the size of tree
   */
  @SuppressWarnings("unused")
  private int Size() {
    return size;
  }

  // Node<T> - Assignment
  // Assignment extends Node<Date>
  // T - Date
  /**
   * This method deletes value of the element by marking it deleted, but not actually removing the
   * node.
   * 
   */
  public Node<Assignment> deleteNode(Node<Assignment> node, Assignment value)
      throws NoSuchElementException {
    // returns if the node to be deleted is null
    if (node == null) {
      throw new NoSuchElementException("Element cannot be found in the tree.");
    }
    // checks if the element is already deleted
    if (node.isDeleted) {
      throw new NoSuchElementException("Element has already been deleted from the tree.");
    }
    // uses compareTo method to store resulting positive or negative value in compare
    int compare = node.data.compareTo(value);

    // if the data of node from where the operation is to be started is greater than
    // the value to be deleted, we recursively go down the left-subtree until we find the
    // value to be deleted
    if (compare > 0) {
      return deleteNode(node.leftChild, value);
    }
    // else we traverse down the right subtree until we find the value
    else if (compare < 0) {
      return deleteNode(node.rightChild, value);
      // as soon as the value matches the data to be deleted, i.e. compare == 0,
      // we mark the node as isDeleted
    } else {
      node.isDeleted = true;
    }
    // returns the node marked deleted
    return node;
  }

  /**
   * method displays the Due date, Assignment name, Course Name, points of the product
   * 
   * @param returned object of class Assignment with details given by the user
   */
  public static void displayGet(Assignment returned) {
    System.out.println("Due Date: " + returned.getDate());
    System.out.println("Assignment Name: " + returned.getAssignName());
    System.out.println("Course Name: " + returned.getCourseName());
    System.out.println("Points: " + returned.getPoints());
  }

  public static void main(String[] args) {
    AssignmentGenerator fileGen = new AssignmentGenerator();
    fileGen.generateFile();
  }
}

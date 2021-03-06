import java.util.LinkedList;
import java.util.NoSuchElementException;

// --== CS400 File Header Information ==--
// Name: Ayushi Mishra
// Email: mishra37@wisc.edu
// Team: LB
// Role: Backend Developer
// TA: Divyanshu Saxena
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
/**
 * 
 * @author ayushi
 *
 * This class implements Red Black Tree data structure, which stores objects of Assignment
 * class as nodes
 * @param <T>
 */
public class AssignmentScheduler extends RedBlackTree<Assignment> {

  private int size;

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
    if (node == null) {
      return false;
    }
    if (node.isDeleted) {
      return false;
    }
    // uses compareTo method to store resulting positive or negative value in compare
    int compare = node.data.compareTo(o);

    if (compare > 0) {
      return containsHelper(node.leftChild, o);
    } else if (compare < 0) {
      return containsHelper(node.rightChild, o);
    }
    return true;
  }

  /**
   * Get Value for a given key
   * 
   * @return value for the key
   */
  public Assignment get(Date date) {
    return getHelper(root, date);
  }

  /**
   * Helper for get method
   * 
   * @param key  key to lookup
   * @param node root
   * @return node for the key
   */
  private Assignment getHelper(Node<Assignment> node, Date date) {
    // if root node is null, we return null
    if (node == null)
      return null;

    // uses compareTo method to store resulting positive or negative value in compare
    int compare = node.data.getDate().compareTo(date);

    // if the value stored at root is greater then the key to lookup
    // we recurse in the left subtree until we find the node
    if (compare > 0)
      getHelper(node.leftChild, date);
    // else check recursively in the right-subtree
    else if (compare < 0)
      getHelper(node.rightChild, date);
    // when found returns Assignment
    else if (compare == 0)
      return node.data;
    // if the node found is marked as deleted the method will return null
    else if (node.isDeleted)
      return null;
    // return null when not found
    return null;
  }

  /**
   * InOrder traversal
   * 
   * @return inOrder linked-list keys
   */
  public String getInOrderTraversal() {
    String n = toStringHelper(root);
    System.out.println(n);
    return n;
  }

   /**
   * helper for inOrder
   * 
   * @param current - root of tree
   * 
   */
  public String toStringHelper(Node<Assignment> current) {
    String toReturn = "";
    if (current == null) {
      return toReturn;
    } else {
        if(!current.isDeleted) {
      // in order traversal
      toReturn = toReturn + toStringHelper(current.leftChild);
      toReturn = toReturn + current.data.toString() + "\n";
      toReturn = toReturn + toStringHelper(current.rightChild);
      }
    }
    return toReturn;
  }

  /**
   * 
   * @param value to be added
   * @return true if is successfully adds the element
   */
  public boolean add(Assignment value) {
    // calls insert only if RBT does not already contains the value to be added
    // to avoid duplicates
    if (!this.contains(value)) {
      this.insert(value);
      // increments size on adding a new element
      size++;
      return true;
    }
    // increments size on adding a new element
    return false;
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

  /**
   * This method deletes value of the element by marking it deleted, but not actually removing the
   * node.
   * 
   */
  public Assignment deleteNode(Date date) {
    return deleteNodeHelper(root, date);
  }

  /**
   * Delete Node Helper
   * 
   */
  public Assignment deleteNodeHelper(Node<Assignment> node, Date date)
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
    int compare = node.data.getDate().compareTo(date);

    // if the data of node from where the operation is to be started is greater than
    // the value to be deleted, we recursively go down the left-subtree until we find the
    // value to be deleted
    if (compare > 0) {
      return deleteNodeHelper(node.leftChild, date);
    }
    // else we traverse down the right subtree until we find the value
    else if (compare < 0) {
      return deleteNodeHelper(node.rightChild, date);
      // as soon as the value matches the data to be deleted, i.e. compare == 0,
      // we mark the node as isDeleted
    } else {
      node.isDeleted = true;
    }
    // returns the node marked deleted
    return node.data;
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

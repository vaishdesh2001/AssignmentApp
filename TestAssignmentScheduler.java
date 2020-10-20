
// --== CS400 File Header Information ==--
// Name: Sneha Tripathi
// Email: stripathi3@wisc.edu
// Team: LB
// Role: Test Engineer 1
// TA: Divyanshu Saxena
// Lecturer: Florian Heimerl
// Notes to Grader: -
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

/**
 * this class tests the AssignmentScheduler as well as RedBlackTree
 * 
 * @author sneha
 *
 */

public class TestAssignmentScheduler {

	AssignmentScheduler planner = new AssignmentScheduler();

	/**
	 * test 1: This test for the contains and delete operations in
	 * AssignemntSceduler 1) 5 assignments are added, each of them is checked with
	 * contains 2) 1 non added assignment is checked with contains 3) assignments
	 * added in first step are deleted and then each of them is checked for contains
	 * 4) an assignment which is not in there is deleted 5) an assignment which is
	 * already deleted is tried to be deleted
	 * 
	 * @fail : if any of the node that should be in the planner is not in it or if
	 *       any of the deleted node is in the planner
	 */
	@Test
	public void TestContainsDelete() {
		Assignment a1 = new Assignment(new Date(20, 10, 2020), "quiz 1", "Affro 101", 12);
		Assignment a2 = new Assignment(new Date(25, 10, 2020), "quiz 2", "Affro 101", 15);
		Assignment a3 = new Assignment(new Date(30, 10, 2020), "quiz 3", "Affro 101", 20);
		Assignment a4 = new Assignment(new Date(01, 11, 2020), "exam 1", "ECE 252", 50);
		Assignment a5 = new Assignment(new Date(12, 11, 2020), "exam 2", "Comp sci 400", 30);
		Assignment[] arr = { a1, a2, a3, a4, a5 };
		// adding all the assignments
		for (int i = 0; i < arr.length; i++) {
			planner.add(arr[i]);
		}
		// testing if all the assignments are contained properly
		for (int i = 0; i < arr.length; i++) {
			if (!planner.contains(arr[i]))
				fail("Error with contains");
		}
		// testing for a an assignment not stored
		Assignment a6 = new Assignment(new Date(19, 11, 2020), "exam 3", "Comp sci 400", 30);
		if (planner.contains(a6))
			fail("contains returned true for assignment not available");
		// deleting all the assignments
		for (int i = 0; i < arr.length; i++) {
			planner.deleteNode(arr[i].getDate());
		}
		// none of the deleted assignment should now be in the planner
		for (int i = 0; i < arr.length; i++) {
			if (planner.contains(arr[i]))
				fail("Error with delete");
		}
		// trying to delete a node which is not present
		Assignment a7 = new Assignment(new Date(4, 11, 2020), "exam 4", "astron 400", 30);
		try {
			planner.deleteNode(a7.getDate());
		} catch (NoSuchElementException e) {
			if (!e.getMessage().equals("Element cannot be found in the tree."))
				fail("wrong condition for exception");
		}
		// trying to delete a node which is already deleted
		try {
			planner.deleteNode(arr[0].getDate());
		} catch (NoSuchElementException e) {
			if (!e.getMessage().equals("Element has already been deleted from the tree."))
				fail("wrong condition for exception");
		}
	}

	/**
	 * test 2: This test checks the get when dates are passed 1) 5 assignments added
	 * then all are called by get by both overloaded methods 2) trying to get an
	 * assignment which was never added 3) trying to get an assignment after
	 * deleting them
	 * 
	 * @fail if any of the tests fail to return the required node
	 */
	@Test
	public void TestGet() {
		Assignment a1 = new Assignment(new Date(20, 10, 2020), "quiz 1", "Affro 101", 12);
		Assignment a2 = new Assignment(new Date(25, 10, 2020), "quiz 2", "Affro 101", 15);
		Assignment a3 = new Assignment(new Date(30, 10, 2020), "quiz 3", "Affro 101", 20);
		Assignment a4 = new Assignment(new Date(01, 11, 2020), "exam 1", "ECE 252", 50);
		Assignment a5 = new Assignment(new Date(12, 11, 2020), "exam 2", "Comp sci 400", 30);
		Assignment[] arr = { a1, a2, a3, a4, a5 };
		// adding all the assignments
		for (int i = 0; i < arr.length; i++) {
			planner.add(arr[i]);
		}
		// getting all the assignments by passing assignments
		for (int i = 0; i < arr.length; i++) {
			Assignment temp = planner.get(arr[i]);
			if (!temp.equals(arr[i])) {
				fail("Assignment :" + arr[i].toString() + " was not returned");
			}
		}
		// passing all assignment by name and date
		for (int i = 0; i < arr.length; i++) {
			Assignment temp = planner.get(arr[i].getDate(), arr[i].getCourseName());
			if (!temp.equals(arr[i])) {
				fail("Assignment :" + arr[i].toString() + " was not returned");
			}
		}
		// passing a node which was never added
		Assignment a8 = new Assignment(new Date(3, 11, 2020), "exam 4", "astron 400", 30);
		Assignment temp2 = planner.get(a8);
		Assignment temp3 = planner.get(a8.getDate(), a8.getCourseName());
		if (temp2 != null && temp3 != null) {
			fail("Assignment :" + a8.toString() + " was not returned");
		}
		// passing the assignment after deleting them
		for (int i = 0; i < arr.length; i++) {
			planner.deleteNode(arr[i].getDate());
		}
		for (int i = 0; i < arr.length; i++) {
			Assignment temp1 = planner.get(arr[i]);
			Assignment temp = planner.get(arr[i].getDate(), arr[i].getCourseName());
			if (temp1 != null && temp != null) {
				fail("Assignment :" + arr[i].toString() + " was not returned");
			}
		}
	}

	/**
	 * test 3: This test insert assignment which do not cause red property violation
	 * to ensure that no additional changes are made if the properties are not
	 * violated
	 * 
	 * @fail When the expected level order does not match with the one returned from
	 *       toString()
	 */
	@Test
	public void ParentBlack() {
		Assignment a1 = new Assignment(new Date(5, 11, 2020), "quiz 1", "Affro 101", 12);
		Assignment a2 = new Assignment(new Date(2, 11, 2020), "quiz 2", "Affro 101", 15);
		Assignment a3 = new Assignment(new Date(7, 11, 2020), "quiz 3", "Affro 101", 20);
		Assignment a4 = new Assignment(new Date(1, 11, 2020), "exam 1", "ECE 252", 50);
		Assignment a5 = new Assignment(new Date(6, 11, 2020), "exam 2", "Comp sci 400", 30);
		Assignment a6 = new Assignment(new Date(8, 11, 2020), "exam 3", "astron 400", 30);
		Assignment a7 = new Assignment(new Date(4, 11, 2020), "exam 4", "astron 400", 30);
		Assignment[] arr = { a1, a2, a3, a4, a5, a6, a7 };
		for (int i = 0; i < arr.length; i++) {
			planner.add(arr[i]);
		}
		// adding a red child to black parent no violation
		Assignment a8 = new Assignment(new Date(3, 11, 2020), "exam 4", "astron 400", 30);
		planner.add(a8);
		String output1 = planner.toString();
		String expected1 = "[quiz 1 for Affro 101 due on 11/5/2020 worth 12 points, "
				+ "quiz 2 for Affro 101 due on 11/2/2020 worth 15 points,"
				+ " quiz 3 for Affro 101 due on 11/7/2020 worth 20 points,"
				+ " exam 1 for ECE 252 due on 11/1/2020 worth 50 points,"
				+ " exam 4 for astron 400 due on 11/4/2020 worth 30 points,"
				+ " exam 2 for Comp sci 400 due on 11/6/2020 worth 30 points,"
				+ " exam 3 for astron 400 due on 11/8/2020 worth 30 points,"
				+ " exam 4 for astron 400 due on 11/3/2020 worth 30 points]";

		if (!output1.equals(expected1)) {
			fail("output " + output1);
		}
	}

	/**
	 * test 4: Red property violation occurs red child to a red parent, black uncle
	 * a) a node is added which is inserted as left child of the parent, the parent
	 * itself is a left child. Parent's sibling is black. One right rotation should
	 * occur. b) a node is added which is inserted as right child of the parent, the
	 * parent itself is a right child. Parent's sibling is black. One left rotation
	 * should occur.
	 * 
	 * @fail if any of the two sub tests expected traversal is different from the
	 *       one returned by toString();
	 */
	@Test
	public void ParentRedSiblingBlackOneRotation() {
		Assignment a1 = new Assignment(new Date(8, 11, 2020), "quiz 1", "Affro 101", 12);
		Assignment a2 = new Assignment(new Date(5, 11, 2020), "quiz 2", "Affro 101", 15);
		Assignment a3 = new Assignment(new Date(18, 11, 2020), "quiz 3", "Affro 101", 20);
		Assignment a4 = new Assignment(new Date(15, 11, 2020), "exam 1", "ECE 252", 50);
		Assignment[] arr = { a1, a2, a3, a4 };
		for (int i = 0; i < arr.length; i++) {
			planner.add(arr[i]);
		}
		// inserting node as left child, whose parent is also a left child with black
		// sibling (null)
		// right rotate
		Assignment a5 = new Assignment(new Date(14, 11, 2020), "exam 1", "ECE 252", 50);
		planner.add(a5);
		String output1 = planner.toString();
		String expected1 = "[quiz 1 for Affro 101 due on 11/8/2020 worth 12 points,"
				+ " quiz 2 for Affro 101 due on 11/5/2020 worth 15 points,"
				+ " exam 1 for ECE 252 due on 11/15/2020 worth 50 points,"
				+ " exam 1 for ECE 252 due on 11/14/2020 worth 50 points,"
				+ " quiz 3 for Affro 101 due on 11/18/2020 worth 20 points]";
		if (!output1.equals(expected1)) {
			fail("output " + output1);
		}
		// inserting node as right child, whose parent is also a right child with black
		// sibling (null)
		// left rotate
		Assignment a6 = new Assignment(new Date(6, 11, 2020), "exam 3", "astron 400", 30);
		Assignment a7 = new Assignment(new Date(7, 11, 2020), "exam 4", "astron 400", 30);
		planner.add(a6);
		planner.add(a7);
		String output2 = planner.toString();
		String expected2 = "[quiz 1 for Affro 101 due on 11/8/2020 worth 12 points,"
				+ " exam 3 for astron 400 due on 11/6/2020 worth 30 points,"
				+ " exam 1 for ECE 252 due on 11/15/2020 worth 50 points,"
				+ " quiz 2 for Affro 101 due on 11/5/2020 worth 15 points,"
				+ " exam 4 for astron 400 due on 11/7/2020 worth 30 points,"
				+ " exam 1 for ECE 252 due on 11/14/2020 worth 50 points, "
				+ "quiz 3 for Affro 101 due on 11/18/2020 worth 20 points]";
		if (!output2.equals(expected2)) {
			fail("output " + output2);
		}
	}

	/**
	 * test 5: Red property violation occurs red child to a red parent, black uncle
	 * a) a node is added which is inserted as left child of the parent, the parent
	 * itself is a right child. Parent's sibling is black. One left-right should
	 * occur. b) a node is added which is inserted as right child of the parent, the
	 * parent itself is a left child. Parent's sibling is black. One right - left
	 * rotation should occur.
	 * 
	 * @fail if any of the two sub tests expected traversal is different from the
	 *       one returned by toString();
	 */
	@Test
	public void ParentRedSiblingBlackTwoRotation() {
		Assignment a1 = new Assignment(new Date(8, 11, 2020), "quiz 1", "Affro 101", 12);
		Assignment a2 = new Assignment(new Date(5, 11, 2020), "quiz 2", "Affro 101", 15);
		Assignment a3 = new Assignment(new Date(18, 11, 2020), "quiz 3", "Affro 101", 20);
		Assignment a4 = new Assignment(new Date(15, 11, 2020), "exam 1", "ECE 252", 50);
		Assignment[] arr = { a1, a2, a3, a4 };
		for (int i = 0; i < arr.length; i++) {
			planner.add(arr[i]);
		}
		// inserting node as right child, whose parent is a left child with black
		// sibling (null) left-right rotation
		Assignment a5 = new Assignment(new Date(17, 11, 2020), "exam 1", "ECE 252", 50);
		planner.add(a5);
		String output1 = planner.toString();
		String expected1 = "[quiz 1 for Affro 101 due on 11/8/2020 worth 12 points,"
				+ " quiz 2 for Affro 101 due on 11/5/2020 worth 15 points,"
				+ " exam 1 for ECE 252 due on 11/17/2020 worth 50 points, "
				+ "exam 1 for ECE 252 due on 11/15/2020 worth 50 points,"
				+ " quiz 3 for Affro 101 due on 11/18/2020 worth 20 points]";
		if (!output1.equals(expected1)) {
			fail("output " + output1);
		}
		// inserting node as left child, whose parent is a right child with black
		// sibling (null) right-left rotation
		Assignment a6 = new Assignment(new Date(7, 11, 2020), "exam 3", "astron 400", 30);
		Assignment a7 = new Assignment(new Date(6, 11, 2020), "exam 4", "astron 400", 30);
		planner.add(a6);
		planner.add(a7);
		String output2 = planner.toString();
		String expected2 = "[quiz 1 for Affro 101 due on 11/8/2020 worth 12 points,"
				+ " exam 4 for astron 400 due on 11/6/2020 worth 30 points,"
				+ " exam 1 for ECE 252 due on 11/17/2020 worth 50 points, "
				+ "quiz 2 for Affro 101 due on 11/5/2020 worth 15 points, "
				+ "exam 3 for astron 400 due on 11/7/2020 worth 30 points, "
				+ "exam 1 for ECE 252 due on 11/15/2020 worth 50 points, "
				+ "quiz 3 for Affro 101 due on 11/18/2020 worth 20 points]";
		if (!output2.equals(expected2)) {
			fail("output " + output2);
		}
	}

	/**
	 * test 6: Red property violation occurs red child to a red parent, RED uncle a)
	 * a node is added which is inserted as left child of the parent, the parent
	 * itself is a left child. Parent's sibling is RED. Only re-coloring should
	 * occur, No cascading fix is needed. b) a node is added which is inserted as
	 * left child of the parent, the parent itself is a right child. Parent's
	 * sibling is RED. Cascading fixes should occur
	 * 
	 * @fail if any of the two sub tests expected traversal is different from the
	 *       one returned by toString();
	 */
	@Test
	public void ParentRedSiblingRed() {
		Assignment a1 = new Assignment(new Date(14, 11, 2020), "quiz 1", "Affro 101", 12);
		Assignment a2 = new Assignment(new Date(7, 11, 2020), "quiz 2", "Affro 101", 15);
		Assignment a3 = new Assignment(new Date(20, 11, 2020), "quiz 3", "Affro 101", 20);
		Assignment[] arr = { a1, a2, a3 };
		for (int i = 0; i < arr.length; i++) {
			planner.add(arr[i]);
		}
		// inserting 1 as left child, whose parent is left child with red sibling
		// no cascading fix needed
		Assignment a4 = new Assignment(new Date(1, 11, 2020), "exam 1", "ECE 252", 50);
		planner.add(a4);
		String output1 = planner.toString();
		String expected1 = "[quiz 1 for Affro 101 due on 11/14/2020 worth 12 points,"
				+ " quiz 2 for Affro 101 due on 11/7/2020 worth 15 points,"
				+ " quiz 3 for Affro 101 due on 11/20/2020 worth 20 points,"
				+ " exam 1 for ECE 252 due on 11/1/2020 worth 50 points]";
		if (!output1.equals(expected1)) {
			fail("output " + output1);
		}

		Assignment a5 = new Assignment(new Date(11, 11, 2020), "exam 1", "ECE 252", 50);
		Assignment a6 = new Assignment(new Date(18, 11, 2020), "exam 3", "astron 400", 30);
		Assignment a7 = new Assignment(new Date(25, 11, 2020), "exam 4", "astron 400", 30);
		Assignment a8 = new Assignment(new Date(23, 11, 2020), "survey 1", "asian 255", 30);
		Assignment a9 = new Assignment(new Date(29, 11, 2020), "survey 2", "asian 255", 30);
		Assignment[] arr1 = { a5, a6, a7, a8, a9 };
		for (int i = 0; i < arr1.length; i++) {
			planner.add(arr1[i]);
		}
		// inserting 27 as left child, whose parent is right child with red sibling
		// causing cascading fixes
		Assignment a10 = new Assignment(new Date(27, 11, 2020), "story telling", "asian 255", 15);
		planner.add(a10);
		String output2 = planner.toString();
		String expected2 = "[quiz 3 for Affro 101 due on 11/20/2020 worth 20 points,"
				+ " quiz 1 for Affro 101 due on 11/14/2020 worth 12 points,"
				+ " exam 4 for astron 400 due on 11/25/2020 worth 30 points, "
				+ "quiz 2 for Affro 101 due on 11/7/2020 worth 15 points, "
				+ "exam 3 for astron 400 due on 11/18/2020 worth 30 points, "
				+ "survey 1 for asian 255 due on 11/23/2020 worth 30 points, "
				+ "survey 2 for asian 255 due on 11/29/2020 worth 30 points, "
				+ "exam 1 for ECE 252 due on 11/1/2020 worth 50 points, "
				+ "exam 1 for ECE 252 due on 11/11/2020 worth 50 points, "
				+ "story telling for asian 255 due on 11/27/2020 worth 15 points]";
		if (!output2.equals(expected2)) {
			fail("output " + output2);
		}
	}

	/**
	 * test 7: testing add 1) valid assignment added should return true; 2) trying
	 * to add same assignment checking similarities 3) trying to add null
	 * 
	 * @fail if any of the conditions don't return their expected output
	 */
	@Test
	public void TestAdd() {
		Assignment a1 = new Assignment(new Date(20, 10, 2020), "quiz 1", "Affro 101", 12);
		Assignment a2 = new Assignment(new Date(25, 10, 2020), "quiz 2", "Affro 101", 15);
		Assignment a3 = new Assignment(new Date(30, 10, 2020), "quiz 3", "Affro 101", 20);
		Assignment a4 = new Assignment(new Date(01, 11, 2020), "exam 1", "ECE 252", 50);
		Assignment a5 = new Assignment(new Date(12, 11, 2020), "exam 2", "Comp sci 400", 30);
		Assignment[] arr = { a1, a2, a3, a4, a5 };
		// adding all the assignments and checking size
		for (int i = 0; i < arr.length; i++) {
			if (!planner.add(arr[i]))
				fail("failed to add a valid assignment");
		}
		if (planner.getSize() != 5) {
			fail("error in size");
		}
		// adding the duplicates
		for (int i = 0; i < arr.length; i++) {
			try {
				planner.add(arr[i]);
			} catch (IllegalArgumentException E) {
				if (!E.getMessage().equals("This RedBlackTree already contains that value."))
					fail("error with output message");
			}
		}
		// size should still remain same
		if (planner.getSize() != 5) {
			fail("error in size");
		}
		// adding null
		try {
			planner.add(null);
			fail("no null pointer thrown");
		} catch (NullPointerException E) {
			if (E.getMessage() != null)
				fail("Exception thrown somewhere else");
		} catch (Exception E) {
			fail("any other exeception thrown");
		}
		// size should still remain same
		if (planner.getSize() != 5) {
			fail("error in size");
		}
	}

}

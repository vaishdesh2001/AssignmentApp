
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

public class TestAssignmentScheduler {

	AssignmentScheduler planner = new AssignmentScheduler();
	RedBlackTree<Integer> RBT = new RedBlackTree<Integer>();

	/**
	 * test 1: This test for the contains and delete operations in
	 * AssignemntSceduler 1) 5 assignments are added, each of them is checked with
	 * contains 2) 1 non added assignment is checked with contains 3) assignments
	 * added in first step are deleted and then each of them is checked for
	 * contains,
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
			planner.deleteNode(new RedBlackTree.Node<Assignment>(arr[i]), arr[i]);
		}
		// none of the deleted assignment should now be in the planner
		System.out.println(planner.contains(a1));
		for (int i = 0; i < arr.length; i++) {
			if (planner.contains(arr[i]))
				fail("Error with delete");
		}

	}

	/**
	 * test 2: This test insert assignment which do not cause red property violation
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
		String expected1 = "[Affro 101 quiz 1 11/5/2020 12, Affro 101 quiz 2 11/2/2020 15, "
				+ "Affro 101 quiz 3 11/7/2020 20, ECE 252 exam 1 11/1/2020 50, astron 400 exam 4 11/4/2020 30,"
				+ " Comp sci 400 exam 2 11/6/2020 30, astron 400 exam 3 11/8/2020 30, astron 400 exam 4 11/3/2020 30]";
		if (!output1.equals(expected1)) {
			fail("output " + output1);
		}
	}

	/**
	 * test 3: Red property violation occurs red child to a red parent, black uncle
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
		String expected1 = "[Affro 101 quiz 1 11/8/2020 12, Affro 101 quiz 2 11/5/2020 15,"
				+ " ECE 252 exam 1 11/15/2020 50," + " ECE 252 exam 1 11/14/2020 50, "
				+ "Affro 101 quiz 3 11/18/2020 20]";
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
		String expected2 = "[Affro 101 quiz 1 11/8/2020 12, astron 400 exam 3 11/6/2020 30,"
				+ " ECE 252 exam 1 11/15/2020 50, " + "Affro 101 quiz 2 11/5/2020 15, "
				+ "astron 400 exam 4 11/7/2020 30, " + "ECE 252 exam 1 11/14/2020 50, "
				+ "Affro 101 quiz 3 11/18/2020 20]";
		if (!output2.equals(expected2)) {
			fail("output " + output2);
		}
	}

	/**
	 * test 4: Red property violation occurs red child to a red parent, black uncle
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
		// System.out.println(output1);
		String expected1 = "[Affro 101 quiz 1 11/8/2020 12, Affro 101 quiz 2 11/5/2020 15,"
				+ " ECE 252 exam 1 11/17/2020 50," + " ECE 252 exam 1 11/15/2020 50,"
				+ " Affro 101 quiz 3 11/18/2020 20]";
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
		String expected2 = "[Affro 101 quiz 1 11/8/2020 12, astron 400 exam 4 11/6/2020 30,"
				+ " ECE 252 exam 1 11/17/2020 50," + " Affro 101 quiz 2 11/5/2020 15,"
				+ " astron 400 exam 3 11/7/2020 30," + " ECE 252 exam 1 11/15/2020 50,"
				+ " Affro 101 quiz 3 11/18/2020 20]";
		if (!output2.equals(expected2)) {
			fail("output " + output2);
		}
	}

	/**
	 * test 5: Red property violation occurs red child to a red parent, RED uncle a)
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
		RBT.insert(1);
		String output1 = planner.toString();
		// System.out.println(output1);
		String expected1 = "[Affro 101 quiz 1 11/14/2020 12, Affro 101 quiz 2 11/7/2020 15,"
				+ " Affro 101 quiz 3 11/20/2020 20," + " ECE 252 exam 1 11/1/2020 50]";
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
		String expected2 = "[Affro 101 quiz 3 11/20/2020 20, Affro 101 quiz 1 11/14/2020 12,"
				+ " astron 400 exam 4 11/25/2020 30," + " Affro 101 quiz 2 11/7/2020 15,"
				+ " astron 400 exam 3 11/18/2020 30," + " asian 255 survey 1 11/23/2020 30,"
				+ " asian 255 survey 2 11/29/2020 30," + " ECE 252 exam 1 11/1/2020 50,"
				+ " ECE 252 exam 1 11/11/2020 50," + " asian 255 story telling 11/27/2020 15]";
		if (!output2.equals(expected2)) {
			fail("output " + output2);
		}
	}
	/**
	 * test 6: testing the in-order traversal
	 * 
	 * @fail if expected doesn't match toString 
	 */
	@Test
	public void TestInOrder()
	{
		Assignment a1 = new Assignment(new Date(8, 11, 2020), "quiz 1", "Affro 101", 12);
		Assignment a2 = new Assignment(new Date(5, 11, 2020), "quiz 2", "Affro 101", 15);
		Assignment a3 = new Assignment(new Date(18, 11, 2020), "quiz 3", "Affro 101", 20);
		Assignment a4 = new Assignment(new Date(15, 11, 2020), "exam 1", "ECE 252", 50);
		Assignment[] arr = { a1, a2, a3, a4 };
		for (int i = 0; i < arr.length; i++) {
			planner.add(arr[i]);
		}
		String str = planner.getInOrderTraversal();
		System.out.println(str);
		String expected = "Affro 101 quiz 2 11/5/2020 15\n" + 
				"Affro 101 quiz 1 11/8/2020 12\n" + 
				"ECE 252 exam 1 11/15/2020 50\n" + 
				"Affro 101 quiz 3 11/18/2020 20\n";
		if (!str.equals(expected))
			fail("in order traversal wrong");
	}

}

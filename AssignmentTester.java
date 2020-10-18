// --== CS400 File Header Information ==--
// Name: Uday Malhotra
// Email: umalhotra@wisc.edu
// Team: LB
// Role: Data Wrangler 2
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.io.File;
import java.util.NoSuchElementException;
/**
 * This is a tester for the Assignment Planner. 
 * 
 * It attempts to create a planner and test its various functionalities in different manipulated
 * tree conditions.
 * @author Uday Malhotra
 */

public class AssignmentTester {
    /**
     * This method tests the add implementation.
     * 
     * Tree is checked for size, and elements.
     * 
     * @return true if add works correctly
     */
    public static boolean testAdd(){
        //creating an initial tree
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        //checking positioning of nodes
        if((test.root.data.getDate().compareTo(new Date(7, 11, 2020))) != 0)
        return false;
        if((test.root.leftChild.data.getDate().compareTo(new Date(6, 11, 2020))) != 0)
        return false;
        if((test.root.rightChild.data.getDate().compareTo(new Date(9, 11, 2020))) != 0)
        return false;

        //manipulating tree to add two additional nodes
        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        //testing new size
        if(test.getSize()!=5)
        return false;
        if((test.root.rightChild.leftChild.data.getDate().compareTo(new Date(8, 11, 2020))) != 0)
        return false;
        if((test.root.rightChild.rightChild.data.getDate().compareTo(new Date(10, 11, 2020))) != 0)
        return false;
        //trying to add a duplicate assignment
        if(test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50)))
        return false;

        return true; 
    }
    /**
     * This method tests the functionality of the contains implementation.
     * 
     * Tree is checked for nodes that are present, missing, or deleted.
     * 
     * @return true if contains functions correctly
     */
    public static boolean testContains(){
        //creating an initial tree
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        //checking to see if it detects a contained assignment
        if(!test.contains(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50)))
        return false;
        //checking to see if it detects a missing assignment
        if(test.contains(new Assignment(new Date(25, 11, 2020), "Homework", "Linguis 101", 50)))
        return false;

        //manipulating tree to add two more assignments
        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        //checking to see if updated tree detects a contained assignment
        if(!test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        return false;

        //deleting an assignment(root)
        test.deleteNode(new Date(7, 11, 2020));
         // //checking to see if updated tree detects a contained assignment
        if(!test.contains(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10)))
        return false;
         // //checking to see if updated tree detects a deleted assignment
        if(test.contains(new Assignment(new Date(7, 11, 2020),"Quiz", "Math 240", 20)))
        return false;

        //deleting another assignment
        test.deleteNode(new Date(10, 11, 2020));
        //checking to see if deleted assignment is detected
        if(test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        return false;

        return true;
    }
    /**
     * This method tests the functionality of the Delete method.
     * 
     * Tree is checked for deleting udeleted nodes, deleting
     * an already deleted node, and deleting a node that is
     * not present.
     * 
     * @return true if delete functions correctly
     */
    public static boolean testDelete(){
        //creating an initial tree
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        //deleting a node
        test.deleteNode(new Date(9, 11, 2020));
        //checking if deleted node is present
        if(test.contains(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50)))
        return false;

        //manipulating tree by adding two more nodes
        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));
        
        //deleting a node from the manipulated tree
        test.deleteNode(new Date(8, 11, 2020));
        //checking if deleted node is present
        if(test.contains(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100)))
        return false;

        //deleting another node(root) from the manipulated tree
        test.deleteNode(new Date(7, 11, 2020));
         //checking if deleted node is present
        if(test.contains(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20)))
        return false;

        //deleting and checking another node to set up next text
        test.deleteNode(new Date(10, 11, 2020));
        if(test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        return false;

        //trying to delete an already deleted node
        try{
            test.deleteNode(new Date(10, 11, 2020));
        }
        catch(NoSuchElementException e){
        //checking if NSE is thrown correctly
            if(!(e.getMessage().contains("already been deleted")))
            return false;
        }

        //trying to delete a node that is not present in tree
        try{
            test.deleteNode(new Date(20, 11, 2020));
        }
        catch(NoSuchElementException e){
        //checking if NSE is thrown correctly
            if(!(e.getMessage().contains("cannot be")))
            return false;
        }

        return true;
    }
    /**
     * This method tests the functionality of the get implementation.
     * 
     * Tree is checked for present nodes, missing nodes, and 
     * deleted nodes.
     * 
     * @return true if get functions correctly
     */
    public static boolean testGet(){
        //creating intial tree
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        //checking if get returns correct assignment
        if((test.get(new Date(6, 11, 2020)).getDate().compareTo(
        (new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10).getDate())))!=0)
        return false;

        //deleting an assignment
        test.deleteNode(new Date(9, 11, 2020));
        //checking if get returns null when trying to get deleted assignment
        if(test.get(new Date(9, 11, 2020))!= null)
        return false;
        //checking if get returns null when trying to get an assignment that is not present
        if(test.get(new Date(25, 11, 2020))!= null)
        return false;

        //manipulating tree by adding two more assignments
        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        //checking if manipulated tree returns correct assignment when running get on a present node
        if(test.get(new Date(10, 11, 2020)).getDate().compareTo(
        (new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80).getDate()))!=0)
        return false;

        return true;

    }
    /**
     * This method tests if file of random assignments
     * is generated succesfully.
     * 
     * @return true if file is generated
     */
    public static boolean testGenerator(){
        //creating an object of the assignment generator class
        AssignmentGenerator fileGen = new AssignmentGenerator();
        //generating file
        fileGen.generateFile();
        //checking if file is generated correctly
        if(!(new File("./assignmentToDo.txt").isFile()))
        return false;
    
        return true;
    }

    /**
     * This method tests teh functionality of the In Order Traversal method.
     * 
     * Tree is checked after adding nodes, and deleting nodes.
     * 
     * @return true if traversal functionas correctly
     */
    public static boolean testTraversal(){
        //creating an initial tree
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        //declaring string with expected in order traversal result
        String expectedResult = "Quiz, Astron 123, 10, 11/6/2020"+ "\n"+ "Quiz, Math 240, 20, 11/7/2020"+"\n"+
                                "Homework, Linguis 101, 50, 11/9/2020"+"\n";
        
        //checking if traversal output matches expected result
        if(!expectedResult.equals(test.getInOrderTraversal()))
        return false;

        //manipulating tree by adding two more assignments
        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        //updating expected result for new traversal
        expectedResult = "Quiz, Astron 123, 10, 11/6/2020"+ "\n"+ "Quiz, Math 240, 20, 11/7/2020"+"\n"+ 
                         "Exam, Math 240, 100, 11/8/2020"+"\n"+ "Homework, Linguis 101, 50, 11/9/2020"+"\n"+
                         "Quiz, Psych 414, 80, 11/10/2020"+"\n";
        
        //checking if traversal output matches expected result
        if(!expectedResult.equals(test.getInOrderTraversal()))
        return false;

        //manipulating tree by deleting two nodes
        test.deleteNode(new Date(8, 11, 2020));
        test.deleteNode(new Date(7, 11, 2020));

        //updating expected result for new traversal
        expectedResult = "Quiz, Astron 123, 10, 11/6/2020"+ "\n"+ "Homework, Linguis 101, 50, 11/9/2020"+"\n"+
        "Quiz, Psych 414, 80, 11/10/2020"+"\n";

        //checking if traversal output matches expected result
        if(!expectedResult.equals(test.getInOrderTraversal()))
        return false;

        return true;
    }
    /**
     * Main method to run and display output of all tests.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(testAdd());
        System.out.println(testContains());
        System.out.println(testDelete());
        System.out.println(testGenerator());
        System.out.println(testTraversal());
        System.out.println(testGet());
    }
}

// --== CS400 File Header Information ==--
// Name: Shreyans Sakhlecha
// Email: sakhlecha@wisc.edu
// Team: LB
// Role: Data Wrangler 1
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

/**
 * Main method to execute file generation, assignment scheduler and front end.
 * @author shreyans
 */
public class AssignmentExecuter {
  public static void main(String args[]) {
      System.out.println("Loading data");
      AssignmentGen fileGen = new AssignmentGen();
      fileGen.Genfile();
      System.out.println("Data Loaded");
      AssignmentScheduler runner = new AssignmentScheduler();
      FrontEndInterface.beginPrompt(runner); 
    }
}

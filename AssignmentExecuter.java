// --== CS400 File Header Information ==--
// Name: Shreyans Sakhlecha
// Email: sakhlecha@wisc.edu
// Team: LB
// Role: Data Wrangler 1
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


public class AssignmentExecuter {
  public static void main(String args[]) {
      System.out.println("now generating file");
      AssignmentGen fileGen = new AssignmentGen();
      fileGen.Genfile();  
      AssignmentScheduler runner = new AssignmentScheduler();
      FrontEndInterface.beginPrompt(runner); 
    }
}

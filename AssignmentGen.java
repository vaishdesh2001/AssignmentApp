// --== CS400 File Header Information ==--
// Name: Shreyans Sakhlecha
// Email: sakhlecha@wisc.edu
// Team: LB
// Role: Data Wrangler 1
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.Random;
import java.io.FileWriter;
public class AssignmentGen {
 Assignment[] assign = new Assignment[500]; //creates a new array of type assignment
   String[] types = {"Monthly Quiz", "Weekly Project", "Group Project", "Homework", "Midterm", "Final",
      "Discussion", "Participation", "Research Paper", "Presentation"};
 String[] courseNames = {"ECOL 110", "FINANCE 300", "CS 400", "MATH 431", "ACC 300", "CS 350",
      "ESL 118", "CS 320", "HIST 110", "CS 240", "CS 577"};

/**
 * Method to generate and load data for the assignment scheduler project.
 * Takes the array of courseNames and types and generates random points and date 
 * to populate the assign array of assignment type.
 */
public void Genfile(){
Random points = new Random();
Random days = new Random();
int i =0;
for (int x = 0; x < courseNames.length; x++){
    for (int y =0; y < types.length; y++){ //loop for iterating through the course names and types.
        int day = days.nextInt(31)+1;
        Date dueDate = new Date(day, 10, 2020);
        int point = (points.nextInt(20)+1)*5;
        assign[i] = new Assignment(dueDate, types[y],courseNames[x], point);
        i ++;
    }
}
System.out.println("Loading assignments: " + i); // a print statement to ensure the data is loaded
try {
      FileWriter fw = new FileWriter("src/AssignementScheduler.txt");
      fw.write("Due date, Assignment type, Course, Points worth\n");
      for (int a = 0; a < i; a++) {
        fw.write(assign[a].getDate() + "," + assign[a].getAssignName() + ","
            + assign[a].getCourseName() + "," + assign[a].getPoints() + "\n"); //comma seperated file.
      }
      fw.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}




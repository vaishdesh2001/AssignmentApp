// --== CS400 File Header Information ==--
// Name: Vaishnavi Deshpande
// Email: vvdeshpande@wisc.edu
// Team: LB
// Role: Data Wrangler 2
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.lang.Comparable;

/**
 * This is the Assignment class. Objects of this class will be stored in the red-black tree
 * 
 */
public class Assignment implements Comparable<Assignment> {
  private Date dueDate;
  private String assignmentName;
  private String courseName;
  private Integer numPoints;

  public Assignment(Date dueDate, String assignmentName, String courseName, Integer numPoints) {
    this.dueDate = dueDate;
    this.assignmentName = assignmentName;
    this.courseName = courseName;
    this.numPoints = numPoints;
  }

  @Override
  public int compareTo(Assignment o) {
    // first check by the assignment date
    if (this.dueDate.compareTo(o.dueDate) > 0) {
      return 1;
    } else if (this.dueDate.compareTo(o.dueDate) < 0) {
      return -1;
    } else { // if date is the same, then compare by course name
      if (this.courseName.compareToIgnoreCase(o.courseName) > 0) {
        return 1;
      } else if (this.courseName.compareToIgnoreCase(o.courseName) > 0) {
        return -1;
      } else {
        return 0;
      }
    }
  }
  
  public Date getDate() {
    return this.dueDate;
  }
  
  public String getAssignName() {
    return this.assignmentName;
  }
  
  public String getCourseName() {
    return this.courseName;
  }
  
  public Integer getPoints() {
    return this.numPoints;
  }

}

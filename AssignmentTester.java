import java.util.NoSuchElementException;

public class AssignmentTester {

    public static boolean testInsert(){
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        if((test.root.data.getDate().compareTo(new Date(7, 11, 2020))) != 0)
        return false;
        if((test.root.leftChild.data.getDate().compareTo(new Date(6, 11, 2020))) != 0)
        return false;
        if((test.root.rightChild.data.getDate().compareTo(new Date(9, 11, 2020))) != 0)
        return false;

        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        if(test.getSize()!=5)
        return false;
        if((test.root.rightChild.leftChild.data.getDate().compareTo(new Date(8, 11, 2020))) != 0)
        return false;
        if((test.root.rightChild.rightChild.data.getDate().compareTo(new Date(10, 11, 2020))) != 0)
        return false;

        return true; 
    }

    public static boolean testContains(){
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        if(!test.contains(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50)))
        return false;
        if(test.contains(new Assignment(new Date(25, 11, 2020), "Homework", "Linguis 101", 50)))
        return false;

        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        if(!test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        return false;

        test.deleteNode(new Date(10, 11, 2020));

        if(test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        return false;

        return true;
    }

    public static boolean testDelete(){
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        test.deleteNode(new Date(9, 11, 2020));
        if(test.contains(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50)))
        return false;

        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));
        
        test.deleteNode(new Date(8, 11, 2020));
        if(test.contains(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100)))
        return false;

        test.deleteNode(new Date(7, 11, 2020));
        if(test.contains(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20)))
        return false;

        test.deleteNode(new Date(10, 11, 2020));
        if(test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        return false;

        try{
            test.deleteNode(new Date(10, 11, 2020));
        }
        catch(NoSuchElementException e){
            return true;
        }

        return false;
    }

    public static boolean testGet(){
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        if(!(test.get(new Date(6, 11, 2020)).equals
        (new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10))))
        return false;

        test.deleteNode(new Date(9, 11, 2020));
        if(test.get(new Date(9, 11, 2020))!= null)
        return false;

        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        if(!(test.get(new Date(10, 11, 2020)).equals
        (new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80))))
        return false;

        return true;

    }
    public static void main(String[] args) {
        System.out.println(testInsert());
        System.out.println(testContains());
        System.out.println(testDelete());
        System.out.println(testGet());
    }
}

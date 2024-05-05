/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* Final Project COSC 327				  					 * 
* This is a program to simulate an academic planner		     *
* @author Cadence Stewart									 * 
* @version May 10 2024										 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CourseInfo {
    private int courseNumber;
    private String courseID;
    private String courseName;

    public CourseInfo(int courseNumber, String courseID, String courseName) {
        this.courseNumber = courseNumber;
        this.courseID = courseID;
        this.courseName = courseName;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    // method to initialize course information from file
    public static SeparateChainingHashST<Integer, CourseInfo> initializeCourseInfo(String filename) {
        SeparateChainingHashST<Integer, CourseInfo> courseInfoST = new SeparateChainingHashST<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+", 3);
                if (parts.length == 3) {
                    int courseNumber = Integer.parseInt(parts[0]);
                    String courseID = parts[1];
                    String courseName = parts[2];
                    CourseInfo courseInfo = new CourseInfo(courseNumber, courseID, courseName);
                    courseInfoST.put(courseNumber, courseInfo);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (NumberFormatException e) {
            System.out.println("Invalid course number format in file: " + filename);
        }
        return courseInfoST;
    }
}
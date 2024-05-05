/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* Final Project COSC 327				  					 * 
* This is a program to simulate an academic planner.	     *
* @author Cadence Stewart									 * 
* @version May 10 2024										 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import java.util.ArrayList;
import java.util.List;

public class Student {
    private List<String> courseList;

    public Student() {
        courseList = new ArrayList<>();
    }

    // Method to check if the student has a specific course
    public boolean hasCourse(String courseID) {
        return courseList.contains(courseID);
    }

    // Method to add a course to the student's course list
    public void addCourse(String courseID) {
        courseList.add(courseID);
        System.out.println("Course " + courseID + " added to the course list.");
    }

    // Method to remove a course from the student's course list
    public void removeCourse(String courseID) {
        if (courseList.contains(courseID)) {
            courseList.remove(courseID);
            System.out.println("Course " + courseID + " removed from the course list.");
        } else {
            System.out.println("Course " + courseID + " not found in the course list.");
        }
    }

    // Method to display the student's course list
    public void displayCourseList() {
        if (courseList.isEmpty()) {
            System.out.println("No courses in the course list.");
        } else {
            for (String course : courseList) {
                System.out.println(course);
            }
        }
    }

    // Method to get the course list
    public List<String> getCourseList() {
        return courseList;
    }
}
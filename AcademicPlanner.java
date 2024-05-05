/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* Final Project COSC 327				  					 * 
* This is a program to simulate an academic planner		     *
* @author Cadence Stewart									 * 
* @version May 10 2024										 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AcademicPlanner {
    private SymbolDigraph prerequisiteDigraph; // SymbolDigraph to store prerequisite relationships between classes
    private SeparateChainingHashST<Integer, CourseInfo> courseInfoST; // SeparateChainingHashST to store class information
    private Student student;
    private Scanner input;

    public AcademicPlanner(SymbolDigraph prerequisiteDigraph) {
        this.prerequisiteDigraph = prerequisiteDigraph;
        courseInfoST = CourseInfo.initializeCourseInfo("courses.txt");
        student = new Student();
        input = new Scanner(System.in);
    }

// method to add a course to the planner
	private void addCourse(String courseID) {
		List<String> courseList = student.getCourseList();

		// check if the course is already in the course list
		for (String course : courseList) {
			if (course.equals(courseID)) {
				System.out.println("Course " + courseID + " is already in the list.");
				return;
			}
		}

		// check if the course ID exists in the courseInfoST
		if (!containsCourse(courseID)) {
			System.out.println("Course " + courseID + " does not exist.");
			return;
		}

		// Check if course can be added (no unfulfilled prerequisites)
		if (checkPrerequisites(courseID)) {
			// Add the course to the student's course list
			student.addCourse(courseID);
			System.out.println("Course " + courseID + " added to the student's class list.");
		} else {
			System.out.println("Course " + courseID + " has prerequisites and cannot be added directly.");
			displayPrerequisiteInfo(courseID);
		}
	}

// method to remove a class from the planner
	private void removeCourse(String courseID) {
		List<String> courseList = student.getCourseList();

		// check if the course is already in the course list
		for (String course : courseList) {
			if (course.equals(courseID)) {
				System.out.println("Course " + courseID + " is already in the list.");
				return;
			}
		}

		// check if the course ID exists in the courseInfoST
		if (!containsCourse(courseID)) {
			System.out.println("Course " + courseID + " does not exist.");
			return;
		}

		student.removeCourse(courseID);
		System.out.println("Course " + courseID + " removed from the student's class list.");
    }

    // Method to display the student's courselist
    private void displayCourseList() {
		System.out.println("-------------------------");
		System.out.println("| Student's course list |");
		System.out.println("-------------------------");
        student.displayCourseList();
    }

    // Method to display prerequisite info for a given class
    public void displayPrerequisiteInfo(String courseID) {
		if (!containsCourse(courseID)) {
			System.out.println("Course " + courseID + " does not exist.");
			return;
		}

		// Initialize a list to store courses that have courseID as a prerequisite
		List<String> coursesWithPrerequisite = new ArrayList<>();
	
		// Iterate over all courses in the prerequisiteDigraph
		for (int i = 0; i < prerequisiteDigraph.digraph().V(); i++) {
			// Get the course ID of the current course
			String currentCourseID = prerequisiteDigraph.nameOf(i);
	
			// Skip if it's the same courseID or if the course has no prerequisites
			if (currentCourseID.equals(courseID)) {
				continue;
			}
	
			// Check if courseID is a prerequisite for the current course
			if (checkPrerequisites(courseID, currentCourseID)) {
				coursesWithPrerequisite.add(currentCourseID);
			}
		}
	
		// Display the courses that have courseID as a prerequisite
		System.out.println("-----------------------------");
		System.out.println("| Prerequisites for " + courseID + " |");
		System.out.println("-----------------------------");
		for (String course : coursesWithPrerequisite) {
			System.out.println("- " + course);
		}
	}

// method to check if a course has prerequisites
	private boolean checkPrerequisites(String courseID) {
		int courseIndex = prerequisiteDigraph.indexOf(courseID, -1);

		// if courseIndex does not exist in the prerequisiteGraph then it has no prerequisites
		if (courseIndex == -1) {
			return false;    // no prerequisites
		}

		// retrieve the vertices pointing to the courseIndex using custom reverseAdj method in Digraph
		Iterable<Integer> prerequisiteVertices = prerequisiteDigraph.digraph().reverseAdj(courseIndex);

		for (int prerequisiteVertex : prerequisiteVertices) {
			// get courseID of the prerequisite course
			String prerequisiteCourseID = prerequisiteDigraph.nameOf(prerequisiteVertex);

			// If the prerequisiteCourseID is not in the student's course list, return false
			if (!student.hasCourse(prerequisiteCourseID)) {
				return false; // no prerequisites
			}
		}

		// All prerequisites are satisfied
		return true;
	}

// method to check if a course has prerequisites
	private boolean checkPrerequisites(String courseID, String currentCourseID) {
		// Get the index of the currentCourseID in the prerequisiteDigraph
		int courseIndex = prerequisiteDigraph.indexOf(currentCourseID, -1);

		// Check if courseID is among the prerequisites of currentCourseID
		Iterable<Integer> prerequisites = prerequisiteDigraph.digraph().adj(courseIndex);
		for (int prerequisite : prerequisites) {
			if (prerequisiteDigraph.nameOf(prerequisite).equals(courseID)) {
				return true;
			}
		}
		return false;
	}

// method to check if a course exists in the courseInfoST
	private boolean containsCourse(String courseID) {

		for (Integer key : courseInfoST.keys()) {
			// get the course information for the current key
			CourseInfo courseInfo = courseInfoST.get(key);
			// check if the courseID matches the current course's courseID
			if (courseInfo.getCourseID().equals(courseID)) {
				return true; // courseID found
			}
		}
		return false; // courseID not found
	}

    // Driver method
    public void driver() {
        String choice;

        menu();

        do {
            choice = input.nextLine();

            // Execute choice
            switch (choice) {

                case "1":
                    System.out.println("Enter course ID, ex: COSC100: ");
                    String courseIDToAdd = input.nextLine();
                    System.out.println("Debug: Input Course ID: " + courseIDToAdd);
                    addCourse(courseIDToAdd);
                    break;

                case "2":
					System.out.println("Enter course ID, ex: COSC100: ");
					String courseIDToRemove = input.nextLine();
					System.out.println("Debug: Input Course ID: " + courseIDToRemove);
					removeCourse(courseIDToRemove);
                    break;

                case "3":
                    displayCourseList();
                    break;

                case "4":
                    System.out.println("Functionality not implemented yet.");
                    break;

                case "5":
                    System.out.println("Enter course ID, ex: COSC100: ");
                    String courseIDForPrereq = input.nextLine();
                    displayPrerequisiteInfo(courseIDForPrereq);
                    break;

                case "?":
                case "menu":
                    menu();
                    break;

                case "x":
                case "exit":
                    System.out.println("Exiting...");
                    return; // Exit the program

                default:
                    System.out.println("Invalid input. Enter ? or help for help screen.");
                    break;
            }
        } while (true); // Repeat until user chooses to exit
    }

    // Help/displays choice menu
    private void menu() {
        System.out.println("Select an option:");
        System.out.println("1: Add a class");
        System.out.println("2: Remove a class");
        System.out.println("3: Display student course list");
        System.out.println("4: Display course map for a major");
        System.out.println("5: Display prerequisite info for a class");
        System.out.println("?: Print menu");
        System.out.println("x: Exit");
    }

	public static void main(String[] args) {
		SymbolDigraph symbolDigraph = new SymbolDigraph("prereqrelationships.txt", " ");
		AcademicPlanner planner = new AcademicPlanner(symbolDigraph);
		planner.driver();
	}
}
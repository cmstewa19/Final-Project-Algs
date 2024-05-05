public class Course {
    private int courseNumber;
    private String courseID;
    private String courseName;
    

    public Course(int courseNumber, String courseID, String courseName) {
        this.courseNumber = courseNumber;
        this.courseID = courseID;
        this.courseName = courseName;
    }

    // getters and setters for courseID and courseName
    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseId(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String toString() {
        return "Course{courseNumber=" + courseNumber + ", courseID='" + courseID + "', courseName='" + courseName + "'}";
    }
}
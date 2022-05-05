package com.example.myfuture.Model;

public class eduModel extends eduID {

    private String degree, university, grade, year;

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDegree() {
        System.out.println(degree);
        return degree;
    }

    public String getUniversity() {
        System.out.println(university);
        return university;
    }

    public String getGrade() {
        System.out.println(grade);
        return grade;
    }

    public String getYear() {
        System.out.println(year);
        return year;
    }
}

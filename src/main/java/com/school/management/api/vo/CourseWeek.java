package com.school.management.api.vo;

import java.util.List;
import java.util.Objects;

public class CourseWeek {

    private int state;

    private List<String> courses;

    @Override
    public String toString() {
        return "CourseWeek{" +
                "state=" + state +
                ", courses=" + courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseWeek that = (CourseWeek) o;
        return state == that.state &&
                Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {

        return Objects.hash(state, courses);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}

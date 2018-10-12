package com.school.management.api.vo;

import java.util.List;
import java.util.Objects;

public class ClassDuty {

    private int state;

    private List<String> students;

    @Override
    public String toString() {
        return "ClassDuty{" +
                "state=" + state +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassDuty classDuty = (ClassDuty) o;
        return state == classDuty.state &&
                Objects.equals(students, classDuty.students);
    }

    @Override
    public int hashCode() {

        return Objects.hash(state, students);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }
}

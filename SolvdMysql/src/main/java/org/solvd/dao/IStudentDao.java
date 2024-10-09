package org.solvd.dao;

import org.solvd.model.Student;
import java.util.List;

public interface IStudentDao extends IBaseDao<Student> {
    @Override
    Student getEntityById(int index);

    @Override
    List<Student> getEntities();

    @Override
    void insert(Student student);

    @Override
    void delete(int index);

    @Override
    void update(int index, Student student);
}
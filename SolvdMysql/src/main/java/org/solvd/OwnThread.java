package org.solvd;

import org.solvd.dao.jdbc.StudentDao;

public class OwnThread extends Thread {

    @Override
    public void run() {
        StudentDao studentDao = new StudentDao();
        studentDao.getEntities();
    }
}
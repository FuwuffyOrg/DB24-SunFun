package oop.sunfun.database.dao;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;

public class AbstractDAO {
    protected static final IDatabaseConnection DB_CONNECTION;
    static {
        DB_CONNECTION = SunFunDatabase.getDatabaseInstance();
    }
}

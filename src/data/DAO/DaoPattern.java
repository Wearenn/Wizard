package data.DAO;

import data.Data;

public class DaoPattern extends Data {

    private static Data instance;

    private DaoPattern(){}

    public static Data getInstance(){
        if(instance == null){
            instance = new DaoPattern();
        }
        return instance;
    }


}

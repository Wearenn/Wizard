package data;

public abstract class Data {

    private static Data data;

    public static Data getData(){
        return data;
    }

    public static void setData(Data data){
        Data.data = data;
    }
}

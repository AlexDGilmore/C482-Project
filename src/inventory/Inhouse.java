package inventory;

public class Inhouse extends Part {

    private int machineID;

    public Inhouse(int partID, int machineID, String name, double price, int inStock, int min, int max){
        super(partID, name, price, inStock, min, max);
        this.machineID = machineID;
    }

    public Inhouse(int machineID, String name, double price, int inStock, int min, int max){
        super(name, price, inStock, min, max);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int id) {
        machineID = id;
    }
}

package inventory;

public class Outsourced extends Part{

    private String companyName;

    public Outsourced(int partID, String companyName, String name, double price, int inStock, int min, int max){
        super(partID, name, price, inStock, min, max);
        this.companyName = companyName;
    }

    public Outsourced(String companyName, String name, double price, int inStock, int min, int max){
        super(name, price, inStock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String name){
        companyName = name;
    }
}

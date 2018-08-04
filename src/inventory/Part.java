package inventory;

public abstract class Part {

    private int partID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public Part(int partID, String name, double price, int inStock, int min, int max){
        this.partID = partID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public Part(String name, double price, int inStock, int min, int max){
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public String getName(){
        return name;
    }

    public void setName(String product){
        name = product;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int id) {
        partID = id;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double cost){
        price = cost;
    }

    public int getInStock(){
        return inStock;
    }

    public void setInStock(int amount){
        inStock = amount;
    }

    public int getMin(){
        return min;
    }

    public void setMin(int bottom){
        min = bottom;
    }

    public int getMax(){
        return max;
    }

    public void setMax(int top){
        max = top;
    }
}

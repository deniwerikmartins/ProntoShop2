package okason.com.prontoshop.model;

/**
 * Created by Valentine on 4/22/2016.
 */
public class LineItem extends Product{
    private int qauntity;

    public int getQauntity() {
        return qauntity;
    }

    public LineItem(Product product, int quantity){
        super(product);
        this.setQauntity(quantity);
    }

    public void setQauntity(int qauntity) {
        this.qauntity = qauntity;
    }

    public double getSumPrice(){
        return  getSalePrice() * qauntity;
    }
}

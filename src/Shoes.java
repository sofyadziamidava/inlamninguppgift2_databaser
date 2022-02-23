public class Shoes {
    private int id;
    private int price;
    private int brandId;
    private int size;
    private String colour;
    private int amountInStock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(int amountInStock) {
        this.amountInStock = amountInStock;
    }

    @Override
    public String toString() {
        return "Shoes{" +
                "id=" + id +
                ", price=" + price +
                ", brandId=" + brandId +
                ", size=" + size +
                ", colour='" + colour + '\'' +
                ", amountInStock=" + amountInStock +
                '}';
    }
}

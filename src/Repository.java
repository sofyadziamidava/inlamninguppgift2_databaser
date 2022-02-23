import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {
    List<Brand> allBrands = new ArrayList<>();

    public int loggIn(String name, String password) {
        List<Customer> allCustomers = new ArrayList<>();
        Connection con = null;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("src/info.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"),
                    p.getProperty("password"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer");


            while (rs.next()) {
                Customer temp = new Customer();
                temp.setId(rs.getInt("id"));
                temp.setName(rs.getString("name"));
                temp.setPassword(rs.getString("password"));
                temp.setCity(rs.getString("city"));
                allCustomers.add(temp);
            }
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        for (Customer customer : allCustomers) {
            if (customer.getName().equals(name) && customer.getPassword().equals(password)) {
                return customer.getId();
            }
        }
        return -1;
    }

    public void showAllShoeModells() {
        List<Shoes> allShoes = new ArrayList<>();
        Connection con = null;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("src/info.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"),
                    p.getProperty("password"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM shoes WHERE amountInStock != 0");
            while (rs.next()) {
                Shoes temp = new Shoes();
                temp.setId(rs.getInt("id"));
                temp.setSize(rs.getInt("size"));
                temp.setBrandId(rs.getInt("brandId"));
                temp.setPrice(rs.getInt("price"));
                temp.setColour(rs.getString("colour"));
                temp.setAmountInStock(rs.getInt("amountInStock"));
                allShoes.add(temp);
            }
            rs = stmt.executeQuery("SELECT * FROM brand");
            while(rs.next()) {
                Brand tempBrand = new Brand();
                tempBrand.setId(rs.getInt("id"));
                tempBrand.setName(rs.getString("name"));
                allBrands.add(tempBrand);
            }
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        for (Shoes shoe : allShoes) {
            if (shoe.getAmountInStock() > 0) {
                String brandName = null;
                for (Brand brand : allBrands) {
                    if (shoe.getBrandId() == brand.getId()) {
                        brandName = brand.getName();
                    }
                }
                System.out.println("Brand: " + brandName + " Colour: " + shoe.getColour()
                        + " Size: " + shoe.getSize() + " Price: " + shoe.getPrice());
            }
        }
    }

    public int shoeFound(String brand, String colour, int size) {
        List<Shoes> allShoes = new ArrayList<>();
        Connection con = null;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("src/info.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"),
                    p.getProperty("password"));


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM shoes WHERE amountInStock != 0");
            while (rs.next()) {
                Shoes temp = new Shoes();
                temp.setId(rs.getInt("id"));
                temp.setSize(rs.getInt("size"));
                temp.setBrandId(rs.getInt("brandId"));
                temp.setPrice(rs.getInt("price"));
                temp.setColour(rs.getString("colour"));
                temp.setAmountInStock(rs.getInt("amountInStock"));
                allShoes.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Shoes shoe : allShoes) {
            if(shoe.getColour().equals(colour) && shoe.getSize() == size)
                for (Brand shoeBrand : allBrands) {
                    if (shoe.getBrandId() == shoeBrand.getId() && shoeBrand.getName().equals(brand)) {
                                return shoe.getId();
                            }
                        }
        }
        return -1;
    }

    public int callAddToCart(int customerId, int orderId, int shoeId) {
        Connection con = null;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("src/info.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"),
                    p.getProperty("password"));
            CallableStatement stm =
                    con.prepareCall("CALL AddToCart(?,?,?)");
            stm.setInt(1, customerId);
            stm.setInt(2, orderId);
            stm.setInt(3, shoeId);
            stm.execute();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM order_content where id = last_insert_id()");
            while(rs.next()) {
                return rs.getInt("orderId");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void viewOrder(int currentOrderId) {
        List<Shoes> allShoes = new ArrayList<>();
        Connection con = null;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("src/info.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"),
                    p.getProperty("password"));


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM order_content");
            List<OrderContent> allOrderContent = new ArrayList<>();
            while(rs.next()){
                OrderContent temp = new OrderContent();
                temp.setId(rs.getInt("id"));
                temp.setOrderId(rs.getInt("orderId"));
                temp.setShoeId(rs.getInt("shoeId"));
                allOrderContent.add(temp);
            }
            rs = stmt.executeQuery("SELECT * FROM shoes");
            while (rs.next()) {
                Shoes temp = new Shoes();
                temp.setId(rs.getInt("id"));
                temp.setSize(rs.getInt("size"));
                temp.setBrandId(rs.getInt("brandId"));
                temp.setPrice(rs.getInt("price"));
                temp.setColour(rs.getString("colour"));
                temp.setAmountInStock(rs.getInt("amountInStock"));
                allShoes.add(temp);
            }
            for (Shoes shoe: allShoes) {
                String brandName = null;
                for (OrderContent oc : allOrderContent) {
                    if(oc.getOrderId() == currentOrderId){
                    if (oc.getShoeId() == shoe.getId()) {
                        for (Brand brand : allBrands) {
                            if (shoe.getBrandId() == brand.getId()) {
                                brandName = brand.getName();
                            }
                        }
                    }
                    }
                    if(brandName != null){
                        System.out.println("Shoe brand: " + brandName + " Colour: " + shoe.getColour()
                                + " Size: " + shoe.getSize() + " Price: " + shoe.getPrice());
                        brandName = null;
                    }
                }

            }


        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}





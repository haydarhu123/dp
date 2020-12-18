package p5;

import p4.OVChipkaart;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAOP {
    boolean save(Product product);

    boolean delete(Product product);

    boolean update(Product product);

    Product findByOVChipkaart(OVChipkaart ovchipkaart);

    List<Product> findAll() throws SQLException;
}

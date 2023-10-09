package com.data.persistency.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.data.domain.OVChipkaart;
import com.data.domain.Product;

public interface ProductDAO {
    public void save(Product product) throws SQLException	;
    public boolean update(Product product) throws SQLException;
    public boolean delete(Product product) throws SQLException;
    public Product findById(int id) throws SQLException;
    public List<Product> findByOVChipkaart(OVChipkaart ovchipkaart) throws SQLException;
    public List<Product> findAll() throws SQLException;
}

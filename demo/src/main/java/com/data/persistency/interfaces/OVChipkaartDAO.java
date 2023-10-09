package com.data.persistency.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.data.domain.OVChipkaart;
import com.data.domain.Product;
import com.data.domain.Reiziger;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ovchipkaart) throws SQLException;
    public boolean update(OVChipkaart ovchipkaart) throws SQLException;
    public boolean delete(OVChipkaart ovchipkaart) throws SQLException;
    public OVChipkaart findById(int id) throws SQLException;
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    public List<OVChipkaart> findAll() throws SQLException;
    public List<OVChipkaart> findByProduct(Product product);
    
}

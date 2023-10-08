package com.data.persistency.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.data.domain.OVChipkaart;
import com.data.domain.Reiziger;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ovchipkaart);
    public boolean update(OVChipkaart ovchipkaart);
    public boolean delete(OVChipkaart ovchipkaart);
    public OVChipkaart findById(int id) throws SQLException;
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    public List<OVChipkaart> findAll() throws SQLException;
    
}

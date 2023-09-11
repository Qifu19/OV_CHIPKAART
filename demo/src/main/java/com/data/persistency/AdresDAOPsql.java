package com.data.persistency;

import java.util.List;

import com.data.domain.Adres;
import com.data.domain.Reiziger;

public class AdresDAOPsql implements AdresDAO{

    public boolean save(Adres adres) {
        return false;
        
    }

    public boolean update(Adres adres) {
        return false;
    }

    public boolean delete(Adres adres) {
        return false;
    }

    public Adres findByReiziger(Reiziger reiziger) {
        return null;
    }

    @Override
    public List<Adres> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}

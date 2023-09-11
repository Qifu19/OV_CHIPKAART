package com.data.persistency;

import java.util.List;

import com.data.domain.Adres;
import com.data.domain.Reiziger;

public interface AdresDAO {
    public boolean save(Adres adres);
    public boolean update(Adres adres);
    public boolean delete(Adres adres);
    public Adres findByReiziger(Reiziger reiziger);
    public List<Adres> findAll();
}

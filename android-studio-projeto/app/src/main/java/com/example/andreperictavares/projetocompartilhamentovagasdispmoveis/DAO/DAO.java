package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.DAO;

/**
 * Created by andreperictavares on 4/11/2016.
 */

public interface DAO {
    public void   create(Object o);
    public Object read(Object o);
    public void   update(Object o);
    public void   delete(Object o);
}

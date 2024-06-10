package dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import models.Moeda;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface MoedaDao {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS moedas (id SERIAL PRIMARY KEY, name VARCHAR(200), amount NUMERIC, symbol VARCHAR(10))")
    void createTable();

    @SqlUpdate("INSERT INTO moedas (name, amount, symbol) VALUES (:name, :amount, :symbol)")
    void insertMoeda(@Bind("name") String name, @Bind("amount") BigDecimal amount, @Bind("symbol") String symbol);

    @SqlQuery("SELECT * FROM moedas WHERE name = :name")
    @RegisterBeanMapper(Moeda.class)
    Moeda findMoedaByName(@Bind("name") String name);

    @SqlQuery("SELECT * FROM moedas WHERE id = :id")
    @RegisterBeanMapper(Moeda.class)
    Moeda findMoedaById(@Bind("id") int id);

    @SqlQuery("SELECT * FROM moedas")
    @RegisterBeanMapper(Moeda.class)
    List<Moeda> listMoedas();
}
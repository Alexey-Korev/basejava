package ru.basejava.webapp.sql;

import org.postgresql.util.PSQLException;
import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//getConnection(), prepareStatement, catch SQLException
public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            if (e instanceof PSQLException psqlException && psqlException.getSQLState().equals("23505")) {
                //23505 - A violation of the constraint imposed by a unique index or a unique constraint occurred.
                throw new ExistStorageException(null);
            } else {
                throw new StorageException(e);
            }
        }
    }
}

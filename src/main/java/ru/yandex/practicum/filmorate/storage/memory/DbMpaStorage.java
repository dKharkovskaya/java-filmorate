package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class DbMpaStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public DbMpaStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> findAll() {
        String sql = "select * from mpa order by id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilmsMpa(rs));
    }

    @Override
    public Mpa findById(Integer id) {
        String sql = "select * from mpa where id = ?";

        List<Mpa> mpaCollection = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilmsMpa(rs), id);
        if (mpaCollection.size() == 1) {
            return mpaCollection.get(0);
        } else {
            throw new NotFoundException(String.format("mpa с id-%d не существует.", id));
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        String sqlQuery = "update films set " +
                "mpa_id = null " +
                "where mpa_id = ?";
        jdbcTemplate.update(sqlQuery, id);

        sqlQuery = "delete from mpa where id = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private Mpa makeFilmsMpa(ResultSet rs) throws SQLException {
        Integer mpaId = rs.getInt("id");
        String mpaName = rs.getString("name");
        return new Mpa(mpaId, mpaName);
    }
}

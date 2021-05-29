package tech.itpark.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tech.itpark.exception.DataAccessException;
import tech.itpark.jdbc.JdbcTemplate;
import tech.itpark.model.Feedback;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedbackRepositoryImpl implements FeedbackRepository{

    private final DataSource ds;
    private final JdbcTemplate template = new JdbcTemplate();

    @Override
    public List<Feedback> getAll() {
        List<Feedback> result = new ArrayList<>();
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement("""
                        SELECT id, author_id, product, content FROM feedback WHERE id = ?
                        """)
        ) {
            final var rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Feedback(
                        rs.getLong("id"),
                        rs.getLong("authorId"),
                        rs.getString("product"),
                        rs.getString("content")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    @Override
    public Feedback create(Feedback feedback) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement("""
                        INSERT INTO feedback(author_id, product, content) VALUES (?, ?, ?);
                        """, Statement.RETURN_GENERATED_KEYS)
        ) {
            var index = 0;
            stmt.setLong(++index, feedback.getAuthorId());
            stmt.setString(++index, feedback.getProduct());
            stmt.setString(++index, feedback.getContent());
            stmt.executeUpdate();

            final var keys = stmt.getGeneratedKeys();
            if (!keys.next()) {
                throw new DataAccessException("no keys in result");
            }
            feedback.setId(keys.getLong(1));

            return feedback;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Feedback update(Feedback feedback) {
        try (
                final var stmt = ds.getConnection().prepareStatement("UPDATE feedback SET author_id = ?, product = ?, content = ? WHERE id = ?")
        ) {
            var index = 0;
            stmt.setLong(++index, feedback.getAuthorId());
            stmt.setString(++index, feedback.getProduct());
            stmt.setString(++index, feedback.getContent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return feedback;
    }

    @Override
    public void remove(Long id, Boolean removed) {
        try (
                final var stmt = ds.getConnection().prepareStatement("UPDATE users SET removed = ? WHERE id = ?")
        ) {
            var index = 0;
            stmt.setBoolean(++index, removed);
            stmt.setLong(++index, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}

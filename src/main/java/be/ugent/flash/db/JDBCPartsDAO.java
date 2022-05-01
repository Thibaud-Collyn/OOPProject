package be.ugent.flash.db;

import be.ugent.flash.ImagePart;
import be.ugent.flash.Part;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCPartsDAO extends JDBCAbstractDAO implements PartsDAO {

    public JDBCPartsDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void addTextPart(int qId, String text) throws DataAccessException {
        try (PreparedStatement ps = prepare("INSERT INTO parts(question_id, part) VALUES(?, ?)")){
            ps.setInt(1, qId);
            ps.setString(2, text);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not add part", ex);
        }
    }

    @Override
    public void addImagePart(int qId, byte[] image) throws DataAccessException {
        try (PreparedStatement ps = prepare("INSERT INTO parts(question_id, part) VALUES(?, ?)")){
            ps.setInt(1, qId);
            ps.setBytes(2, image);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not add part", ex);
        }
    }

    @Override
    public void updatePart(int id, String text) throws DataAccessException {
        try (PreparedStatement ps = prepare("UPDATE parts SET part = ? WHERE part_id = ?")) {
            ps.setString(1, text);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not update part", ex);
        }
    }

    @Override
    public void removeParts(int qId) throws DataAccessException {
        try(PreparedStatement ps = prepare("DELETE FROM parts WHERE question_id = ?")) {
            ps.setInt(1, qId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not remove parts", ex);
        }
    }

    @Override
    public ArrayList<Part> getParts(int qId) throws DataAccessException {
        try (PreparedStatement ps = prepare("SELECT part_id, part FROM parts WHERE question_id = ? ORDER BY part_id")) {
            ps.setInt(1, qId);
            ResultSet rs = ps.executeQuery();
            ArrayList<Part> parts = new ArrayList<>();
            while (rs.next()) {
                Part part = new Part(rs.getInt("part_id"), qId, rs.getString("part"));
                parts.add(part);
            }
            return parts;
        } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve parts of give question.", ex);
        }
    }

    public ArrayList<ImagePart> getImageParts(int qId) throws DataAccessException {
        try (PreparedStatement ps = prepare("SELECT part_id, part FROM parts WHERE question_id = ? ORDER BY part_id")) {
            ps.setInt(1, qId);
            ResultSet rs = ps.executeQuery();
            ArrayList<ImagePart> parts = new ArrayList<>();
            while (rs.next()) {
                ImagePart part = new ImagePart(rs.getInt("part_id"), qId, rs.getBytes("part"));
                parts.add(part);
            }
            return parts;
        } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve parts of give question.", ex);
        }
    }
}

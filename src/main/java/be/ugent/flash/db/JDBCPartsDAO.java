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
    public int addPart(int id, String text) throws DataAccessException {
        //TODO: implement
        return 0;
    }

    @Override
    public void updatePart(int id, String text) throws DataAccessException {
        //TODO: implement
    }

    @Override
    public void removePart(int id) throws DataAccessException {
        //TODO: implement
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

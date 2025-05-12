package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.MotifReclamation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMotifReclamation implements IService<MotifReclamation> {
    private Connection cnx;

    public ServiceMotifReclamation() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(MotifReclamation motif) {
        String qry = "INSERT INTO `motif_reclamation` (`nom`, `description`, `type`) VALUES (?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, motif.getNom());
            pstm.setString(2, motif.getDescription());
            pstm.setString(3, motif.getType());
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                motif.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<MotifReclamation> getAll() {
        List<MotifReclamation> motifs = new ArrayList<>();
        String qry = "SELECT * FROM `motif_reclamation`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                MotifReclamation m = new MotifReclamation();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setDescription(rs.getString("description"));
                m.setType(rs.getString("type"));
                motifs.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return motifs;
    }

    @Override
    public void update(MotifReclamation motif) {
        String qry = "UPDATE `motif_reclamation` SET `nom`=?, `description`=?, `type`=? WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, motif.getNom());
            pstm.setString(2, motif.getDescription());
            pstm.setString(3, motif.getType());
            pstm.setInt(4, motif.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(MotifReclamation motif) {
        String qry = "DELETE FROM `motif_reclamation` WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, motif.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public MotifReclamation getById(int id) {
        MotifReclamation motif = null;
        String qry = "SELECT * FROM `motif_reclamation` WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                motif = new MotifReclamation();
                motif.setId(rs.getInt("id"));
                motif.setNom(rs.getString("nom"));
                motif.setDescription(rs.getString("description"));
                motif.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return motif;
    }
}
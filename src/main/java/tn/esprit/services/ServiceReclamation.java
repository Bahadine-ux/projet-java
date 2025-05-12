package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reclamation;
import tn.esprit.models.MotifReclamation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements IService<Reclamation> {
    private Connection cnx;
    private ServiceMotifReclamation serviceMotif;

    public ServiceReclamation() {
        cnx = MyDataBase.getInstance().getCnx();
        serviceMotif = new ServiceMotifReclamation();
    }

    @Override
    public void add(Reclamation reclamation) {
        String qry = "INSERT INTO `reclamation` (`contenu`, `status`, `type`, `id_client`, `date`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, reclamation.getContenu());
            pstm.setString(2, reclamation.getStatus());
            pstm.setString(3, reclamation.getType());
            pstm.setInt(4, reclamation.getId_client());
            pstm.setDate(5, reclamation.getDate() != null ? java.sql.Date.valueOf(reclamation.getDate()) : null); // Convertir LocalDate en java.sql.Date
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                reclamation.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList<>();
        String qry = "SELECT * FROM `reclamation`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id_reclamation"));
                r.setContenu(rs.getString("contenu"));
                r.setStatus(rs.getString("status"));
                r.setType(rs.getString("type"));
                r.setId_client(rs.getInt("id_client"));
                r.setDate(rs.getDate("date") != null ? rs.getDate("date").toLocalDate() : null); // Convertir java.sql.Date en LocalDate
                reclamations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }

    @Override
    public void update(Reclamation reclamation) {
        String qry = "UPDATE `reclamation` SET `contenu`=?, `status`=?, `type`=?, `id_client`=?, `date`=? WHERE `id_reclamation`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, reclamation.getContenu());
            pstm.setString(2, reclamation.getStatus());
            pstm.setString(3, reclamation.getType());
            pstm.setInt(4, reclamation.getId_client());
            pstm.setDate(5, reclamation.getDate() != null ? java.sql.Date.valueOf(reclamation.getDate()) : null); // Mettre à jour la date
            pstm.setInt(6, reclamation.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Reclamation reclamation) {
        String qry = "DELETE FROM `reclamation` WHERE `id_reclamation`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, reclamation.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Reclamation getById(int id) {
        Reclamation reclamation = null;
        String qry = "SELECT * FROM `reclamation` WHERE `id_reclamation`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id_reclamation"));
                reclamation.setContenu(rs.getString("contenu"));
                reclamation.setStatus(rs.getString("status"));
                reclamation.setType(rs.getString("type"));
                reclamation.setId_client(rs.getInt("id_client"));
                reclamation.setDate(rs.getDate("date") != null ? rs.getDate("date").toLocalDate() : null); // Ajout de la date
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamation;
    }

    public MotifReclamation getMotifByType(String type) {
        MotifReclamation motif = null;
        String qry = "SELECT * FROM `motif_reclamation` WHERE `type`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, type);
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
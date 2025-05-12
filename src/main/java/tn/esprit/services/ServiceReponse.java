package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reponse;
import tn.esprit.models.Reclamation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponse implements IService<Reponse> {
    private Connection cnx;
    private ServiceReclamation serviceReclamation;

    public ServiceReponse() {
        cnx = MyDataBase.getInstance().getCnx();
        serviceReclamation = new ServiceReclamation();
    }

    @Override
    public void add(Reponse reponse) {
        String qry = "INSERT INTO `reponse` (`id_reclamation`, `message`, `date`, `statut`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, reponse.getReclamation().getId());
            pstm.setString(2, reponse.getReponse());
            pstm.setDate(3, reponse.getDate() != null ? java.sql.Date.valueOf(reponse.getDate()) : null); // Ajout de la date
            pstm.setString(4, reponse.getStatut()); // Ajout du statut
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                reponse.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reponse> getAll() {
        List<Reponse> reponses = new ArrayList<>();
        String qry = "SELECT * FROM `reponse`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Reponse r = new Reponse();
                r.setId(rs.getInt("id_reponse"));
                Reclamation reclamation = serviceReclamation.getById(rs.getInt("id_reclamation"));
                r.setReclamation(reclamation);
                r.setReponse(rs.getString("message"));
                r.setDate(rs.getDate("date") != null ? rs.getDate("date").toLocalDate() : null); // Récupérer la date
                r.setStatut(rs.getString("statut")); // Récupérer le statut
                reponses.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponses;
    }

    @Override
    public void update(Reponse reponse) {
        String qry = "UPDATE `reponse` SET `id_reclamation`=?, `message`=?, `date`=?, `statut`=? WHERE `id_reponse`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, reponse.getReclamation().getId());
            pstm.setString(2, reponse.getReponse());
            pstm.setDate(3, reponse.getDate() != null ? java.sql.Date.valueOf(reponse.getDate()) : null); // Mettre à jour la date
            pstm.setString(4, reponse.getStatut()); // Mettre à jour le statut
            pstm.setInt(5, reponse.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Reponse reponse) {
        String qry = "DELETE FROM `reponse` WHERE `id_reponse`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, reponse.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Reponse getById(int id) {
        Reponse reponse = null;
        String qry = "SELECT * FROM `reponse` WHERE `id_reponse`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                reponse = new Reponse();
                reponse.setId(rs.getInt("id_reponse"));
                Reclamation reclamation = serviceReclamation.getById(rs.getInt("id_reclamation"));
                reponse.setReclamation(reclamation);
                reponse.setReponse(rs.getString("message"));
                reponse.setDate(rs.getDate("date") != null ? rs.getDate("date").toLocalDate() : null); // Récupérer la date
                reponse.setStatut(rs.getString("statut")); // Récupérer le statut
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponse;
    }
}
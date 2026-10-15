package dao;

import database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pelanggan;

public class PelangganDAO {

    public boolean tambah(Pelanggan p) throws SQLException {
        String sql = "INSERT INTO pelanggan "
                + "(id_pelanggan, nama, no_telepon, alamat) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getIdPelanggan());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getNoTelepon());
            ps.setString(4, p.getAlamat());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Pelanggan> tampilkanSemua() throws SQLException {
        List<Pelanggan> daftar = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                daftar.add(new Pelanggan(
                        rs.getString("id_pelanggan"),
                        rs.getString("nama"),
                        rs.getString("no_telepon"),
                        rs.getString("alamat")
                ));
            }
        }
        return daftar;
    }

    public boolean ubah(Pelanggan p) throws SQLException {
        String sql = "UPDATE pelanggan SET nama=?, no_telepon=?, "
                + "alamat=? WHERE id_pelanggan=?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoTelepon());
            ps.setString(3, p.getAlamat());
            ps.setString(4, p.getIdPelanggan());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean hapus(String id) throws SQLException {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan=?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean cari(String id) throws SQLException {
        String sql = "SELECT id_pelanggan FROM pelanggan "
                + "WHERE id_pelanggan=?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}

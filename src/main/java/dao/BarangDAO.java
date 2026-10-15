package dao;

import database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Barang;

public class BarangDAO {

    public boolean tambah(Barang b) throws SQLException {
        String sql = "INSERT INTO barang "
                + "(id_barang, nama_barang, jenis_barang, berat) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getIdBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getJenisBarang());
            ps.setDouble(4, b.getBerat());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Barang> tampilkanSemua() throws SQLException {
        List<Barang> daftar = new ArrayList<>();
        String sql = "SELECT * FROM barang";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                daftar.add(new Barang(
                        rs.getString("id_barang"),
                        rs.getString("nama_barang"),
                        rs.getString("jenis_barang"),
                        rs.getDouble("berat")
                ));
            }
        }
        return daftar;
    }

    public boolean ubah(Barang b) throws SQLException {
        String sql = "UPDATE barang SET nama_barang=?, "
                + "jenis_barang=?, berat=? WHERE id_barang=?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getNamaBarang());
            ps.setString(2, b.getJenisBarang());
            ps.setDouble(3, b.getBerat());
            ps.setString(4, b.getIdBarang());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean hapus(String id) throws SQLException {
        String sql = "DELETE FROM barang WHERE id_barang=?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean cari(String id) throws SQLException {
        String sql = "SELECT id_barang FROM barang WHERE id_barang=?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}

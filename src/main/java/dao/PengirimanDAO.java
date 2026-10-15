package dao;

import database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Pengiriman;
import model.PengirimanDarat;
import model.PengirimanUdara;

public class PengirimanDAO {

    public boolean tambah(Pengiriman p) throws SQLException {
        String sql = "INSERT INTO pengiriman "
                + "(id_pengiriman, id_pelanggan, id_barang, kota_asal, "
                + "kota_tujuan, status_pengiriman, jenis_pengiriman, "
                + "jenis_kendaraan, nomor_penerbangan) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getIdPengiriman());
            ps.setString(2, p.getIdPelanggan());
            ps.setString(3, p.getIdBarang());
            ps.setString(4, p.getKotaAsal());
            ps.setString(5, p.getKotaTujuan());
            ps.setString(6, p.getStatusPengiriman());

            if (p instanceof PengirimanDarat) {
                PengirimanDarat darat = (PengirimanDarat) p;
                ps.setString(7, "Darat");
                ps.setString(8, darat.getJenisKendaraan());
                ps.setNull(9, Types.VARCHAR);

            } else if (p instanceof PengirimanUdara) {
                PengirimanUdara udara = (PengirimanUdara) p;
                ps.setString(7, "Udara");
                ps.setNull(8, Types.VARCHAR);
                ps.setString(9, udara.getNomorPenerbangan());

            } else {
                throw new SQLException("Jenis pengiriman tidak dikenal.");
            }

            return ps.executeUpdate() > 0;
        }
    }

    public List<Pengiriman> tampilkanSemua() throws SQLException {
        List<Pengiriman> daftar = new ArrayList<>();
        String sql = "SELECT * FROM pengiriman";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String jenis = rs.getString("jenis_pengiriman");

                String id = rs.getString("id_pengiriman");
                String idPelanggan = rs.getString("id_pelanggan");
                String idBarang = rs.getString("id_barang");
                String asal = rs.getString("kota_asal");
                String tujuan = rs.getString("kota_tujuan");
                String status = rs.getString("status_pengiriman");

                if ("Darat".equalsIgnoreCase(jenis)) {
                    daftar.add(new PengirimanDarat(
                            id, idPelanggan, idBarang, asal, tujuan,
                            status, rs.getString("jenis_kendaraan")
                    ));

                } else if ("Udara".equalsIgnoreCase(jenis)) {
                    daftar.add(new PengirimanUdara(
                            id, idPelanggan, idBarang, asal, tujuan,
                            status, rs.getString("nomor_penerbangan")
                    ));

                } else {
                    throw new SQLException(
                            "Jenis pengiriman tidak valid untuk ID: " + id
                    );
                }
            }
        }
        return daftar;
    }

    public boolean ubah(Pengiriman p) throws SQLException {
        String sql = "UPDATE pengiriman SET kota_asal=?, kota_tujuan=?, "
                + "status_pengiriman=? WHERE id_pengiriman=?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getKotaAsal());
            ps.setString(2, p.getKotaTujuan());
            ps.setString(3, p.getStatusPengiriman());
            ps.setString(4, p.getIdPengiriman());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean hapus(String id) throws SQLException {
        String sql = "DELETE FROM pengiriman WHERE id_pengiriman=?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean cari(String id) throws SQLException {
        String sql = "SELECT id_pengiriman FROM pengiriman "
                + "WHERE id_pengiriman=?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}

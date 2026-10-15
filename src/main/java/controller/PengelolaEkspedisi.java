package controller;

import dao.PelangganDAO;
import dao.BarangDAO;
import dao.PengirimanDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import model.Pelanggan;
import model.Barang;
import model.Pengiriman;
import model.PengirimanDarat;
import model.PengirimanUdara;
import view.Menu;

public class PengelolaEkspedisi {

    private final Scanner scanner = new Scanner(System.in);
    private final Menu menu = new Menu();

    private final PelangganDAO pelangganDAO = new PelangganDAO();
    private final BarangDAO barangDAO = new BarangDAO();
    private final PengirimanDAO pengirimanDAO = new PengirimanDAO();

    public void jalankanProgram() {
        int pilihan = 0;

        do {
            menu.tampilkanMenuUtama();

            try {
                System.out.print("Pilih Menu (1-5): ");
                pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1:
                        tambahData();
                        break;
                    case 2:
                        lihatData();
                        break;
                    case 3:
                        ubahData();
                        break;
                    case 4:
                        hapusData();
                        break;
                    case 5:
                        System.out.println("Program selesai.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        } while (pilihan != 5);

        scanner.close();
    }

    void tambahData() {
        menu.tampilkanMenuData();

        try {
            System.out.print("Pilih Data: ");
            int pilihan = Integer.parseInt(scanner.nextLine());

            switch (pilihan) {
                case 1: {
                    System.out.print("ID Pelanggan: ");
                    String id = scanner.nextLine();

                    if (pelangganDAO.cari(id)) {
                        System.out.println("ID Pelanggan sudah digunakan!");
                        return;
                    }

                    System.out.print("Nama: ");
                    String nama = scanner.nextLine();

                    System.out.print("No Telepon: ");
                    String telepon = scanner.nextLine();

                    System.out.print("Alamat: ");
                    String alamat = scanner.nextLine();

                    Pelanggan p = new Pelanggan(id, nama, telepon, alamat);
                    if (pelangganDAO.tambah(p)) {
                        System.out.println("Data pelanggan berhasil ditambahkan.");
                    }
                    break;
                }

                case 2: {
                    System.out.print("ID Barang: ");
                    String id = scanner.nextLine();

                    if (barangDAO.cari(id)) {
                        System.out.println("ID Barang sudah digunakan!");
                        return;
                    }

                    System.out.print("Nama Barang: ");
                    String nama = scanner.nextLine();

                    System.out.print("Jenis Barang: ");
                    String jenis = scanner.nextLine();

                    System.out.print("Berat (ton): ");
                    double berat = Double.parseDouble(scanner.nextLine());

                    if (berat <= 0) {
                        System.out.println("Berat harus lebih dari 0!");
                        return;
                    }

                    Barang b = new Barang(id, nama, jenis, berat);
                    if (barangDAO.tambah(b)) {
                        System.out.println("Data barang berhasil ditambahkan.");
                    }
                    break;
                }

                case 3: {
                    System.out.println("1. Pengiriman Darat");
                    System.out.println("2. Pengiriman Udara");
                    System.out.print("Pilih Jenis Pengiriman: ");
                    int jenis = Integer.parseInt(scanner.nextLine());

                    if (jenis < 1 || jenis > 2) {
                        System.out.println("Pilihan tidak valid!");
                        return;
                    }

                    System.out.print("ID Pengiriman: ");
                    String id = scanner.nextLine();

                    if (pengirimanDAO.cari(id)) {
                        System.out.println("ID Pengiriman sudah digunakan!");
                        return;
                    }

                    System.out.print("ID Pelanggan: ");
                    String idPelanggan = scanner.nextLine();

                    if (!pelangganDAO.cari(idPelanggan)) {
                        System.out.println("ID Pelanggan tidak ditemukan!");
                        return;
                    }

                    System.out.print("ID Barang: ");
                    String idBarang = scanner.nextLine();

                    if (!barangDAO.cari(idBarang)) {
                        System.out.println("ID Barang tidak ditemukan!");
                        return;
                    }

                    System.out.print("Kota Asal: ");
                    String asal = scanner.nextLine();

                    System.out.print("Kota Tujuan: ");
                    String tujuan = scanner.nextLine();

                    System.out.print("Status Pengiriman: ");
                    String status = scanner.nextLine();

                    Pengiriman p;

                    if (jenis == 1) {
                        System.out.print("Jenis Kendaraan: ");
                        String kendaraan = scanner.nextLine();

                        p = new PengirimanDarat(
                                id, idPelanggan, idBarang,
                                asal, tujuan, status, kendaraan
                        );
                    } else {
                        System.out.print("Nomor Penerbangan: ");
                        String nomor = scanner.nextLine();

                        p = new PengirimanUdara(
                                id, idPelanggan, idBarang,
                                asal, tujuan, status, nomor
                        );
                    }

                    if (pengirimanDAO.tambah(p)) {
                        System.out.println("Data pengiriman berhasil ditambahkan.");
                    }
                    break;
                }

                default:
                    System.out.println("Pilihan tidak tersedia.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Input angka tidak valid!");
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    void lihatData() {
        menu.tampilkanMenuData();

        try {
            System.out.print("Pilih Data: ");
            int pilihan = Integer.parseInt(scanner.nextLine());

            switch (pilihan) {
                case 1:
                    List<Pelanggan> pelanggan = pelangganDAO.tampilkanSemua();

                    if (pelanggan.isEmpty()) {
                        System.out.println("Belum ada data pelanggan.");
                    }

                    for (Pelanggan p : pelanggan) {
                        System.out.println("ID Pelanggan: " + p.getIdPelanggan());
                        System.out.println("Nama: " + p.getNama());
                        System.out.println("No Telepon: " + p.getNoTelepon());
                        System.out.println("Alamat: " + p.getAlamat());
                        System.out.println();
                    }
                    break;

                case 2:
                    List<Barang> barang = barangDAO.tampilkanSemua();

                    if (barang.isEmpty()) {
                        System.out.println("Belum ada data barang.");
                    }

                    for (Barang b : barang) {
                        System.out.println("ID Barang: " + b.getIdBarang());
                        System.out.println("Nama Barang: " + b.getNamaBarang());
                        System.out.println("Jenis Barang: " + b.getJenisBarang());
                        System.out.println("Berat: " + b.getBerat() + " ton");
                        System.out.println();
                    }
                    break;

                case 3:
                    List<Pengiriman> pengiriman =
                            pengirimanDAO.tampilkanSemua();

                    if (pengiriman.isEmpty()) {
                        System.out.println("Belum ada data pengiriman.");
                    }

                    for (Pengiriman p : pengiriman) {
                        System.out.println("ID Pengiriman: " + p.getIdPengiriman());
                        System.out.println("ID Pelanggan: " + p.getIdPelanggan());
                        System.out.println("ID Barang: " + p.getIdBarang());
                        System.out.println("Kota Asal: " + p.getKotaAsal());
                        System.out.println("Kota Tujuan: " + p.getKotaTujuan());
                        System.out.println("Status: " + p.getStatusPengiriman());
                        p.tampilkanJenisPengiriman();
                        System.out.println();
                    }
                    break;

                default:
                    System.out.println("Pilihan tidak tersedia.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka!");
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }
    }

    void ubahData() {
        menu.tampilkanMenuData();

        try {
            System.out.print("Pilih Data: ");
            int pilihan = Integer.parseInt(scanner.nextLine());

            switch (pilihan) {
                case 1: {
                    System.out.print("Masukkan ID Pelanggan: ");
                    String id = scanner.nextLine();

                    for (Pelanggan p : pelangganDAO.tampilkanSemua()) {
                        if (p.getIdPelanggan().equals(id)) {
                            System.out.print("Nama: ");
                            p.setNama(scanner.nextLine());

                            System.out.print("No Telepon: ");
                            p.setNoTelepon(scanner.nextLine());

                            System.out.print("Alamat: ");
                            p.setAlamat(scanner.nextLine());

                            if (pelangganDAO.ubah(p)) {
                                System.out.println("Data pelanggan berhasil diubah.");
                            }
                            return;
                        }
                    }

                    System.out.println("Data pelanggan tidak ditemukan.");
                    break;
                }

                case 2: {
                    System.out.print("Masukkan ID Barang: ");
                    String id = scanner.nextLine();

                    for (Barang b : barangDAO.tampilkanSemua()) {
                        if (b.getIdBarang().equals(id)) {
                            System.out.print("Nama Barang: ");
                            b.setNamaBarang(scanner.nextLine());

                            System.out.print("Jenis Barang: ");
                            b.setJenisBarang(scanner.nextLine());

                            System.out.print("Berat (ton): ");
                            double berat = Double.parseDouble(scanner.nextLine());

                            if (berat <= 0) {
                                System.out.println("Berat harus lebih dari 0!");
                                return;
                            }

                            b.setBerat(berat);

                            if (barangDAO.ubah(b)) {
                                System.out.println("Data barang berhasil diubah.");
                            }
                            return;
                        }
                    }

                    System.out.println("Data barang tidak ditemukan.");
                    break;
                }

                case 3: {
                    System.out.print("Masukkan ID Pengiriman: ");
                    String id = scanner.nextLine();

                    for (Pengiriman p : pengirimanDAO.tampilkanSemua()) {
                        if (p.getIdPengiriman().equals(id)) {
                            System.out.print("Kota Asal: ");
                            p.setKotaAsal(scanner.nextLine());

                            System.out.print("Kota Tujuan: ");
                            p.setKotaTujuan(scanner.nextLine());

                            System.out.print("Status Pengiriman: ");
                            p.setStatusPengiriman(scanner.nextLine());

                            if (pengirimanDAO.ubah(p)) {
                                System.out.println("Data pengiriman berhasil diubah.");
                            }
                            return;
                        }
                    }

                    System.out.println("Data pengiriman tidak ditemukan.");
                    break;
                }

                default:
                    System.out.println("Pilihan tidak tersedia.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Input angka tidak valid!");
        } catch (SQLException e) {
            System.out.println("Gagal mengubah data: " + e.getMessage());
        }
    }

    void hapusData() {
        menu.tampilkanMenuData();

        try {
            System.out.print("Pilih Data: ");
            int pilihan = Integer.parseInt(scanner.nextLine());

            System.out.print("Masukkan ID: ");
            String id = scanner.nextLine();

            boolean berhasil;

            switch (pilihan) {
                case 1:
                    berhasil = pelangganDAO.hapus(id);
                    break;
                case 2:
                    berhasil = barangDAO.hapus(id);
                    break;
                case 3:
                    berhasil = pengirimanDAO.hapus(id);
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
                    return;
            }

            if (berhasil) {
                System.out.println("Data berhasil dihapus.");
            } else {
                System.out.println("Data tidak ditemukan.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka!");
        } catch (SQLException e) {
            System.out.println(
                    "Gagal menghapus data. Pastikan data tidak sedang "
                    + "digunakan oleh pengiriman lain."
            );
            System.out.println("Detail: " + e.getMessage());
        }
    }
}

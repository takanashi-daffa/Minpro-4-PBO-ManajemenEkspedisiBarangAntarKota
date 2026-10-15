# MINPRO 4 PBO - MANAJEMEN EKSPEDISI BARANG ANTAR KOTA

Nama: Daffa Arkhabista

NIM: 2509116018

## DESKRIPSI SINGKAT PROGRAM

Program ini merupakan pengembangan dari Mini Project 3 untuk mengelola data ekspedisi barang antar kota. Data yang dikelola terdiri dari data pelanggan, barang, dan pengiriman.

Program memiliki fitur CRUD (Create, Read, Update, Delete), sehingga pengguna dapat menambah, melihat, mengubah, dan menghapus data. Pada Mini Project 4, program sudah terhubung dengan database MySQL menggunakan JDBC agar data dapat disimpan dan dikelola melalui database.

## STRUKTUR PACKAGE

Struktur package yang digunakan dalam program:

```text
controller
└── PengelolaEkspedisi.java

dao
├── PelangganDAO.java
├── BarangDAO.java
└── PengirimanDAO.java

database
└── KoneksiDatabase.java

main
└── MinproPBO.java

model
├── Barang.java
├── LayananPengiriman.java
├── Pelanggan.java
├── Pengiriman.java
├── PengirimanDarat.java
└── PengirimanUdara.java

view
└── Menu.java
```

## ALUR PROGRAM

Saat program dijalankan, `MinproPBO` menjalankan `PengelolaEkspedisi`, kemudian pengguna masuk ke menu utama.

**Menu Utama → Tambah Data / Lihat Data / Ubah Data / Hapus Data / Keluar**

Pengguna dapat memilih data pelanggan, barang, atau pengiriman yang ingin dikelola. Controller memproses pilihan pengguna dan memanggil DAO yang sesuai untuk menjalankan operasi CRUD pada database MySQL.

## PENERAPAN ENCAPSULATION DAN INHERITANCE

### ENCAPSULATION

Encapsulation diterapkan dengan menggunakan access modifier `private` pada atribut class model dan getter serta setter untuk mengakses atau mengubah data.

Contohnya pada `Pelanggan.java`:

```java
private String idPelanggan;
private String nama;
private String noTelepon;
private String alamat;
```

Getter dan setter digunakan untuk mengakses serta mengubah atribut tersebut:

```java
public String getNama() {
    return nama;
}

public void setNama(String nama) {
    this.nama = nama;
}
```

### INHERITANCE

Inheritance diterapkan pada class `PengirimanDarat` dan `PengirimanUdara` yang mewarisi atribut dan method dari abstract class `Pengiriman`.

```java
public class PengirimanDarat extends Pengiriman
```

```java
public class PengirimanUdara extends Pengiriman
```

Kedua subclass menggunakan data dan method dari `Pengiriman`, serta memiliki atribut tambahan masing-masing.

```java
private String jenisKendaraan;
```

```java
private String nomorPenerbangan;
```

## PENERAPAN POLYMORPHISM DAN ABSTRACTION

### POLYMORPHISM

Polymorphism diterapkan melalui overriding method `tampilkanJenisPengiriman()` pada class `PengirimanDarat` dan `PengirimanUdara`.

Pada abstract class `Pengiriman`:

```java
public abstract void tampilkanJenisPengiriman();
```

Implementasi pada `PengirimanDarat`:

```java
@Override
public void tampilkanJenisPengiriman() {
    System.out.println("Jenis Pengiriman: Darat");
    System.out.println("Jenis Kendaraan: " + jenisKendaraan);
}
```

Implementasi pada `PengirimanUdara`:

```java
@Override
public void tampilkanJenisPengiriman() {
    System.out.println("Jenis Pengiriman: Udara");
    System.out.println("Nomor Penerbangan: " + nomorPenerbangan);
}
```

Kedua class menggunakan nama method yang sama, tetapi memiliki implementasi berbeda sesuai jenis pengirimannya.

### ABSTRACTION

Abstraction diterapkan melalui abstract class `Pengiriman` dan abstract method `tampilkanJenisPengiriman()`.

```java
public abstract class Pengiriman {
```

Abstract method yang terdapat di dalamnya:

```java
public abstract void tampilkanJenisPengiriman();
```

Method tersebut diimplementasikan oleh subclass `PengirimanDarat` dan `PengirimanUdara` menggunakan `@Override`.

## PENERAPAN JDBC

JDBC (Java Database Connectivity) digunakan untuk menghubungkan program Java dengan database MySQL bernama `db_ekspedisi_barang`.

Koneksi database diatur melalui class `KoneksiDatabase` menggunakan `DriverManager` dan `Connection`.

Contoh kode pada `KoneksiDatabase.java`:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/db_ekspedisi_barang";

private static final String USER = "root";
private static final String PASSWORD = "";

public static Connection getKoneksi() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
}
```

Operasi CRUD ditangani oleh class DAO menggunakan `PreparedStatement`, sedangkan `ResultSet` digunakan untuk membaca hasil query.

Contoh penggunaan JDBC:

```java
String sql = "SELECT * FROM pelanggan";
PreparedStatement ps = koneksi.prepareStatement(sql);
ResultSet rs = ps.executeQuery();
```

Dengan penerapan JDBC, data pelanggan, barang, dan pengiriman dapat dikelola langsung melalui database MySQL.

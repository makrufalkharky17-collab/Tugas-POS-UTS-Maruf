/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_toko;

/**
 *
 * @author LENOVO
 */
public class Barang {
    
    // Attribute
private String kode;
private String nama;
private int harga;
private int stok;

// Constructor
public Barang(String kode, String nama, int harga, int stok) {
    this.kode = kode;
    this.nama = nama;
    this.harga = harga;
    this.stok = stok;
}

// Getter
public String getKode() {
    return kode;
}

public String getNama() {
    return nama;
}

public int getHarga() {
    return harga;
}

public int getStok() {
    return stok;
}

// Mengubah stok barang
public void setStok(int stok) {
    if (stok >= 0) {
        this.stok = stok;
    }
}

// Menampilkan informasi barang
public void tampilkanBarang() {
    System.out.printf(
        "%-8s %-18s Rp%-10d %d%n",
        kode, nama, harga, stok
    );
}
    
}

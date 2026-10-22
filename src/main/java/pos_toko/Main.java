package pos_toko;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Barang> daftarBarang = new ArrayList<>();

    public static void main(String[] args) {

        isiDaftarBarang();

        int pilihan;

        do {
            System.out.println();
            System.out.println("========================================");
            System.out.println("          POS TOKO MAJU JAYA");
            System.out.println("========================================");
            System.out.println("1. Lihat Daftar Barang");
            System.out.println("2. Mulai Transaksi");
            System.out.println("3. Lihat Riwayat Transaksi");
            System.out.println("4. Keluar");
            System.out.println("========================================");

            pilihan = bacaAngka("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    tampilkanDaftarBarang();
                    break;

                case 2:
                    mulaiTransaksi();
                    break;

                case 3:
                    RiwayatTransaksi.tampilkan();
                    break;

                case 4:
                    System.out.println("Terima kasih telah menggunakan POS!");
                    break;

                default:
                    System.out.println("Pilihan tidak tersedia. Silakan coba lagi.");
            }

        } while (pilihan != 4);

        input.close();
    }

    private static void isiDaftarBarang() {

        daftarBarang.add(new Barang("B001", "Indomie", 3500, 20));
        daftarBarang.add(new Barang("B002", "Teh Botol", 5000, 15));
        daftarBarang.add(new Barang("B003", "Roti", 4000, 10));
        daftarBarang.add(new Barang("B004", "Susu", 7000, 12));
        daftarBarang.add(new Barang("B005", "Kopi", 2500, 25));
    }

    private static void tampilkanDaftarBarang() {

        System.out.println();
        System.out.println("================ DAFTAR BARANG ================");

        System.out.printf("%-8s %-18s %-12s %s%n",
                "Kode", "Nama Barang", "Harga", "Stok");

        System.out.println("-----------------------------------------------");

        for (Barang barang : daftarBarang) {
            barang.tampilkanBarang();
        }

        System.out.println("===============================================");
    }

    private static void mulaiTransaksi() {

        Transaksi transaksi = new Transaksi();

        while (true) {

            tampilkanDaftarBarang();

            System.out.print("Masukkan kode barang (0 untuk selesai): ");
            String kode = input.nextLine().trim();

            if (kode.equals("0")) {
                break;
            }

            Barang barangDitemukan = null;

            for (Barang barang : daftarBarang) {
                if (barang.getKode().equalsIgnoreCase(kode)) {
                    barangDitemukan = barang;
                    break;
                }
            }

            if (barangDitemukan == null) {
                System.out.println("Kode barang tidak ditemukan.");
                continue;
            }

            System.out.println("Nama barang : " + barangDitemukan.getNama());
            System.out.println("Harga       : Rp" + barangDitemukan.getHarga());
            System.out.println("Stok        : " + barangDitemukan.getStok());

            int jumlah = bacaAngka("Masukkan jumlah: ");

            if (!transaksi.tambahBarang(barangDitemukan, jumlah)) {
                System.out.println("Jumlah tidak valid atau stok tidak cukup.");
                continue;
            }

            System.out.println("Barang berhasil ditambahkan.");
            transaksi.tampilkanBelanja();

            System.out.print("Tambah barang lagi? (y/n): ");
            String jawaban = input.nextLine().trim();

            if (jawaban.equalsIgnoreCase("n")) {
                break;
            }
        }

        if (transaksi.isKosong()) {
            System.out.println("Transaksi dibatalkan karena tidak ada barang.");
            return;
        }

        int total = transaksi.hitungTotal();

        System.out.println();
        System.out.println("=============== PEMBAYARAN ===============");
        System.out.println("Total belanja: Rp" + total);

        Pembayaran pembayaran = new PembayaranTunai();

        while (true) {

            int uangBayar = bacaAngka("Uang pembayaran: Rp");

            if (uangBayar < 0) {
                System.out.println("Uang pembayaran tidak boleh negatif.");
                continue;
            }

            int kembalian = pembayaran.prosesPembayaran(total, uangBayar);

            if (kembalian < 0) {
                System.out.println("Uang belum cukup!");
                System.out.println("Kekurangan: Rp" + (total - uangBayar));
                continue;
            }

            // Ambil nomor transaksi berikutnya dari file riwayat.
            int nomorTransaksi =
                    RiwayatTransaksi.nomorTransaksiBerikutnya();

            // Buat struk lengkap dengan rincian barang.
            String struk = transaksi.buatStruk(
                    nomorTransaksi,
                    uangBayar,
                    kembalian
            );

            // Simpan riwayat sebelum menyelesaikan transaksi.
            if (RiwayatTransaksi.simpan(struk)) {

                // Kurangi stok setelah riwayat berhasil disimpan.
                transaksi.kurangiStok();

                // Cetak struk belanja.
                transaksi.cetakStruk(
                        nomorTransaksi,
                        uangBayar,
                        kembalian
                );

            } else {
                System.out.println(
                        "Transaksi tidak dapat diselesaikan karena riwayat gagal disimpan."
                );
            }

            break;
        }
    }

    private static int bacaAngka(String pesan) {

        while (true) {
            System.out.print(pesan);
            String teks = input.nextLine().trim();

            try {
                return Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa bilangan bulat.");
            }
        }
    }
}
package pos_toko;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class RiwayatTransaksi {

    private static final Path LOKASI_FILE =
            Paths.get("riwayat_transaksi.txt");

    public static boolean simpan(String isiTransaksi) {

        try {
            Files.writeString(
                    LOKASI_FILE,
                    isiTransaksi + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            System.out.println("Riwayat transaksi berhasil disimpan.");
            return true;

        } catch (IOException e) {
            System.out.println("Gagal menyimpan riwayat transaksi.");
            System.out.println("Pesan error: " + e.getMessage());
            return false;
        }
    }

    public static void tampilkan() {

        System.out.println();
        System.out.println("=========== RIWAYAT TRANSAKSI ===========");

        if (!Files.exists(LOKASI_FILE)) {
            System.out.println("Belum ada riwayat transaksi.");
            System.out.println("=========================================");
            return;
        }

        try {
            String isi = Files.readString(LOKASI_FILE);

            if (isi.isBlank()) {
                System.out.println("Belum ada riwayat transaksi.");
            } else {
                System.out.println(isi);
            }

        } catch (IOException e) {
            System.out.println("Gagal membaca riwayat transaksi.");
            System.out.println("Pesan error: " + e.getMessage());
        }

        System.out.println("=========================================");
    }

    public static int nomorTransaksiBerikutnya() {

        if (!Files.exists(LOKASI_FILE)) {
            return 1;
        }

        int nomorTerbesar = 0;

        try {
            for (String baris : Files.readAllLines(LOKASI_FILE)) {

                String penanda = "TRANSAKSI #";
                int posisi = baris.indexOf(penanda);

                if (posisi >= 0) {

                    String angka = baris.substring(
                            posisi + penanda.length()
                    ).trim();

                    int akhir = 0;

                    while (akhir < angka.length()
                            && Character.isDigit(angka.charAt(akhir))) {
                        akhir++;
                    }

                    if (akhir > 0) {
                        int nomor = Integer.parseInt(
                                angka.substring(0, akhir)
                        );

                        if (nomor > nomorTerbesar) {
                            nomorTerbesar = nomor;
                        }
                    }
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println(
                    "Peringatan: nomor transaksi gagal dibaca dari riwayat."
            );
        }

        return nomorTerbesar + 1;
    }
}
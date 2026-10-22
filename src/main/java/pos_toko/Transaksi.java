package pos_toko;

import java.util.ArrayList;

public class Transaksi {

    private ArrayList<Barang> daftarBarang;
    private ArrayList<Integer> jumlahBarang;

    public Transaksi() {
        daftarBarang = new ArrayList<>();
        jumlahBarang = new ArrayList<>();
    }

    public boolean tambahBarang(Barang barang, int jumlah) {

        if (jumlah <= 0 || jumlah > barang.getStok()) {
            return false;
        }

        int index = -1;

        for (int i = 0; i < daftarBarang.size(); i++) {
            if (daftarBarang.get(i).getKode()
                    .equalsIgnoreCase(barang.getKode())) {
                index = i;
                break;
            }
        }

        if (index != -1) {

            int jumlahBaru = jumlahBarang.get(index) + jumlah;

            if (jumlahBaru > barang.getStok()) {
                return false;
            }

            jumlahBarang.set(index, jumlahBaru);

        } else {
            daftarBarang.add(barang);
            jumlahBarang.add(jumlah);
        }

        return true;
    }

    public int hitungTotal() {

        int total = 0;

        for (int i = 0; i < daftarBarang.size(); i++) {
            total += daftarBarang.get(i).getHarga()
                    * jumlahBarang.get(i);
        }

        return total;
    }

    public boolean isKosong() {
        return daftarBarang.isEmpty();
    }

    public void kurangiStok() {

        for (int i = 0; i < daftarBarang.size(); i++) {

            Barang barang = daftarBarang.get(i);
            int jumlah = jumlahBarang.get(i);

            barang.setStok(barang.getStok() - jumlah);
        }
    }

    public void tampilkanBelanja() {

        System.out.println();
        System.out.println("============== RINCIAN BELANJA ==============");

        System.out.printf("%-18s %5s %12s %12s%n",
                "Nama Barang", "Qty", "Harga", "Subtotal");

        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < daftarBarang.size(); i++) {

            Barang barang = daftarBarang.get(i);
            int jumlah = jumlahBarang.get(i);
            int harga = barang.getHarga();
            int subtotal = harga * jumlah;

            System.out.printf("%-18s %5d Rp%10d Rp%10d%n",
                    barang.getNama(), jumlah, harga, subtotal);
        }

        System.out.println("----------------------------------------------------------");

        System.out.printf("%-35s Rp%10d%n",
                "TOTAL BELANJA", hitungTotal());

        System.out.println("==========================================================");
    }

    // Membuat teks struk untuk disimpan ke file.
    public String buatStruk(int nomorTransaksi,
                            int uangBayar,
                            int kembalian) {

        StringBuilder struk = new StringBuilder();

        struk.append("==================================================\n");
        struk.append("                 TRANSAKSI #")
                .append(nomorTransaksi).append("\n");
        struk.append("                 TOKO MAJU JAYA\n");
        struk.append("==================================================\n");

        struk.append(String.format(
                "%-18s %5s %12s %12s%n",
                "Nama Barang", "Qty", "Harga", "Subtotal"
        ));

        struk.append("--------------------------------------------------\n");

        for (int i = 0; i < daftarBarang.size(); i++) {

            Barang barang = daftarBarang.get(i);
            int jumlah = jumlahBarang.get(i);
            int harga = barang.getHarga();
            int subtotal = harga * jumlah;

            struk.append(String.format(
                    "%-18s %5d Rp%10d Rp%10d%n",
                    barang.getNama(),
                    jumlah,
                    harga,
                    subtotal
            ));
        }

        struk.append("--------------------------------------------------\n");

        struk.append(String.format(
                "%-35s Rp%10d%n",
                "Total Belanja", hitungTotal()
        ));

        struk.append(String.format(
                "%-35s Rp%10d%n",
                "Uang Pembayaran", uangBayar
        ));

        struk.append(String.format(
                "%-35s Rp%10d%n",
                "Kembalian", kembalian
        ));

        struk.append("==================================================\n");
        struk.append("                 TERIMA KASIH!\n");
        struk.append("              SELAMAT BERBELANJA\n");
        struk.append("==================================================");

        return struk.toString();
    }

    public void cetakStruk(int nomorTransaksi,
                           int uangBayar,
                           int kembalian) {

        System.out.println();
        System.out.println(buatStruk(
                nomorTransaksi,
                uangBayar,
                kembalian
        ));
    }
}
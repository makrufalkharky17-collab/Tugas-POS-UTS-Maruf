/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_toko;

public class PembayaranTunai extends Pembayaran {

    @Override
    public int prosesPembayaran(int total, int uangBayar) {

        if (uangBayar < total) {
            return -1;
        }

        return uangBayar - total;
    }

}
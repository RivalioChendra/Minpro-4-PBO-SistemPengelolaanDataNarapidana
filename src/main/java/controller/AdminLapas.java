package controller;

import dao.NarapidanaDAO;
import model.Narapidana;
import model.NarapidanaNarkotika;
import model.NarapidanaTerorisme;
import model.NarapidanaKorupsi;
import model.NarapidanaPembunuhan;
import model.NarapidanaPencurian;
import model.PemeriksaanRutin;
import view.NarapidanaView;

import java.util.List;

public class AdminLapas {

    private NarapidanaDAO narapidanaDAO;
    private NarapidanaView view;

    public AdminLapas(NarapidanaView view) {
        this.narapidanaDAO = new NarapidanaDAO();
        this.view = view;
    }

    // CREATE
    public void tambahNarapidana() {
        view.tampilkanSubMenuKategori();
        int kategori = view.bacaAngka("Pilih kategori (1-5)");

        if (kategori < 1 || kategori > 5) {
            view.tampilkanPesan(">> Kategori tidak valid.");
            return;
        }

        String id = view.bacaTeks("ID Narapidana");

        // Cek ID dari database
        if (narapidanaDAO.cariNarapidanaById(id) != null) {
            view.tampilkanPesan(">> ID sudah digunakan.");
            return;
        }

        String nama = view.bacaTeks("Nama");
        String kasus = view.bacaTeks("Kasus");
        int masaTahanan = view.bacaAngkaPositif("Masa Tahanan (bulan)");
        String nomorSel = view.bacaTeks("Nomor Sel");
        String blokSel = view.bacaTeks("Blok Sel");

        Narapidana napiBaru;

        switch (kategori) {
            case 1 -> {
                String jenisRehabilitasi = view.bacaTeks("Jenis Rehabilitasi");
                napiBaru = new NarapidanaNarkotika(id, nama, kasus, masaTahanan, nomorSel, blokSel, jenisRehabilitasi);
            }

            case 2 -> {
                String tingkatRisiko = view.bacaTeks("Tingkat Risiko (Rendah/Sedang/Tinggi)");
                napiBaru = new NarapidanaTerorisme(id, nama, kasus, masaTahanan, nomorSel, blokSel, tingkatRisiko);
            }

            case 3 -> {
                long uangPengganti = view.bacaAngkaPanjangPositif("Uang Pengganti (Rp)");
                napiBaru = new NarapidanaKorupsi(id, nama, kasus, masaTahanan, nomorSel, blokSel, uangPengganti);
            }

            case 4 -> {
                String kategoriPembunuhan = view.bacaTeks("Kategori (Berencana/Tidak Berencana)");
                napiBaru = new NarapidanaPembunuhan(id, nama, kasus, masaTahanan, nomorSel, blokSel, kategoriPembunuhan);
            }

            case 5 -> {
                long nilaiKerugian = view.bacaAngkaPanjangPositif("Nilai Kerugian (Rp)");
                napiBaru = new NarapidanaPencurian(id, nama, kasus, masaTahanan, nomorSel, blokSel, nilaiKerugian);
            }

            default -> {
                view.tampilkanPesan(">> Kategori tidak valid.");
                return;
            }
        }

        // Simpan ke database
        boolean berhasil = narapidanaDAO.tambahNarapidana(napiBaru);

        if (berhasil) {
            view.tampilkanPesan(">> Data narapidana berhasil ditambahkan!");
        } else {
            view.tampilkanPesan(">> Gagal menambahkan data narapidana.");
        }
    }

    // READ (semua)
    public void tampilkanNarapidana() {
        List<Narapidana> daftarNarapidana = narapidanaDAO.getAllNarapidana();

        if (daftarNarapidana.isEmpty()) {
            view.tampilkanPesan("Belum ada data narapidana.");
            return;
        }

        for (Narapidana n : daftarNarapidana) {
            view.tampilkanNarapidana(n);

            if (n instanceof PemeriksaanRutin p) {
                view.tampilkanPesan("Pemeriksaan   : " + p.getJenisPemeriksaan());
                view.tampilkanPesan("-----------------------------------------------------------");
            }

            view.tampilkanPesan("");
        }
    }

    // READ (berdasarkan kategori)
    public void tampilkanNarapidana(String kategori) {
        List<Narapidana> daftarNarapidana = narapidanaDAO.getAllNarapidana();

        boolean ditemukan = false;

        for (Narapidana n : daftarNarapidana) {
            if (n.getKategori().equalsIgnoreCase(kategori)) {
                view.tampilkanNarapidana(n);

                if (n instanceof PemeriksaanRutin p) {
                    view.tampilkanPesan("Pemeriksaan  : " + p.getJenisPemeriksaan());
                }

                view.tampilkanPesan("-----------------------------------------------------------");
                view.tampilkanPesan("");

                ditemukan = true;
            }
        }

        if (!ditemukan) {
            view.tampilkanPesan(">> Tidak ada narapidana dengan kategori " + kategori);
        }
    }

    // UPDATE (nomor sel)
    public void updateNomorSel() {
        String idTarget = view.bacaTeks("Masukkan ID Narapidana");

        Narapidana napi = narapidanaDAO.cariNarapidanaById(idTarget);

        if (napi == null) {
            view.tampilkanPesan(">> ID Narapidana tidak ditemukan!");
            return;
        }

        String selBaru = view.bacaTeks("Nomor Sel Baru");

        try {
            napi.setNomorSel(selBaru);
        } catch (IllegalArgumentException e) {
            view.tampilkanPesan(">> " + e.getMessage());
            return;
        }

        boolean berhasil = narapidanaDAO.updateNarapidana(napi);

        if (berhasil) {
            view.tampilkanPesan(">> Nomor sel berhasil diperbarui!"
            );
        } else {
            view.tampilkanPesan(">> Gagal memperbarui nomor sel."
            );
        }
    }

    // DELETE
    public void hapusNarapidana() {
        String idTarget = view.bacaTeks("Masukkan ID Narapidana");
        Narapidana napi = narapidanaDAO.cariNarapidanaById(idTarget);

        if (napi == null) {
            view.tampilkanPesan(">> ID Narapidana tidak ditemukan!");
            return;
        }

        boolean berhasil = narapidanaDAO.hapusNarapidana(idTarget);

        if (berhasil) {
            view.tampilkanPesan(">> Data narapidana berhasil dihapus!");
        } else {
            view.tampilkanPesan(">> Gagal menghapus data narapidana.");
        }
    }
}

package dao;

import config.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Narapidana;
import model.NarapidanaNarkotika;
import model.NarapidanaTerorisme;
import model.NarapidanaKorupsi;
import model.NarapidanaPembunuhan;
import model.NarapidanaPencurian;

public class NarapidanaDAO {

    // CREATE
    public boolean tambahNarapidana(Narapidana napi) {
        String sql = "INSERT INTO narapidana "
                + "(id_napi, nama, kasus, masa_tahanan, nomor_sel, "
                + "blok_sel, kategori, jenis_rehabilitasi, tingkat_risiko, "
                + "uang_pengganti, kategori_pembunuhan, nilai_kerugian) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, napi.getIdNapi());
            pstmt.setString(2, napi.getNama());
            pstmt.setString(3, napi.getKasus());
            pstmt.setInt(4, napi.getMasaTahanan());
            pstmt.setString(5, napi.getNomorSel());
            pstmt.setString(6, napi.getBlokSel());
            pstmt.setString(7, napi.getKategori());

            pstmt.setNull(8, java.sql.Types.VARCHAR);
            pstmt.setNull(9, java.sql.Types.VARCHAR);
            pstmt.setNull(10, java.sql.Types.BIGINT);
            pstmt.setNull(11, java.sql.Types.VARCHAR);
            pstmt.setNull(12, java.sql.Types.BIGINT);

            if (napi instanceof NarapidanaNarkotika) {
                NarapidanaNarkotika n = (NarapidanaNarkotika) napi;
                pstmt.setString(8, n.getJenisRehabilitasi());

            } else if (napi instanceof NarapidanaTerorisme) {
                NarapidanaTerorisme n = (NarapidanaTerorisme) napi;
                pstmt.setString(9, n.getTingkatRisiko());

            } else if (napi instanceof NarapidanaKorupsi) {
                NarapidanaKorupsi n = (NarapidanaKorupsi) napi;
                pstmt.setLong(10, n.getUangPengganti());

            } else if (napi instanceof NarapidanaPembunuhan) {
                NarapidanaPembunuhan n = (NarapidanaPembunuhan) napi;
                pstmt.setString(11, n.getKategoriPembunuhan());

            } else if (napi instanceof NarapidanaPencurian) {
                NarapidanaPencurian n = (NarapidanaPencurian) napi;
                pstmt.setLong(12, n.getNilaiKerugian());
            }

            int barisBerubah = pstmt.executeUpdate();
            return barisBerubah > 0;

        } catch (SQLException e) {
            System.err.println("Gagal menambah narapidana - " + e.getMessage());
            return false;
        }
    }

    // READ (ID)
    public Narapidana cariNarapidanaById(String idNapi) {
        String sql = "SELECT * FROM narapidana WHERE id_napi = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idNapi);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buatObjekNarapidana(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal mencari narapidana - " + e.getMessage());
        }

        return null;
    }

    // READ (all)
    public List<Narapidana> getAllNarapidana() {
        List<Narapidana> daftarNarapidana = new ArrayList<>();
        String sql = "SELECT * FROM narapidana ORDER BY id_napi";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Narapidana napi = buatObjekNarapidana(rs);

                if (napi != null) {
                    daftarNarapidana.add(napi);
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal mengambil data narapidana - " + e.getMessage());
        }

        return daftarNarapidana;
    }

    // Membuat objek subclass yang sesuai berdasarkan kolom kategori
    private Narapidana buatObjekNarapidana(ResultSet rs) throws SQLException {

        String id = rs.getString("id_napi");
        String nama = rs.getString("nama");
        String kasus = rs.getString("kasus");
        int masa = rs.getInt("masa_tahanan");
        String nomorSel = rs.getString("nomor_sel");
        String blokSel = rs.getString("blok_sel");
        String kategori = rs.getString("kategori");

        switch (kategori) {
            case "NARKOTIKA":
                return new NarapidanaNarkotika(id, nama, kasus, masa, nomorSel, blokSel, rs.getString("jenis_rehabilitasi"));

            case "TERORISME":
                return new NarapidanaTerorisme(id, nama, kasus, masa, nomorSel, blokSel, rs.getString("tingkat_risiko"));

            case "KORUPSI":
                return new NarapidanaKorupsi(id, nama, kasus, masa, nomorSel, blokSel, rs.getLong("uang_pengganti"));

            case "PEMBUNUHAN":
                return new NarapidanaPembunuhan(id, nama, kasus, masa, nomorSel, blokSel, rs.getString("kategori_pembunuhan"));

            case "PENCURIAN":
                return new NarapidanaPencurian(id, nama, kasus, masa, nomorSel, blokSel, rs.getLong("nilai_kerugian"));

            default:
                return null;
        }
    }

    // UPDATE (nomor dan blok sel)
    public boolean updateNarapidana(Narapidana napi) {
        String sql = "UPDATE narapidana "
                + "SET nomor_sel = ?, blok_sel = ? "
                + "WHERE id_napi = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, napi.getNomorSel());
            pstmt.setString(2, napi.getBlokSel());
            pstmt.setString(3, napi.getIdNapi());

            int barisBerubah = pstmt.executeUpdate();
            return barisBerubah > 0;

        } catch (SQLException e) {
            System.err.println("Gagal mengubah data narapidana - " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean hapusNarapidana(String idNapi) {
        String sql = "DELETE FROM narapidana WHERE id_napi = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idNapi);

            int barisBerubah = pstmt.executeUpdate();
            return barisBerubah > 0;

        } catch (SQLException e) {
            System.err.println("Gagal menghapus narapidana - " + e.getMessage());
            return false;
        }
    }
}
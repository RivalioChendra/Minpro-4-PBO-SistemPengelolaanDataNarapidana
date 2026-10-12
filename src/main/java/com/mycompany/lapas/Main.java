package com.mycompany.lapas;

import controller.AdminLapas;
import view.NarapidanaView;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        NarapidanaView view = new NarapidanaView(scanner);
        AdminLapas admin = new AdminLapas(view);

        boolean berjalan = true;

        while (berjalan) {
            view.tampilkanMenu();
            int pilihan = view.bacaAngka("Pilih menu (1-6)");
            view.tampilkanPesan("");

            switch (pilihan) {
                case 1 -> admin.tampilkanNarapidana();
                case 2 -> admin.tambahNarapidana();
                case 3 -> admin.updateNomorSel();
                case 4 -> admin.hapusNarapidana();
                case 5 -> {
                    String kategori = view.bacaTeks(
                            "Kategori (Narkotika/Terorisme/Korupsi/Pembunuhan/Pencurian)");
                    admin.tampilkanNarapidana(kategori);
                }
                case 6 -> {
                    view.tampilkanPesan("\nKeluar dari program...");
                    berjalan = false;
                }

                default -> view.tampilkanPesan(">> Pilihan tidak valid.");
            }
        }

        scanner.close();
    }
}
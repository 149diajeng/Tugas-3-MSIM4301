import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public abstract void tampilMenu();
}

class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Makanan: " + getNama());
        System.out.println("Jenis: " + jenisMakanan);
        System.out.println("Harga: " + getHarga());
        System.out.println();
    }
}

class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Minuman: " + getNama());
        System.out.println("Jenis: " + jenisMinuman);
        System.out.println("Harga: " + getHarga());
        System.out.println();
    }
}

class Diskon extends MenuItem {
    private double diskon;

    public Diskon(String nama, double harga, double diskon) {
        super(nama, harga, "Diskon");
        this.diskon = diskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Diskon: " + getNama());
        System.out.println("Diskon: " + diskon);
        System.out.println();
    }
}

class Menu {
    private ArrayList<MenuItem> daftarMenu = new ArrayList<>();

    public void tambahMenuItem(MenuItem item) {
        daftarMenu.add(item);
    }

    public void tampilMenuRestoran() {
        System.out.println("===== Menu Restoran =====");
        for (MenuItem item : daftarMenu) {
            item.tampilMenu();
        }
    }

    public MenuItem getItemByNama(String nama) {
        for (MenuItem item : daftarMenu) {
            if (item.getNama().equalsIgnoreCase(nama)) {
                return item;
            }
        }
        return null;
    }
}

class Pesanan {
    private ArrayList<MenuItem> itemsPesanan = new ArrayList<>();

    public void tambahPesanan(MenuItem item) {
        itemsPesanan.add(item);
    }

    public double hitungTotal() {
        double total = 0;
        for (MenuItem item : itemsPesanan) {
            total += item.getHarga();
        }
        return total;
    }

    public void tampilStruk() {
        System.out.println("===== Struk Pesanan =====");
        for (MenuItem item : itemsPesanan) {
            item.tampilMenu();
        }
        System.out.println("Total Biaya: " + hitungTotal());
    }
}

public class Main {
    public static void main(String[] args) {
        Menu restoranMenu = new Menu();
        Pesanan pesanan = new Pesanan();

        Scanner scanner = new Scanner(System.in);

        int pilihan = 0;  // Initialize pilihan with an initial value
        do {
            System.out.println("===== Menu Utama =====");
            System.out.println("1. Tambah Item ke Menu");
            System.out.println("2. Tampilkan Menu Restoran");
            System.out.println("3. Pesan Menu");
            System.out.println("4. Tampilkan Struk Pesanan");
            System.out.println("5. Keluar");
            System.out.print("Pilihan: ");

            try {
                pilihan = scanner.nextInt();
                scanner.nextLine();  // consume newline
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                scanner.nextLine();  // consume invalid input
                continue;
            }

            switch (pilihan) {
                case 1:
                    tambahItemMenu(restoranMenu, scanner);
                    break;
                case 2:
                    restoranMenu.tampilMenuRestoran();
                    break;
                case 3:
                    pesanMenu(restoranMenu, pesanan, scanner);
                    break;
                case 4:
                    pesanan.tampilStruk();
                    break;
                case 5:
                    System.out.println("Terima kasih! Sampai jumpa lagi.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih kembali.");
            }

        } while (pilihan != 5);

        // Simpan menu ke file
        simpanMenuKeFile(restoranMenu, "menu.txt");
        // Simpan pesanan ke file
        simpanPesananKeFile(pesanan, "pesanan.txt");
        // Muat menu dari file
        Menu menuDariFile = muatMenuDariFile("menu.txt");
        // Muat pesanan dari file
        Pesanan pesananDariFile = muatPesananDariFile("pesanan.txt");

        // Contoh penggunaan menu dan pesanan yang dimuat dari file
        menuDariFile.tampilMenuRestoran();
        pesananDariFile.tampilStruk();
    }

    private static void tambahItemMenu(Menu menu, Scanner scanner) {
        System.out.println("===== Tambah Item Menu =====");
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Harga: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();  // consume newline

        System.out.println("Pilih kategori:");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        System.out.print("Kategori: ");
        int kategori = scanner.nextInt();
        scanner.nextLine();  // consume newline

        MenuItem item = null;

        switch (kategori) {
            case 1:
                System.out.print("Jenis Makanan: ");
                String jenisMakanan = scanner.nextLine();
                item = new Makanan(nama, harga, jenisMakanan);
                break;
            case 2:
                System.out.print("Jenis Minuman: ");
                String jenisMinuman = scanner.nextLine();
                item = new Minuman(nama, harga, jenisMinuman);
                break;
            case 3:
                System.out.print("Diskon: ");
                double diskon = scanner.nextDouble();
                item = new Diskon(nama, harga, diskon);
                scanner.nextLine();  // consume newline
                break;
            default:
                System.out.println("Kategori tidak valid.");
        }

        if (item != null) {
            menu.tambahMenuItem(item);
            System.out.println("Item berhasil ditambahkan ke menu.");
        }
    }

    private static void pesanMenu(Menu menu, Pesanan pesanan, Scanner scanner) {
        System.out.println("===== Pesan Menu =====");
        menu.tampilMenuRestoran();
        System.out.print("Pilih item yang ingin dipesan (masukkan nama): ");
        String namaItem = scanner.nextLine();
        MenuItem itemDipesan = menu.getItemByNama(namaItem);

        if (itemDipesan != null) {
            pesanan.tambahPesanan(itemDipesan);
            System.out.println("Item berhasil ditambahkan ke pesanan.");
        } else {
            System.out.println("Item tidak ditemukan dalam menu.");
        }
    }

    private static void simpanMenuKeFile(Menu menu, String namaFile) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(namaFile))) {
            outputStream.writeObject(menu);
            System.out.println("Menu berhasil disimpan ke file: " + namaFile);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu ke file: " + e.getMessage());
        }
    }

    private static Menu muatMenuDariFile(String namaFile) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(namaFile))) {
            return (Menu) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Gagal membaca menu dari file: " + e.getMessage());
            return new Menu();  // Mengembalikan menu kosong jika gagal membaca dari file
        }
    }

    private static void simpanPesananKeFile(Pesanan pesanan, String namaFile) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(namaFile))) {
            outputStream.writeObject(pesanan);
            System.out.println("Pesanan berhasil disimpan ke file: " + namaFile);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan pesanan ke file: " + e.getMessage());
        }
    }

    private static Pesanan muatPesananDariFile(String namaFile) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(namaFile))) {
            return (Pesanan) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Gagal membaca pesanan dari file: " + e.getMessage());
            return new Pesanan();  // Mengembalikan pesanan kosong jika gagal membaca dari file
        }
    }
}

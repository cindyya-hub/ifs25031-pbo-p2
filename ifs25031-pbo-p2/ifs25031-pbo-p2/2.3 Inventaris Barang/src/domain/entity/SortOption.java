package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan barang.
 * Setiap opsi membawa comparator-nya sendiri sehingga logika pengurutan
 * terpusat di domain, bukan tersebar sebagai angka ajaib di view/use case.
 */
public enum SortOption {
    /** Urutkan nama barang A-Z. */
    NAME_ASC(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan nama barang Z-A. */
    NAME_DESC(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER).reversed()),

    /** Urutkan jumlah stok dari yang terkecil. */
    QUANTITY_ASC(Comparator.comparingInt(Item::getQuantity)),

    /** Urutkan jumlah stok dari yang terbesar. */
    QUANTITY_DESC(Comparator.comparingInt(Item::getQuantity).reversed());

    /** Comparator yang digunakan untuk mengurutkan daftar Item. */
    private final Comparator<Item> comparator;

    SortOption(Comparator<Item> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator yang sesuai dengan opsi ini. */
    public Comparator<Item> comparator() {
        return comparator;
    }

    /**
     * Memetakan pilihan menu (1-4) ke {@link SortOption} yang sesuai.
     *
     * @return option yang sesuai, atau {@code null} jika pilihan tidak valid
     */
    public static SortOption fromMenuChoice(String choice) {
        return switch (choice.trim()) {
            case "1" -> NAME_ASC;
            case "2" -> NAME_DESC;
            case "3" -> QUANTITY_ASC;
            case "4" -> QUANTITY_DESC;
            default -> null;
        };
    }
}

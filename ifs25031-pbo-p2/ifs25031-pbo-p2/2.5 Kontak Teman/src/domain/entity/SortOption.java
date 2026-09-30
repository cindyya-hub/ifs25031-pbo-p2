package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan kontak.
 * Setiap opsi membawa comparator-nya sendiri sehingga logika pengurutan
 * terpusat di domain, bukan tersebar sebagai angka ajaib di view/use case.
 */
public enum SortOption {
    /** Urutkan nama kontak A-Z. */
    NAME_ASC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan nama kontak Z-A. */
    NAME_DESC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER).reversed());

    /** Comparator yang digunakan untuk mengurutkan daftar Contact. */
    private final Comparator<Contact> comparator;

    SortOption(Comparator<Contact> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator yang sesuai dengan opsi ini. */
    public Comparator<Contact> comparator() {
        return comparator;
    }

    /**
     * Memetakan pilihan menu (1-2) ke {@link SortOption} yang sesuai.
     *
     * @return option yang sesuai, atau {@code null} jika pilihan tidak valid
     */
    public static SortOption fromMenuChoice(String choice) {
        return switch (choice.trim()) {
            case "1" -> NAME_ASC;
            case "2" -> NAME_DESC;
            default -> null;
        };
    }
}

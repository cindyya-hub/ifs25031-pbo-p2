package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan kegiatan.
 * Setiap opsi membawa comparator-nya sendiri sehingga logika pengurutan
 * terpusat di domain, bukan tersebar sebagai angka ajaib di view/use case.
 */
public enum SortOption {
    /** Urutkan berdasarkan hari (Senin -> Minggu), lalu waktu. */
    DAY_ASC(Comparator.comparingInt(Kegiatan::getHariIndex).thenComparing(Kegiatan::getWaktu)),

    /** Urutkan berdasarkan waktu (awal -> akhir). */
    TIME_ASC(Comparator.comparing(Kegiatan::getWaktu)),

    /** Urutkan judul A-Z. */
    TITLE_ASC(Comparator.comparing(Kegiatan::getJudul, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan judul Z-A. */
    TITLE_DESC(Comparator.comparing(Kegiatan::getJudul, String.CASE_INSENSITIVE_ORDER).reversed());

    /** Comparator yang digunakan untuk mengurutkan daftar Kegiatan. */
    private final Comparator<Kegiatan> comparator;

    SortOption(Comparator<Kegiatan> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator yang sesuai dengan opsi ini. */
    public Comparator<Kegiatan> comparator() {
        return comparator;
    }

    /**
     * Memetakan pilihan menu (1-4) ke {@link SortOption} yang sesuai.
     *
     * @return option yang sesuai, atau {@code null} jika pilihan tidak valid
     */
    public static SortOption fromMenuChoice(String choice) {
        return switch (choice.trim()) {
            case "1" -> DAY_ASC;
            case "2" -> TIME_ASC;
            case "3" -> TITLE_ASC;
            case "4" -> TITLE_DESC;
            default -> null;
        };
    }
}

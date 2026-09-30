package usecase;

import domain.entity.Kegiatan;
import domain.entity.SortOption;
import domain.repository.IActivityRepository;
import java.util.List;

/**
 * Use case yang menangani logika bisnis aplikasi jadwal kegiatan.
 * Tidak melakukan I/O — hanya memproses data dan mengembalikan hasil
 * ke layer presenter/view.
 */
public class ActivityUseCase {
    /** Port repository yang di-inject dari luar (Dependency Inversion). */
    private final IActivityRepository kegiatanRepository;

    public ActivityUseCase(IActivityRepository kegiatanRepository) {
        this.kegiatanRepository = kegiatanRepository;
    }

    /** Mengambil semua kegiatan yang terdaftar. */
    public List<Kegiatan> getAllKegiatan() {
        return kegiatanRepository.findAll();
    }

    /** Menambahkan kegiatan baru dan mengembalikan entity yang tersimpan. */
    public Kegiatan addKegiatan(String judul, String hari, String waktu) {
        return kegiatanRepository.save(judul, hari, waktu);
    }

    /**
     * Mengubah data kegiatan berdasarkan ID.
     * Parameter bernilai null berarti field tersebut tidak diubah.
     *
     * @return true jika kegiatan ditemukan (dan field yang tidak null diperbarui)
     */
    public boolean updateKegiatan(int id, String judul, String hari, String waktu) {
        return kegiatanRepository.update(id, judul, hari, waktu);
    }

    /** Menghapus kegiatan berdasarkan ID. */
    public boolean removeKegiatan(int id) {
        return kegiatanRepository.deleteById(id);
    }

    /** Mencari kegiatan yang judulnya mengandung kata kunci (case-insensitive). */
    public List<Kegiatan> searchKegiatan(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return kegiatanRepository.findAll().stream()
                .filter(kegiatan -> kegiatan.getJudul().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    /** Mengurutkan kegiatan sesuai kriteria {@link SortOption} yang dipilih. */
    public List<Kegiatan> sortKegiatan(SortOption option) {
        return kegiatanRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}

package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.repository.IContactRepository;
import java.util.List;

/**
 * Use case yang menangani logika bisnis aplikasi kontak teman.
 * Tidak melakukan I/O — hanya memproses data dan mengembalikan hasil
 * ke layer presenter/view.
 */
public class ContactUseCase {
    /** Port repository yang di-inject dari luar (Dependency Inversion). */
    private final IContactRepository contactRepository;

    public ContactUseCase(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /** Mengambil semua kontak yang terdaftar. */
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    /** Menambahkan kontak baru dan mengembalikan entity yang tersimpan. */
    public Contact addContact(String name, String phone, String email) {
        return contactRepository.save(name, phone, email);
    }

    /**
     * Mengubah data kontak berdasarkan ID.
     * Parameter bernilai null berarti field tersebut tidak diubah.
     *
     * @return true jika kontak ditemukan (dan field yang tidak null diperbarui)
     */
    public boolean updateContact(int id, String name, String phone, String email) {
        return contactRepository.update(id, name, phone, email);
    }

    /** Menghapus kontak berdasarkan ID. */
    public boolean removeContact(int id) {
        return contactRepository.deleteById(id);
    }

    /** Mencari kontak yang namanya mengandung kata kunci (case-insensitive). */
    public List<Contact> searchContacts(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return contactRepository.findAll().stream()
                .filter(contact -> contact.getName().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    /** Mengurutkan kontak sesuai kriteria {@link SortOption} yang dipilih. */
    public List<Contact> sortContacts(SortOption option) {
        return contactRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}

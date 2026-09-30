package usecase;

import domain.entity.Item;
import domain.entity.SortOption;
import domain.repository.IItemRepository;

import java.util.List;
import java.util.Optional;

public class ItemUseCase {

    private final IItemRepository itemRepository;

    public ItemUseCase(
            IItemRepository itemRepository
    ) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item addItem(
            String name,
            int quantity,
            String category
    ) {
        return itemRepository.save(
                name,
                quantity,
                category
        );
    }

    public boolean updateStock(
            int id,
            Integer quantity
    ) {
        Optional<Item> item =
                itemRepository.findById(id);

        if (item.isEmpty()) {
            return false;
        }

        if (quantity != null) {
            itemRepository.updateQuantity(
                    id,
                    quantity
            );
        }

        return true;
    }

    public boolean removeItem(int id) {
        return itemRepository.deleteById(id);
    }

    public List<Item> searchItems(
            String keyword
    ) {
        String lowerKeyword =
                keyword.toLowerCase();

        return itemRepository.findAll()
                .stream()
                .filter(
                    item ->
                        item.getName()
                            .toLowerCase()
                            .contains(lowerKeyword)
                )
                .toList();
    }

    /** Mengurutkan barang sesuai kriteria {@link SortOption} yang dipilih. */
    public List<Item> sortItems(SortOption option) {
        return itemRepository.findAll()
                .stream()
                .sorted(option.comparator())
                .toList();
    }
}

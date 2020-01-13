package dto;

import entities.Item;

/**
 *
 * @author Henning
 */
public class ItemDTO {

    private String name;

    public ItemDTO(Item item) {
        this.name = item.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

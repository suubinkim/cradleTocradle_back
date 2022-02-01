package site.cradle.cradle_back.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.cradle.cradle_back.domain.LocationType;
import site.cradle.cradle_back.domain.favoriteShop;

@Setter
@Getter
@NoArgsConstructor
public class ShopDto {
    private String title;
    private String link;
    private String address;
    private String description;
    private LocationType locationType;
    private int mapx;
    private int mapy;

    public ShopDto(favoriteShop favoriteShop) {
        this.title = favoriteShop.getTitle();
        this.link = favoriteShop.getLink();
        this.address = favoriteShop.getAddress();
        this.description = favoriteShop.getDescription();
        this.locationType = favoriteShop.getLocationType();
    }
}

package site.cradle.cradle_back.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.LocaleType;
import site.cradle.cradle_back.domain.LocationType;
import site.cradle.cradle_back.domain.Shop;

import javax.xml.stream.Location;

@Setter
@Getter
public class ShopDto {
    private String title;
    private String link;
    private String address;
    private String description;
    private String imgLink;
    private LocationType locationType;
    private int mapx;
    private int mapy;

    public ShopDto(Shop shop) {
        this.title = shop.getTitle();
        this.link = shop.getLink();
        this.address = shop.getAddress();
        this.description = shop.getDescription();
        this.imgLink = shop.getImgLink();
        this.locationType = shop.getLocationType();
    }
}

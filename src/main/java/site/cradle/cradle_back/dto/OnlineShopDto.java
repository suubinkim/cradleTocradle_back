package site.cradle.cradle_back.dto;

import lombok.Getter;

@Getter
public class OnlineShopDto {
    private final String title;
    private final String description;
    private final String link;
//    private final String imgLink;

    public OnlineShopDto(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }
}

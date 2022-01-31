package site.cradle.cradle_back.dto;

import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
public class ShopDto {
    private final String title;
    private final String link;
    private final String description;
    private final String address;
    private final String roadAddress;
    private final int mapx;
    private final int mapy;

    public ShopDto(JSONObject shopJson) throws JSONException {
        this.title = shopJson.getString("title");
        this.link = shopJson.getString("link");
        this.description = shopJson.getString("description");
        this.address = shopJson.getString("address");
        this.roadAddress = shopJson.getString("roadAddress");
        this.mapx = shopJson.getInt("mapx");
        this.mapy = shopJson.getInt("mapy");
    }
}

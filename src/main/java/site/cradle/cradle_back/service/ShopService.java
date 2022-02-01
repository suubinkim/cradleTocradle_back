package site.cradle.cradle_back.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import site.cradle.cradle_back.domain.LocationType;
import site.cradle.cradle_back.domain.favoriteShop;
import site.cradle.cradle_back.domain.User;
import site.cradle.cradle_back.dto.OnlineShopDto;
import site.cradle.cradle_back.dto.ShopDto;
import site.cradle.cradle_back.repository.favoriteShopRepository;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final favoriteShopRepository favoriteShopRepository;

    //네이버 지역 검색 api 사용
    public String naverSearchShop(String location) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", location + " 제로웨이스트 샵")
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode(StandardCharsets.UTF_8)
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "qXCHBCMwdV9Zph1ZEwsV")
                .header("X-Naver-Client-Secret", "IoxpIY6dOF")
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        return result.getBody();
    }

    public void saveShop(ShopDto requestDto, User user) {
        favoriteShop wish = new favoriteShop(requestDto.getTitle(),
                requestDto.getLink(),
                requestDto.getAddress(),
                user,
                requestDto.getLocationType(),
                requestDto.getDescription()
        );
        favoriteShopRepository.save(wish);
    }

    public void deleteShop(String title, User user) {
        favoriteShop byTitleAndUser = favoriteShopRepository.findByTitleAndUser(title, user);
        favoriteShopRepository.delete(byTitleAndUser);
    }

    public List<ShopDto> getShops(User user) {
        List<favoriteShop> allByUser = favoriteShopRepository.findAllByUser(user);
        return allByUser
                .stream()
                .map(ShopDto::new)
                .collect(Collectors.toList());
    }

    //온라인사이트 웹크롤링
    public List<OnlineShopDto> crawling() throws IOException {
        String url = "https://ad.search.naver.com/search.naver?where=ad&query=%EC%A0%9C%EB%A1%9C%EC%9B%A8%EC%9D%B4%EC%8A%A4%ED%8A%B8%EC%83%B5&referenceId=hQADhdp0Jy0ssPuRd34ssssss9C-165294";

        Document doc = Jsoup.connect(url).get();
        Elements contents = doc.select("ol[class=lst_type]").select("li div[class=inner]");

        ArrayList<OnlineShopDto> OnlineShopList = new ArrayList<>();

        for (Element content : contents) {
            String title = content.select("a[class=lnk_tit]").text();
            String des = content.select("div[class=ad_dsc]").select("p[class=ad_dsc_inner]").text();
            String link = content.select("div[class=url_area]").select("a[class=url]").text();
            OnlineShopList.add(new OnlineShopDto(title, des, link));
        }
        return OnlineShopList;
    }
}

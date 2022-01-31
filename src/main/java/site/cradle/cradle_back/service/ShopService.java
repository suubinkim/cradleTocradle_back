package site.cradle.cradle_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import site.cradle.cradle_back.domain.LocationType;
import site.cradle.cradle_back.domain.Shop;
import site.cradle.cradle_back.domain.User;
import site.cradle.cradle_back.dto.ShopDto;
import site.cradle.cradle_back.repository.ShopRepository;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

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
        Shop wish = new Shop(requestDto.getTitle(),
                requestDto.getLink(),
                requestDto.getAddress(),
                user,
                LocationType.OFFLINE,
                requestDto.getDescription(),
                requestDto.getImgLink()
        );
        shopRepository.save(wish);
    }

    public List<ShopDto> getShops(User user) {
        List<Shop> allByUser = shopRepository.findAllByUser(user);
        return allByUser
                .stream()
                .map(ShopDto::new)
                .collect(Collectors.toList());
    }
}

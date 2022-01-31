package site.cradle.cradle_back.service;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import site.cradle.cradle_back.domain.LocationType;
import site.cradle.cradle_back.domain.Shop;
import site.cradle.cradle_back.domain.User;
import site.cradle.cradle_back.domain.UserRole;
import site.cradle.cradle_back.repository.ShopRepository;
import site.cradle.cradle_back.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void 카카오찾기() {
        //given
        String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/keyword.json?query=";
        String GEOCODE_USER_INFO = "KakaoAK dc0eb7328ada55278701dc6a126c26cc";

        URL obj;
        try {
            //인코딩한 String을 넘겨야 원하는 데이터를 받을 수 있다.
            String keyword = URLEncoder.encode("대전 제로웨이스트", "UTF-8");
            String request = URLEncoder.encode("sort=distance", "UTF-8");
            obj = new URL(GEOCODE_URL + keyword + "&" + request);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //get으로 받아오면 된다. 자세한 사항은 카카오개발자센터에 나와있다.
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", GEOCODE_USER_INFO);
            con.setRequestProperty("content-type", "application/json");
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);
            Charset charset = StandardCharsets.UTF_8;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //response 객체를 출력해보자
            System.out.println(response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void 네이버찾기() {
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", "서울특별시 강남구 제로웨이스트 샵")
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode(StandardCharsets.UTF_8)
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> req = (RequestEntity<Void>) RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "qXCHBCMwdV9Zph1ZEwsV")
                .header("X-Naver-Client-Secret", "IoxpIY6dOF")
                .build();

        //주소 로그 확인
//        log.info("uri : {}", uri);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        System.out.println(result.getBody());
    }

    @Test
    public void 가게저장() {
        //given
        User user = new User("gg","gg@gg","gg", UserRole.USER);
        userRepository.save(user);
        Shop shop = new Shop("가게이름",
                "requestDto.getLink()",
                "requestDto.getAddress()",
                user,
                LocationType.OFFLINE,
                " ",
                "b");

        //when
        shopRepository.save(shop);
        //then
        assertThat(shopRepository.findAll().size()).isEqualTo(1);

    }
}

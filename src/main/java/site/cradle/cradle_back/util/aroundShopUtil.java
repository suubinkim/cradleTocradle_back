package site.cradle.cradle_back.util;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import site.cradle.cradle_back.dto.ShopDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class aroundShopUtil {
    private static String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/keyword.json?query=";
    private static String GEOCODE_USER_INFO = "KakaoAK dc0eb7328ada55278701dc6a126c26cc";

    public static StringBuilder kakaoSearchShop() {
        URL obj;
        try {
            //인코딩한 String을 넘겨야 원하는 데이터를 받을 수 있다.
            String keyword = URLEncoder.encode("제로웨이스트", "UTF-8");
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
            } //response 객체를 출력해보자
            System.out.println(response);
            return response;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

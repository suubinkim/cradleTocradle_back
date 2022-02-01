package site.cradle.cradle_back.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class aroundShopUtilTest {

    @Test
    public void 좌표변환(){
        //given
        String URL = "http://dapi.kakao.com/v2/local/geo/transcoord.json?x=";
        String USER_INFO = "KakaoAK dc0eb7328ada55278701dc6a126c26cc";

        java.net.URL obj;
        try {
            //인코딩한 String을 넘겨야 원하는 데이터를 받을 수 있다.
            String mapx = URLEncoder.encode("325614", "UTF-8");
            String mapy = URLEncoder.encode("&y=464927", "UTF-8");
            String input_coord = URLEncoder.encode("&input_coord=KTM", "UTF-8");
            obj = new URL(URL + mapx + mapy + input_coord);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //get으로 받아오면 된다. 자세한 사항은 카카오개발자센터에 나와있다.
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", USER_INFO);
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
}

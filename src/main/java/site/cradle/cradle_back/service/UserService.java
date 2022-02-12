package site.cradle.cradle_back.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import site.cradle.cradle_back.domain.User;
import site.cradle.cradle_back.domain.UserRole;
import site.cradle.cradle_back.dto.Request.SignupRequestDto;
import site.cradle.cradle_back.repository.UserRepository;
import site.cradle.cradle_back.util.GoogleOAuthRequest;

import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${sns.google.token.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;

    private final HttpServletResponse response;

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();

        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(username, email, password, UserRole.USER);
        userRepository.save(user);
    }

    public void checkEmail(String email) {
        //이메일 중복체크
        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
    }

    public void getOauthRedirectURL() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile%20email");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        String redirect = GOOGLE_SNS_BASE_URL + "?" + parameterString;
        response.sendRedirect(redirect);
    }

    public String requestAccessToken(String code) throws JSONException, IOException {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JSONObject responseBody = new JSONObject(responseEntity.getBody());
            String jwtToken = responseBody.getString("id_token");
            String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com" + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();

            String resultJson = restTemplate.getForObject(requestUrl, String.class);
            JSONObject result = new JSONObject(resultJson);

            log.info(String.valueOf(result));

            // 구글 정보조회 성공
            if (resultJson != null) {

                //  회원 고유 식별자
                String googleUniqueNo = result.getString("sub");

                return String.valueOf(result);


                /**

                 TO DO : 리턴받은 googleUniqueNo 해당하는 회원정보 조회 후 로그인 처리 후 메인으로 이동

                 */


                // 구글 정보조회 실패
            }
        }
        return "실패";
    }

}

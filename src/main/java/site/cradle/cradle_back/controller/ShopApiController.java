package site.cradle.cradle_back.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.cradle.cradle_back.dto.OnlineShopDto;
import site.cradle.cradle_back.dto.ShopDto;
import site.cradle.cradle_back.dto.Response.ResultResponseDto;
import site.cradle.cradle_back.security.UserDetailsImpl;
import site.cradle.cradle_back.service.ShopService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShopApiController {

    private final ShopService shopService;


//    //선택한 지역 주변의 가게
//    @GetMapping(value = "/shop/offline")
//    public StringBuilder offlineShop(){
//        return aroundShopUtil.kakaoSearchShop();
//    }

    //선택한 지역 주변의 가게
    @GetMapping(value = "/shop/offline")
    public String offlineShop(@RequestParam String location) {
        return shopService.naverSearchShop(location);
    }

    //오프라인 찜한 가게 등록
    @PostMapping(value = "/shop/favorite")
    public ResultResponseDto favoriteShop(@RequestBody ShopDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        shopService.saveShop(requestDto, userDetails.getUser());
        return new ResultResponseDto("success", "찜한 가게로 등록되었습니다.");
    }

    //찜한 가게 가져오기
    @GetMapping(value = "/shop/favorite")
    public List<ShopDto> shops(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return shopService.getShops(userDetails.getUser());
    }

    //온라인 사이트 가져오기
    @GetMapping(value = "/shop/online")
    public List<OnlineShopDto> ShopCrawling() throws IOException {
        return shopService.crawling();
    }

    @DeleteMapping(value = "/shop/favorite")
    public ResultResponseDto deleteShop(@RequestParam("title") String title,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        shopService.deleteShop(title, userDetails.getUser());
        return new ResultResponseDto("success", "삭제되었습니다.");
    }
}

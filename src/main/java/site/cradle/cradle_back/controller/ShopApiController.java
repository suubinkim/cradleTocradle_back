package site.cradle.cradle_back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.cradle.cradle_back.util.aroundShopUtil;

@RequiredArgsConstructor
@RestController
public class ShopApiController {


//    //선택한 지역 주변의 가게
//    @GetMapping(value = "/shop/offline")
//    public StringBuilder offlineShop(){
//        return aroundShopUtil.kakaoSearchShop();
//    }

    //선택한 지역 주변의 가게
    @GetMapping(value = "/shop/offline")
    public String offlineShop(@RequestParam String location) {
        return aroundShopUtil.naverSearchShop(location);
    }
}

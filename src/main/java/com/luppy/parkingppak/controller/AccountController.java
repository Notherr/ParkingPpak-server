package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.dto.LoginRequestDto;
import com.luppy.parkingppak.domain.dto.LoginResponseDto;
import com.luppy.parkingppak.service.AccountService;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AccountDto dto) {

        AccountDto registeredAccount = accountService.joinAccount(dto);

        if(registeredAccount == null) return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
        else return ResponseEntity.ok().body(registeredAccount);
    }

    @PostMapping("/accounts/cards/{email}/{card}")
    public ResponseEntity<?> registerCard(@PathVariable String email, @PathVariable String card) {

        String registeredCard = accountService.registerCard(email, card);

        return ResponseEntity.ok().body(registeredCard);
    }

    @PostMapping("/accounts/oil-type/{email}/{oilType}")
    public ResponseEntity<?> registerOilType(@PathVariable String email, @PathVariable String oilType) {

        String registeredOilType = accountService.registerOilType(email);

        return ResponseEntity.ok().body(registeredOilType);
    }

    @PostMapping("/accounts/navi/{email}/{navi}")
    public ResponseEntity<?> registerNavi(@PathVariable String email, @PathVariable String navi) {

        String registeredNavi = accountService.registerNavi(email);

        return ResponseEntity.ok().body(registeredNavi);
    }
    /*

    각각의 정보를 조회하는 api를 하나씩 만들어주는 것이 비효율적이라 생각이 듬.
    로그인 시, 모든 정보를 한번에 반환해 주는 것이 좋을까 ?

    @GetMapping("/accounts/cards/{email}")
    public ResponseEntity<?> getCard(@PathVariable String email) {

        String card = accountService.getCard(email);

        return
    }

    @GetMapping("/accounts/oil-type/{email}")
    public ResponseEntity<?> getOilType(@PathVariable String email) {
        accountService.getOilType(email);
    }

    @GetMapping("/accounts/navi/{email}")
    public ResponseEntity<?> getNavi(@PathVariable String email) {
        accountService.getNavi(email);
    }
*/

    /*
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {

        AccountDto accountDto = accountService.login(dto);

        if (accountDto == null) return ResponseEntity.notFound().build();
        else {
            String accessToken = jwtUtil.createToken(accountDto.getId(), accountDto.getName());

            return ResponseEntity.ok().body(
                    LoginResponseDto.builder()
                            .accessToken(accessToken)
                            .build());
        }
    }
     */
}

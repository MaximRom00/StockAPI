package by.rom.stockapi.controller;

import by.rom.stockapi.model.User;
import by.rom.stockapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/crypto")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save_user")
    public User saveUser(@Valid @RequestBody User user){
        return userService.saveUser(user);
    }

    @PostMapping("/save_account")
    public String saveCryptoToStack(@RequestParam String email,
                                    @RequestParam(name = "crypto_short") String cryptoShort,
                                    @RequestParam double amount,
                                    @RequestParam(required = false) String currency){

        return userService.addCryptoAccount(email, cryptoShort, amount, currency);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email){
        userService.deleteUser(email);
        return new ResponseEntity<>("Delete was successful", HttpStatus.OK);
    }
}

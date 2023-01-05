package codestates.main22.personalDm.controller;

import codestates.main22.personalDm.mapper.PersonalDmMapper;
import codestates.main22.personalDm.service.PersonalDmService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personalDM")
@Validated
@AllArgsConstructor
public class PersonalDmController {
    private final PersonalDmService personalDmService;
    private final PersonalDmMapper personalDmMapper;
}

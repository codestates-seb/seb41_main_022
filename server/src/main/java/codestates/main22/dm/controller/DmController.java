package codestates.main22.dm.controller;

import codestates.main22.dm.mapper.DmMapper;
import codestates.main22.dm.service.DmService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dm")
@Validated
@AllArgsConstructor
public class DmController {
    private final DmService dmService;
    private final DmMapper dmMapper;
}

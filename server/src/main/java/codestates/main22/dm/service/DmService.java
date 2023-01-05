package codestates.main22.dm.service;

import codestates.main22.dm.repository.DmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DmService {
    private DmRepository dmRepository;
}

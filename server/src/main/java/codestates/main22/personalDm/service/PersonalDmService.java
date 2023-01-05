package codestates.main22.personalDm.service;

import codestates.main22.personalDm.repository.PersonalDmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonalDmService {
    private PersonalDmRepository personalDmRepository;
}

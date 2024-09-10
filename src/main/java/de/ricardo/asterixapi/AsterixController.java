package de.ricardo.asterixapi;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asterix/characters")
public class AsterixController {

    private final CharacterRepository characterRepository;

    public AsterixController(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @GetMapping
    public List<Character> getCharacters() {
        return characterRepository.findAll();
    }

    @PostMapping
    public Character addCharacter(@RequestBody Character character) {
        return characterRepository.save(character);
    }

    


}

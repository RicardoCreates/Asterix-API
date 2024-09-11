package de.ricardo.asterixapi;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asterix/characters")
@RequiredArgsConstructor
public class AsterixController {


    private final CharacterService characterService;



    @GetMapping
    public List<Character> getCharacters() {
        return characterService.getCharacters();
    }

    @PostMapping
    public Character addCharacter(@RequestBody CharacterDTO characterDTO) {
        return characterService.addCharacter(characterDTO);
    }

    @PutMapping("/{id}")
    public Character updateCharacter(@PathVariable String id, @RequestBody Character updatedCharacter){
        return  characterService.updateCharacter(id, updatedCharacter);
    }

    @DeleteMapping("/{id}")
    public void deleteCharacter(@PathVariable String id) {
        characterService.deleteCharacter(id);
    }
}

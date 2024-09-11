package de.ricardo.asterixapi;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;

    public List<Character> getCharacters() {
        return characterRepository.findAll();
    }

    public Optional<Character> findById(String id) {
        return characterRepository.findById(id);
    }

    public Character addCharacter(CharacterDTO characterDTO) {
        String generateId = UUID.randomUUID().toString();
        Character character = new Character(generateId, characterDTO.getName(), characterDTO.getAge(), characterDTO.getProfession());
        return characterRepository.save(character);
    }

    public Character updateCharacter(@PathVariable String id, @RequestBody Character updatedCharacter){
        return  characterRepository.save(updatedCharacter);
    }

    public void deleteCharacter(@PathVariable String id) {
        characterRepository.deleteById(id);
    }
}

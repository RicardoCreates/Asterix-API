package de.ricardo.asterixapi;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    private final CharacterRepository characterRepository = mock(CharacterRepository.class);
    private final CharacterService characterService = new CharacterService(characterRepository);

    @Test
    void getCharacters() {
        //GIVEN
        Character character = new Character("1", "Asterix", 30, "Warrior");
        List<Character> expected = List.of(character);
        //WHEN
        when(characterRepository.findAll()).thenReturn(expected);
        List<Character> actual = characterService.getCharacters();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        //GIVEN
        String id = "1";
        Character expected = new Character("1", "Asterix", 30, "Warrior");
        //WHEN
        when(characterRepository.findById(id)).thenReturn(Optional.of(expected));
        Character actual = characterService.findById(id).orElseThrow(NoSuchElementException::new);
        //THEN
        verify(characterRepository).findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void addCharacter() {
        //GIVEN
        CharacterDTO characterDTO = new CharacterDTO( "1", "Asterix", 30, "Warrior");
        Character expected = new Character("1", "Asterix", 30, "Warrior");
        //WHEN
        when(characterRepository.save(any(Character.class))).thenReturn(expected);
        Character actual = characterService.addCharacter(characterDTO);
        //THEN
        verify(characterRepository).save(any(Character.class));
        assertEquals(expected, actual);
    }

    @Test
    void updateCharacter() {
        //GIVEN
        Character updateCharacter = new Character("1", "Asterix", 30, "Chief");
        //WHEN
        when(characterRepository.save(any(Character.class))).thenReturn(updateCharacter);
        Character actual = characterService.updateCharacter("1", updateCharacter);
        //THEN
        verify(characterRepository).save(any(Character.class));
        assertEquals(updateCharacter, actual);
    }

    @Test
    void deleteCharacter() {
        //GIVEN
        String id = "1";
        Character existingCharacter = new Character("1", "Asterix", 30, "Warrior");
        //WHEN
        when(characterRepository.findById(id)).thenReturn(Optional.of(existingCharacter));
        doNothing().when(characterRepository).deleteById(id);
        //THEN
        characterService.deleteCharacter(id);
        verify(characterRepository).deleteById(id);
    }
}
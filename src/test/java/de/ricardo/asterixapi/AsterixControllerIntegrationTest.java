package de.ricardo.asterixapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AsterixControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CharacterRepository characterRepository;

    @Test
    @DirtiesContext
    void testGetAllCharacters_returnsEmptyListOnEmptyRepo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void testGetCharacterById_returnsCharacter() throws Exception {
        Character character = new Character("1", "Asterix", 30, "Warrior");
        characterRepository.save(character);
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                        "id": "1",
                        "name": "Asterix",
                        "age": 30,
                        "profession": "Warrior"
                    }
                """));
    }

    @Test
    @DirtiesContext
    void testGetCharacterById_returnsNotFound() throws Exception {
        assertThrows(
                Exception.class,
                () -> mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/1"))
                        .andExpect(status().isNotFound())
        );
    }

    @Test
    @DirtiesContext
    void testSaveCharacter() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/asterix/characters")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                        {
                            "name": "Abraracourcix",
                            "age": 50,
                            "profession": "Herbalist"
                        }
                    """)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                        "name": "Abraracourcix",
                        "age": 50,
                        "profession": "Herbalist"
                    }
                """));
    }

    @Test
    @DirtiesContext
    void updateCharacter() throws Exception{
        characterRepository.save(new Character("1", "Asterix", 30, "Warrior"));
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/asterix/characters/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(""" 
                                {
                                    "name": "Abraracourcix",
                                    "age": 50,
                                    "profession": "Herbalist"
                                }
                            """)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                    "id": "1",
                    "name": "Abraracourcix",
                    "age": 50,
                    "profession": "Herbalist"
                    }
                """));
    }

    @Test
    @DirtiesContext
    void deleteCharacter() throws Exception {
        characterRepository.save(new Character("1", "Asterix", 30, "Warrior"));
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/asterix/characters/1")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        // Make sure that the character is removed from the db and is no longer retrievable in the test
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/asterix/1")).andExpect(status().isNotFound());
            fail();
        } catch (Exception e) {
            // Ignore as this means it was as expected
        }
    }
}

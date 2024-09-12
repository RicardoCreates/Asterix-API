package de.ricardo.asterixapi;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CharacterDTO {
    private String id;
    private String name;
    private int age;
    private String profession;
}

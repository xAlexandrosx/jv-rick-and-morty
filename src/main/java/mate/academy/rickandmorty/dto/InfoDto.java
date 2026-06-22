package mate.academy.rickandmorty.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoDto {
    private int count;
    private int pages;
    private String next;
    private String prev;
}

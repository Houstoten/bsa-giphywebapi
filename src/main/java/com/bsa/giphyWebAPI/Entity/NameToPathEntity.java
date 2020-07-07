package com.bsa.giphyWebAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class NameToPathEntity {
    private String name;
    private String path;

    public static List<NameToPathEntity> asList(NameToPathEntity nameToPathEntity){
        List<NameToPathEntity> list = new ArrayList<>();
        list.add(nameToPathEntity);
        return list;
    }
}

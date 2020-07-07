package com.bsa.giphyWebAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QueryFolderEntity {
    private String query;
    private List<NameToPathEntity> files;
}


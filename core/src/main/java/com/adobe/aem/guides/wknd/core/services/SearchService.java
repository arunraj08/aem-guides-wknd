package com.adobe.aem.guides.wknd.core.services;

import java.util.List;

public interface SearchService {

    List<String> searchByKeyword(String keyword);
}
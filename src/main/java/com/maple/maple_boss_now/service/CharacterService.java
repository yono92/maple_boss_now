package com.maple.maple_boss_now.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterService {

    @Value("${api.domain}")
    private String apiDomain;

    @Value("${api.test-token}")
    private String apiToken;

}

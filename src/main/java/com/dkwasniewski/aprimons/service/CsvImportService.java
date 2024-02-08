package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokeball;
import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.repository.PokeballRepository;
import com.dkwasniewski.aprimons.repository.PokemonRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CsvImportService {

    public CsvImportService(PokemonRepository pokemonRepository, PokeballRepository pokeballRepository) {
        this.pokemonRepository = pokemonRepository;
        this.pokeballRepository = pokeballRepository;
    }

    private final PokemonRepository pokemonRepository;
    private final PokeballRepository pokeballRepository;


    public void importDataFromCsv(String csvFilePath) throws IOException {
        try (FileReader fileReader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
             List<String> pokeballs = csvParser.getHeaderNames();
             pokeballs = pokeballs.subList(4, 15);
            for (String pokeballName: pokeballs ) {
                Pokeball pokeball = new Pokeball(pokeballName, pokeballName + ".png");
                pokeballRepository.save(pokeball);
            }
            for (CSVRecord csvRecord : csvParser) {
                int dexNumber = Integer.parseInt(csvRecord.get("DEX"));
                String name = csvRecord.get("Name");
                String image = csvRecord.get("Image");
                List<Pokeball> list = new ArrayList<>();
                for (String pokeball: pokeballs) {
                    if(csvRecord.get(pokeball).equals("True")){
                        Pokeball pokeballFromBase = pokeballRepository.findByName(pokeball);
                        list.add(pokeballFromBase);
                    }
                }
                Pokemon pokemon = new Pokemon(dexNumber, name, image);
                pokemon.setPokeballs(list);
                pokemonRepository.save(pokemon);
            }
        }
    }
}
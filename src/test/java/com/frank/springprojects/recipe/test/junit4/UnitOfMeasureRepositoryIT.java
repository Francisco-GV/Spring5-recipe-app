package com.frank.springprojects.recipe.test.junit4;

import com.frank.springprojects.recipe.model.UnitOfMeasure;
import com.frank.springprojects.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByDescriptionTeaspoon() {

        Optional<UnitOfMeasure> unitOfMeasureOptional =
                unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon",
                unitOfMeasureOptional.orElseThrow().getDescription());

    }

    @Test
    public void findByDescriptionCup() {

        Optional<UnitOfMeasure> unitOfMeasureOptional =
                unitOfMeasureRepository.findByDescription("Cup");

        assertEquals("Cup",
                unitOfMeasureOptional.orElseThrow().getDescription());

    }
}
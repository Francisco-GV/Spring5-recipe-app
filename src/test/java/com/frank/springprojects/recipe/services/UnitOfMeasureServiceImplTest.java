package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.commands.UnitOfMeasureCommand;
import com.frank.springprojects.recipe.converters.unitofmeasure.UnitOfMeasureToUnitOfMeasureCommand;
import com.frank.springprojects.recipe.model.UnitOfMeasure;
import com.frank.springprojects.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUnitOfMeasures() {
        // given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
            unitOfMeasure.setId((long) i + 1);
            unitOfMeasures.add(unitOfMeasure);
        }

        // when
        Mockito.when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
        Set<UnitOfMeasureCommand> commandSet = unitOfMeasureService.listAllUnitOfMeasures();

        // then
        assertEquals(2, commandSet.size());
        Mockito.verify(unitOfMeasureRepository, Mockito.times(1)).findAll();
    }
}
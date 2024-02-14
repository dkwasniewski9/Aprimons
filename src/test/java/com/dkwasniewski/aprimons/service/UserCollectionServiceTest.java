package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.UserCollection;
import com.dkwasniewski.aprimons.repository.UserCollectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserCollectionServiceTest {

    @Mock
    UserCollectionRepository userCollectionRepository;
    @InjectMocks
    UserCollectionService userCollectionService;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    public void testSaveUserCollection() {
        UserCollection userCollection = new UserCollection("exampleUserId");

        userCollectionService.saveUserCollection(userCollection);

        verify(userCollectionRepository).save(userCollection);
    }

    @Test
    public void testGetUserCollectionExists() {
        String userId = "exampleUserId";
        UserCollection existingUserCollection = new UserCollection(userId);

        when(userCollectionRepository.getUserCollectionByUserId(userId)).thenReturn(existingUserCollection);

        UserCollection resultUserCollection = userCollectionService.getUserCollection(userId);

        verify(userCollectionRepository, never()).save(any(UserCollection.class));

        assertEquals(existingUserCollection, resultUserCollection);
    }

    @Test
    public void testGetUserCollectionNotExist() {
        String userId = "exampleUserId";

        when(userCollectionRepository.getUserCollectionByUserId(userId)).thenReturn(null);
        when(userCollectionRepository.save(any(UserCollection.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserCollection resultUserCollection = userCollectionService.getUserCollection(userId);

        verify(userCollectionRepository).save(any(UserCollection.class));

        assertNotNull(resultUserCollection);
        assertEquals(userId, resultUserCollection.getUserId());
    }
}
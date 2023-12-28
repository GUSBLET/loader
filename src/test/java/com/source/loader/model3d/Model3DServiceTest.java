package com.source.loader.model3d;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Model3DServiceTest {

    @InjectMocks
    Model3DService model3DService;

    @Mock
    Model3DRepository model3DRepository;

    @Test
    void testUpdateModelPriorityById() {
        // Arrange
        String id = "8a32e52a-289d-4224-857c-fd16a1729d7a";
        Long priority = 10L;
        Long lastPriority = 18L;
        UUID uuidId = UUID.fromString(id);
        Model3D model3D = new Model3D();
        model3D.setId(uuidId);
        model3D.setPriority(lastPriority + 1);

        // Mocking repository behavior
        when(model3DRepository.findById(uuidId)).thenReturn(Optional.of(model3D));

        // Act
        model3DService.updateModelPriorityById(id, priority, lastPriority);

        // Assert
        verify(model3DRepository, times(1)).updateModel3DPriorityById(uuidId, priority);
        if (priority < lastPriority) {
            verify(model3DRepository, times(1)).updateModel3DSequenceWherePriorityMoreCurrentAndLessLast(priority, lastPriority, uuidId);
            verify(model3DRepository, never()).updateModel3DSequenceWherePriorityLessCurrentAndMoreLast(anyLong(), anyLong(), any(UUID.class));
        } else {
            verify(model3DRepository, times(1)).updateModel3DSequenceWherePriorityLessCurrentAndMoreLast(priority, lastPriority, uuidId);
            verify(model3DRepository, never()).updateModel3DSequenceWherePriorityMoreCurrentAndLessLast(anyLong(), anyLong(), any(UUID.class));
        }
    }
}
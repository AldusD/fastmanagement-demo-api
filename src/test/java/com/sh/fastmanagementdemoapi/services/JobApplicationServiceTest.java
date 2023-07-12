package com.sh.fastmanagementdemoapi.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sh.fastmanagementdemoapi.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JobApplicationServiceTest {
    private JobApplicationService hrSystem;

    @BeforeEach
    public void setup() {
        hrSystem = new JobApplicationService();
    }

    @Test
    public void testStartApplication() throws Exception {
        // Test correct input, conflict, invalid input
        int id = hrSystem.startApplication("candidate1");
        assertEquals(Status.RECEBIDO.name(), hrSystem.checkApplicationStatus(id));
        assertThrows(Exception.class, () -> hrSystem.startApplication("candidate1"));
        assertThrows(Exception.class, () -> hrSystem.startApplication(null));
        assertThrows(Exception.class, () -> hrSystem.startApplication(""));
        assertThrows(Exception.class, () -> hrSystem.startApplication(" "));
    }

    @Test
    public void testScheduleInterview() throws Exception {
        // Test correct input, invalid candidate
        int id1 = hrSystem.startApplication("candidate1");
        int id2 = hrSystem.startApplication("candidate2");
        hrSystem.scheduleInterview(id2);

        assertDoesNotThrow(() -> hrSystem.scheduleInterview(id1));
        assertThrows(Exception.class, () -> hrSystem.scheduleInterview((id2)));
        assertThrows(Exception.class, () -> hrSystem.scheduleInterview(-1));
        assertThrows(Exception.class, () -> hrSystem.scheduleInterview(0));
        assertThrows(Exception.class, () -> hrSystem.scheduleInterview(100));
    }

    @Test
    public void testEndApplicationAndCheckApplicationStatus() throws Exception {
        // Test endApplication + checkApplicationStatus  (correct input, invalid candidate)
        int id1 = hrSystem.startApplication("candidate1");
        int id2 = hrSystem.startApplication("candidate2");
        hrSystem.scheduleInterview(id2);
        int id3 = hrSystem.startApplication("candidate3");
        hrSystem.scheduleInterview(id3);
        hrSystem.approveCandidate(id3);
        assertEquals(Status.RECEBIDO.name(), hrSystem.checkApplicationStatus(id1));
        assertEquals(Status.QUALIFICADO.name(), hrSystem.checkApplicationStatus(id2));
        assertEquals(Status.APROVADO.name(), hrSystem.checkApplicationStatus(id3));
        hrSystem.endApplication(id1);
        hrSystem.endApplication(id2);
        hrSystem.endApplication(id3);
        assertThrows(Exception.class, () -> hrSystem.checkApplicationStatus(id1));
        assertThrows(Exception.class, () -> hrSystem.checkApplicationStatus(id2));
        assertThrows(Exception.class, () -> hrSystem.checkApplicationStatus(id3));
        assertThrows(Exception.class, () -> hrSystem.endApplication(100));
    }

    @Test
    public void testApproveCandidate() throws Exception {
        // Test correct input, invalid candidate
        int id = hrSystem.startApplication("candidate");
        assertThrows(Exception.class, () -> hrSystem.approveCandidate(id));
        hrSystem.scheduleInterview(id);
        hrSystem.approveCandidate(id);
        assertThrows(Exception.class, () -> hrSystem.approveCandidate(id));
    }

    @Test
    public void testSendApproved() throws Exception {
        int id1 = hrSystem.startApplication("candidate1");
        hrSystem.scheduleInterview(id1);
        hrSystem.approveCandidate(id1);
        int id2 = hrSystem.startApplication("candidate2");
        hrSystem.scheduleInterview(id2);
        hrSystem.approveCandidate(id2);
        List<String> output = hrSystem.sendApproved();
        List<String> expectedOutput = Arrays.asList("candidate1", "candidate2");
        assertTrue(output.equals(expectedOutput));
    }
}

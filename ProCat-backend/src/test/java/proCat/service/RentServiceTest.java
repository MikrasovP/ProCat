package proCat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import proCat.entity.Rent;
import proCat.exception.PaymentTimeOutException;
import proCat.repository.AvailabilityStatusRepository;
import proCat.repository.RentInventoryRepository;
import proCat.repository.RentRepository;
import proCat.repository.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class RentServiceTest {
    @InjectMocks
    private RentService rentService;

    @Mock
    private RentInventoryRepository inventoryRepository;
    @Mock
    private RentRepository rentRepository;

    @Mock
    private AvailabilityStatusRepository availabilityStatusRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    public void payForRentTimeOut() {
        Rent rent = new Rent();
        rent.setRentId(1L);
        rent.setEndTime(LocalDateTime.now().minusSeconds(180));
        Mockito.doReturn(rent).when(rentRepository).getByRentId(1L);
        assertThrows(PaymentTimeOutException.class,()->{rentService.payForRent(1L);});
    }
}
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.controller.OrderController;
import com.example.model.Order;
import com.example.service.OrderService;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Create a new order
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderType("Dine-In");
        order.setTableId(5);
        order.setTotal(100.0);

        // Define behavior for the mock service
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        // Perform POST request and check results
        mockMvc.perform(post("/orders")
                .contentType("application/json")
                .content("{\"customerId\":1,\"orderType\":\"Dine-In\",\"tableId\":5,\"total\":100.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.orderType").value("Dine-In"))
                .andExpect(jsonPath("$.tableId").value(5))
                .andExpect(jsonPath("$.total").value(100.0));
    }

    @Test
    public void testGetOrderById() throws Exception {
        // Create a new order
        Order order = new Order();
        order.setId(1);
        order.setCustomerId(1);
        order.setOrderType("Dine-In");
        order.setTableId(5);
        order.setTotal(100.0);

        // Define behavior for the mock service
        when(orderService.getOrderById(1)).thenReturn(order);

        // Perform GET request and check results
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.orderType").value("Dine-In"))
                .andExpect(jsonPath("$.tableId").value(5))
                .andExpect(jsonPath("$.total").value(100.0));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        // Create a new order
        Order order = new Order();
        order.setId(1);
        order.setCustomerId(1);
        order.setOrderType("Dine-In");
        order.setTableId(5);
        order.setTotal(100.0);

        // Define behavior for the mock service
        when(orderService.updateOrder(any(Order.class))).thenReturn(order);

        // Perform PUT request and check results
        mockMvc.perform(put("/orders/1")
                .contentType("application/json")
                .content("{\"id\":1,\"customerId\":1,\"orderType\":\"Dine-In\",\"tableId\":5,\"total\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.orderType").value("Dine-In"))
                .andExpect(jsonPath("$.tableId").value(5))
                .andExpect(jsonPath("$.total").value(100.0));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        // Define behavior for the mock service
        doNothing().when(orderService).deleteOrder(1);

        // Perform DELETE request and check results
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }
}

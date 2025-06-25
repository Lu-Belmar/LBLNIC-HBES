package test.java.com.example.HardBoiledEgg;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.HardBoiledEgg.model.Envio;
import com.example.HardBoiledEgg.model.Venta;

import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EnvioServiceTest {

    @Autowired
    private com.example.HardBoiledEgg.service.envioService envioService;

    @MockBean
    private EnvioRepository envioRepository;

    @Test
    public void testFindAll(){
        Venta venta = new Venta();
        venta.setId(1);
        venta.setMonto(20000);
        venta.setInventario(1);
        venta.setCliente(1);

        when(envioRepository.findAll()).thenReturn(List.of(new Envio(1,10,"Av. Juanin",venta)));

        List<Envio> envios = envioService.findAll();

        assertNotNull(envios);
        assertEquals(1,ventas.size());
    }
}

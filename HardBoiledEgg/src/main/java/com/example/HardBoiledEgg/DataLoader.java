package com.example.HardBoiledEgg;

import com.example.HardBoiledEgg.controller.clienteController;
import com.example.HardBoiledEgg.model.Categorias;
import com.example.HardBoiledEgg.model.Cliente;
import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.Empleado;
import com.example.HardBoiledEgg.model.Envio;
import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.model.Proveedores;
import com.example.HardBoiledEgg.model.Tienda;
import com.example.HardBoiledEgg.model.Venta;
import com.example.HardBoiledEgg.repository.categoriasRepository;
import com.example.HardBoiledEgg.repository.clienteRepository;
import com.example.HardBoiledEgg.repository.direccionRepository;
import com.example.HardBoiledEgg.repository.empleadoRepository;
import com.example.HardBoiledEgg.repository.envioRepository;
import com.example.HardBoiledEgg.repository.inventariotiendaRepository;
import com.example.HardBoiledEgg.repository.productoRepository;
import com.example.HardBoiledEgg.repository.proveedoresRepository;
import com.example.HardBoiledEgg.repository.tiendaRepository;
import com.example.HardBoiledEgg.repository.ventaRepository;
import com.example.HardBoiledEgg.service.productoService;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

  @Autowired
  private categoriasRepository categoriasrepository;
  @Autowired
  private clienteRepository clienterepository;
  @Autowired
  private direccionRepository direccionrepository;
  @Autowired
  private empleadoRepository empleadorepository;
  @Autowired
  private envioRepository enviorepository;
  @Autowired
  private inventariotiendaRepository inventariotiendarepository;
  @Autowired
  private productoRepository productorepository;
  @Autowired
  private proveedoresRepository proveedoresrepository;
  @Autowired
  private tiendaRepository tiendarepository;
  @Autowired
  private ventaRepository ventarepository;

  @Override
  public void run(String... args) throws Exception {
    Faker faker = new Faker();
    Random random = new Random();   
    Set<String> generosUnicos = new HashSet<>();
    while (generosUnicos.size() < 3) {
        generosUnicos.add(faker.book().genre());
    }

    List<String> listaGeneros = new ArrayList<>(generosUnicos);
    
    for (int i = 0; i < 3; i++) {
        Categorias categorias = new Categorias();
        categorias.setNombre(listaGeneros.get(i));
        categorias.setDescripcion(faker.lorem().characters());
        categoriasrepository.save(categorias);


           
      for (int m = 0; m < 3; m++){
        Proveedores proveedores = new Proveedores();
        proveedores.setId(null);
        proveedores.setNombre(faker.book().author());
        proveedores.setTelefono(faker.number().numberBetween(900000000, 999999999));
        proveedoresrepository.save(proveedores);

        for (int j = 0; j < 3; j++) {
          Producto producto = new Producto();
          producto.setId(null);
          producto.setNombre(faker.commerce().productName());
          producto.setCategoria(categorias);
          producto.setMarca(faker.brand().sport());
          producto.setProveedor(proveedores);
          productorepository.save(producto);
      }
      
  } 
      
  }

  for (int i = 0; i < 3; i++){
    String correo;
    String nombrefaker;
    String apellidofaker;
    String nombrecompletofaker;
    nombrefaker = faker.name().firstName();
    apellidofaker = faker.name().lastName();
    correo = nombrefaker + "." + apellidofaker + "@" + faker.domain();
    nombrecompletofaker = nombrefaker +" "+ apellidofaker;
    Cliente cliente = new Cliente();
    cliente.setId(null);
    cliente.setNombre(nombrecompletofaker);
    cliente.setRun(faker.number().numberBetween(80000000, 100000000));
    cliente.setTelefono(faker.number().numberBetween(900000000, 999999999));
    cliente.setCorreo(correo);

    for (int x = 0; i<3; x++){
      String correo2;
      String nombrefaker2;
      String apellidofaker2;
      String nombrecompletofaker2;
      nombrefaker2 = faker.name().firstName();
      apellidofaker2 = faker.name().lastName();
      correo = nombrefaker2 + "." + apellidofaker2 + "@" + faker.domain();
      nombrecompletofaker2 = nombrefaker2 +" "+ apellidofaker2;
      correo2 = faker.name().firstName() + "." + faker.name().lastName() + "@" + faker.domain();
      Empleado empleado = new Empleado();
      empleado.setId(null);
      empleado.setCorreo(correo2);
      empleado.setRun(null);
      empleado.setNombre(nombrecompletofaker2);

    }
  }




}
}







/*      
      for (int m = 0; m < 3; m++){
        Proveedores proveedores = new Proveedores();
        proveedores.setId(m+1);
        proveedores.setNombre(faker.book().author());
        proveedores.setTelefono(faker.number().numberBetween(900000000, 999999999));
        proveedoresrepository.save(proveedores);

      for (int j = 0; j < 3; j++) {
      Producto producto = new Producto();
      producto.setId(j+1);
      producto.setNombre(faker.commerce().productName());
      producto.setCategoria(categorias);
      producto.setMarca(faker.brand().sport());
      producto.setProveedor(proveedores);
      productorepository.save(producto);
    }
      
  } */

//@Profile("!test")
//@Component
//public class DataLoader implements CommandLineRunner{

   // @Autowired
    //private ClienteRepository clienterepository; aqui irían los repositorios que necesitamos 

  //  @Override
  //  public void run(String... args) throws Exception {
        //Faker faker = new Faker();
        //Random random = new Random();



    //}
//}



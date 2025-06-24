package com.example.HardBoiledEgg;

import com.example.HardBoiledEgg.controller.clienteController;
import com.example.HardBoiledEgg.model.Categorias;
import com.example.HardBoiledEgg.model.Cliente;
import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.DireccionCliente;
import com.example.HardBoiledEgg.model.DireccionEmpleado;
import com.example.HardBoiledEgg.model.DireccionTienda;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
/* 

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

            // 1. Primero creamos algunas entidades principales
        List<Cliente> clientes = crearClientes(faker, 5);
        List<Empleado> empleados = crearEmpleados(faker, 5);
        List<Tienda> tiendas = crearTiendas(faker, 4);
        List<Categorias> categorias = crearCategorias(faker, 5);
        List<Proveedores> proveedores = crearProveedores(faker, 8);
        List<Producto> productos = crearProductos(faker, 100, categorias, proveedores);
        crearInventarios(faker, productos, tiendas);
        List<InventarioTienda> inventarios = inventariotiendarepository.findAll();
        List<Venta> ventas = crearVentas(faker, 100, clientes, inventarios);
        crearEnvios(faker, ventas);


        
        // 2. Creamos direcciones asociadas
        crearDireccionesClientes(faker, clientes);
        crearDireccionesEmpleados(faker, empleados);
        crearDireccionesTiendas(faker, tiendas);
    }
    
    private List<Tienda> crearTiendas(Faker faker, int cantidad){
      List<Tienda> tiendas = new ArrayList<>();
      String tiendacorreo = "EcoMarket@HardBoiledEgg.cl";
      for (int i = 0; i < cantidad; i++) {
        Tienda t = new Tienda();
        t.setCorreo(tiendacorreo);
        t.setTelefono(faker.number().numberBetween(10000, 1000000));
        tiendas.add(tiendarepository.save(t)); // Guardar cada tienda
      }
      return tiendas;
    }

    private List<Empleado> crearEmpleados(Faker faker, int cantidad){
      List<Empleado> empleados = new ArrayList<>();
      for (int i = 0; i < cantidad; i++) {
        Empleado e = new Empleado();
        e.setNombre(faker.name().fullName());
        e.setCorreo(faker.internet().emailAddress());
        e.setRun(faker.number().numberBetween(1000, 10000));
        e.setTelefono(faker.number().numberBetween(10000, 1000000));
        e.setSalario(faker.number().numberBetween(4219421, 900000));
        empleados.add(empleadorepository.save(e));
      }
      return empleados;

    }

    private List<Cliente> crearClientes(Faker faker, int cantidad) {
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            Cliente c = new Cliente();
            c.setNombre(faker.name().fullName());
            c.setCorreo(faker.internet().emailAddress());
            c.setRun(faker.number().numberBetween(1000, 10000));
            c.setTelefono(faker.number().numberBetween(10000, 1000000));
            clientes.add(clienterepository.save(c));
        }
        return clientes;
    }
    
    private void crearDireccionesClientes(Faker faker, List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            DireccionCliente dir = new DireccionCliente();
            dir.setCalle(faker.address().streetAddress());
            dir.setCiudad(faker.address().city());
            dir.setRegion(faker.address().state());
            DireccionCliente saved = direccionrepository.save(dir);
            cliente.setDireccion(saved); // Establece la relación inversa
            clienterepository.save(cliente);
        }
    }
    
    private void crearDireccionesEmpleados(Faker faker, List<Empleado> empleados) {
        for (Empleado empleado : empleados) {
            DireccionEmpleado dir = new DireccionEmpleado();
            dir.setCalle(faker.address().streetAddress());
            dir.setCiudad(faker.address().city());
            dir.setRegion(faker.address().state());
            
            DireccionEmpleado saved = direccionrepository.save(dir);
            empleado.setDireccion(saved);
            empleadorepository.save(empleado);
        }
    }
    
    private void crearDireccionesTiendas(Faker faker, List<Tienda> tiendas) {
        for (Tienda tienda : tiendas) {
            DireccionTienda dir = new DireccionTienda();
            dir.setCalle(faker.address().streetAddress());
            dir.setCiudad(faker.address().city());
            dir.setRegion(faker.address().state());
            
            DireccionTienda saved = direccionrepository.save(dir);
            tienda.setDireccion(saved);
            tiendarepository.save(tienda);
        }
    }

    private List<Categorias> crearCategorias(Faker faker, int cantidad) {
    List<Categorias> categorias = new ArrayList<>();
    Set<String> nombresUnicos = new HashSet<>();
    
      while (categorias.size() < cantidad) {
          Categorias cat = new Categorias();
          String nombre;
          do {
              nombre = faker.commerce().department();
          } while (nombresUnicos.contains(nombre));
          
          nombresUnicos.add(nombre);
          cat.setNombre(nombre);
          cat.setDescripcion(faker.lorem().sentence());
          
          categorias.add(categoriasrepository.save(cat));
      }
      return categorias;
   }

   private List<Proveedores> crearProveedores(Faker faker, int cantidad) {
    List<Proveedores> proveedores = new ArrayList<>();
    
    for (int i = 0; i < cantidad; i++) {
        Proveedores prov = new Proveedores();
        prov.setNombre(faker.company().name());
        prov.setTelefono(faker.number().numberBetween(900_000_000, 999_999_999));
        
        proveedores.add(proveedoresrepository.save(prov));
    }
    return proveedores;
  }


  private List<Producto> crearProductos(Faker faker, int cantidad, List<Categorias> categorias, List<Proveedores> proveedores) {
    List<Producto> productos = new ArrayList<>();
    Random random = new Random();
    
    for (int i = 0; i < cantidad; i++) {
        Producto prod = new Producto();
        prod.setNombre(faker.commerce().productName());
        prod.setMarca(faker.company().name());
        
        // Asignar categoría y proveedor aleatorios
        prod.setCategoria(categorias.get(random.nextInt(categorias.size())));
        prod.setProveedor(proveedores.get(random.nextInt(proveedores.size())));
        
        productos.add(productorepository.save(prod));
    }
    return productos;
  }

  private void crearInventarios(Faker faker, List<Producto> productos, List<Tienda> tiendas) {
    Random random = new Random();
    
      for (Tienda tienda : tiendas) {
          for (Producto producto : productos) {
              // 70% de probabilidad de que un producto esté en una tienda
              if (random.nextDouble() < 0.7) {
                  InventarioTienda inv = new InventarioTienda();
                  inv.setProducto(producto);
                  inv.setTienda(tienda);
                  inv.setStock(random.nextInt(100) + 10); // Stock entre 10-110
                  inv.setPrecioLocal(random.nextInt(50000) + 5000); // Precio entre $5.000-$55.000
                  
                  inventariotiendarepository.save(inv);
              }
          }
      }
  }



    private List<Venta> crearVentas(Faker faker, int cantidad, List<Cliente> clientes, List<InventarioTienda> todosInventarios) {
      List<Venta> ventas = new ArrayList<>();
      Random random = new Random();
    List<InventarioTienda> inventariosDisponibles = todosInventarios.stream()
        .filter(inv -> inv.getVenta() == null)
        .collect(Collectors.toList());

    if (inventariosDisponibles.size() < cantidad) {
        throw new RuntimeException("No hay suficientes inventarios disponibles. Se necesitan " + 
            cantidad + " pero solo hay " + inventariosDisponibles.size() + " disponibles.");
    }
    Collections.shuffle(inventariosDisponibles);

    for (int i = 0; i < cantidad; i++) {
        Venta venta = new Venta();
        venta.setCliente(clientes.get(random.nextInt(clientes.size())));
        
        InventarioTienda inventario = inventariosDisponibles.get(i);
        venta.setInventario(inventario);
        
        int cantidadProductos = random.nextInt(5) + 1;
        venta.setMonto(inventario.getPrecioLocal() * cantidadProductos);

        inventario.setVenta(venta);
        
        ventas.add(ventarepository.save(venta));
    }
    return ventas;
}

      private void crearEnvios(Faker faker, List<Venta> ventas) {
        for (Venta venta : ventas) {
            // 60% de probabilidad de que una venta tenga envío
            if (new Random().nextDouble() < 0.6) {
                Envio envio = new Envio();
                envio.setVenta(venta);
                envio.setFecha_Creacion_Envio(LocalDateTime.now()
                    .minusDays(new Random().nextInt(30))); // Envíos de hasta 30 días atrás
                envio.setDireccion(faker.address().fullAddress());
                
                enviorepository.save(envio);
            }
        }
      }
}  
  






*/
/*      


    Set<String> generosUnicos = new HashSet<>();
    String tiendacorreo = "EcoMarket@HardBoiledEgg.cl";
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

    for (int h = 0; h < 3; h++){
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


    for (int x = 0; h<3; x++){
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
      empleado.setRun(faker.number().numberBetween(80000000, 100000000));
      empleado.setNombre(nombrecompletofaker2);
      empleado.setSalario(faker.number().numberBetween(10000, 20000));
      empleado.setTelefono(faker.number().numberBetween(900000000, 999999999));

      for (int y = 0; i<3; y++){
        Tienda tienda = new Tienda();
        tienda.setCorreo(tiendacorreo);
        tienda.setTelefono(faker.number().numberBetween(900000000, 999999999));
        
        for (int z = 0; z<3; z++){
          InventarioTienda inventarioTienda = new InventarioTienda();
          inventarioTienda.setPrecioLocal(faker.number().numberBetween(1000, 50000));
          inventarioTienda.setProducto(producto);
          inventarioTienda.setStock(faker.number().numberBetween(1, 150));
          inventarioTienda.setTienda(tienda);

          

        }


      }
    }
  }

          
      }
      
  } 
      
  }




}








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



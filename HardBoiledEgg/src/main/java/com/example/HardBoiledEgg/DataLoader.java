package com.example.HardBoiledEgg;

import com.example.HardBoiledEgg.model.*;
import com.example.HardBoiledEgg.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Profile("dev") // Se ejecutará solo en el perfil de desarrollo
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private categoriasRepository categoriasrepository;
    @Autowired private clienteRepository clienterepository;
    @Autowired private direccionRepository direccionrepository;
    @Autowired private empleadoRepository empleadorepository;
    @Autowired private envioRepository enviorepository;
    @Autowired private inventariotiendaRepository inventariotiendarepository;
    @Autowired private productoRepository productorepository;
    @Autowired private proveedoresRepository proveedoresrepository;
    @Autowired private tiendaRepository tiendarepository;
    @Autowired private ventaRepository ventarepository;

    @Override
    public void run(String... args) throws Exception {
        if (tiendarepository.count() > 0) {
            System.out.println("====== DATALOADER: La base de datos ya contiene datos. No se generarán nuevos datos. ======");
            return;
        }

        System.out.println("====== DATALOADER: Iniciando la carga de datos de prueba... ======");
        Faker faker = new Faker();


        List<Categorias> categorias = crearCategorias(faker, 5);
        List<Proveedores> proveedores = crearProveedores(faker, 8);
        List<Tienda> tiendas = crearTiendas(faker, 4);
        List<Cliente> clientes = crearClientes(faker, 15);
        List<Empleado> empleados = crearEmpleados(faker, 10);
        
        List<Producto> productos = crearProductos(faker, 100, categorias, proveedores);

        crearInventarios(faker, productos, tiendas);

        List<InventarioTienda> inventarios = inventariotiendarepository.findAll();

        List<Venta> ventas = crearVentas(faker, 100, clientes, inventarios);
        crearEnvios(faker, ventas);   

        crearDireccionesClientes(faker, clientes);
        crearDireccionesEmpleados(faker, empleados);
        crearDireccionesTiendas(faker, tiendas);
        
        System.out.println("====== DATALOADER COMPLETE: La base de datos ha sido poblada con datos de prueba. ======");
    }

    private List<Tienda> crearTiendas(Faker faker, int cantidad) {
        List<Tienda> tiendas = new ArrayList<>();
        String tiendacorreo = "EcoMarket@HardBoiledEgg.cl";
        for (int i = 0; i < cantidad; i++) {
            Tienda t = new Tienda();
            t.setCorreo(tiendacorreo);
            t.setTelefono(faker.number().numberBetween(10000000, 99999999));
            tiendas.add(tiendarepository.save(t));
        }
        return tiendas;
    }

    private List<Empleado> crearEmpleados(Faker faker, int cantidad) {
        List<Empleado> empleados = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            Empleado e = new Empleado();
            e.setNombre(faker.name().fullName());
            e.setCorreo(faker.internet().emailAddress());
            e.setRun(faker.number().numberBetween(10000000, 25000000));
            e.setTelefono(faker.number().numberBetween(10000000, 99999999));
            e.setSalario(faker.number().numberBetween(450000, 1200000));
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
            c.setRun(faker.number().numberBetween(10000000, 25000000));
            c.setTelefono(faker.number().numberBetween(10000000, 99999999));
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
            cliente.setDireccion(saved);
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
            String nombre;
            do {
                nombre = faker.commerce().department();
            } while (nombresUnicos.contains(nombre));
            nombresUnicos.add(nombre);
            Categorias cat = new Categorias();
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
            prov.setTelefono(faker.number().numberBetween(900000000, 999999999));
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
                if (random.nextDouble() < 0.7) {
                    InventarioTienda inv = new InventarioTienda();
                    inv.setProducto(producto);
                    inv.setTienda(tienda);
                    inv.setStock(random.nextInt(100) + 10);
                    inv.setPrecioLocal(random.nextInt(50000) + 5000);
                    inventariotiendarepository.save(inv);
                }
            }
        }
    }

    private List<Venta> crearVentas(Faker faker, int cantidad, List<Cliente> clientes, List<InventarioTienda> todosInventarios) {
        List<Venta> ventas = new ArrayList<>();
        Random random = new Random();
        List<InventarioTienda> inventariosDisponibles = todosInventarios.stream()
            .filter(inv -> !ventarepository.existsByInventario(inv))
            .collect(Collectors.toList());

        if (inventariosDisponibles.isEmpty() || inventariosDisponibles.size() < cantidad) {
            System.out.println("ADVERTENCIA: No hay suficientes inventarios disponibles para crear la cantidad deseada de ventas.");
            return ventas;
        }
        
        Collections.shuffle(inventariosDisponibles);

        for (int i = 0; i < cantidad; i++) {
            Venta venta = new Venta();
            venta.setCliente(clientes.get(random.nextInt(clientes.size())));
            
            InventarioTienda inventario = inventariosDisponibles.get(i);
            venta.setInventario(inventario);
            
            int cantidadProductos = random.nextInt(5) + 1;
            venta.setMonto(inventario.getPrecioLocal() * cantidadProductos);

            ventas.add(ventarepository.save(venta));
        }
        return ventas;
    }

    private void crearEnvios(Faker faker, List<Venta> ventas) {
        Random random = new Random();
        for (Venta venta : ventas) {
            if (random.nextDouble() < 0.6) {
                Envio envio = new Envio();
                envio.setVenta(venta);
                envio.setFecha_Creacion_Envio(LocalDateTime.now().minusDays(random.nextInt(30)));
                envio.setDireccion(faker.address().fullAddress());
                enviorepository.save(envio);
            }
        }
    }
}
package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.repo.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ClienteRepository clienteRepository;
    private final EstadoRepository estadoRepository;
    @PostConstruct
    public void init() {
        if (rolRepository.count() == 0) {
            Rol admin = Rol.builder().nombre("ADMIN").build();
            Rol mecanico = Rol.builder().nombre("MECANICO").build();
            Rol chofer = Rol.builder().nombre("CHOFER").build();
            Rol gerente = Rol.builder().nombre("GERENTE").build();


            rolRepository.save(admin);
            rolRepository.save(mecanico);
            rolRepository.save(chofer);
            rolRepository.save(gerente);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // --- Usuarios base ---
            usuarioRepository.save(Usuario.builder()
                    .nombre("Rafael")
                    .apellido("Ortega")
                    .correo("rortega@admin.com")
                    .contrasena("rafael123")
                    .rol(admin)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Elena")
                    .apellido("Morales")
                    .correo("emorales@admin.com")
                    .contrasena("elena123")
                    .rol(admin)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Jorge")
                    .apellido("Silva")
                    .correo("jsilva@admin.com")
                    .contrasena("jorge123")
                    .rol(admin)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Carlos")
                    .apellido("Mecánico")
                    .correo("mecanico@demo.com")
                    .contrasena("mecanico123")
                    .rol(mecanico)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Luis")
                    .apellido("Martínez")
                    .correo("lmartinez@demo.com")
                    .contrasena("luis123")
                    .rol(mecanico)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Pedro")
                    .apellido("Gómez")
                    .correo("pgomez@demo.com")
                    .contrasena("pedro123")
                    .rol(mecanico)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Ana")
                    .apellido("Reyes")
                    .correo("areyes@demo.com")
                    .contrasena("ana123")
                    .rol(mecanico)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Juan")
                    .apellido("Perez")
                    .correo("perez@demo.com")
                    .contrasena("chofer123")
                    .rol(chofer)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Luis")
                    .apellido("Ramírez")
                    .correo("lramirez@demo.com")
                    .contrasena("luis123")
                    .rol(chofer)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Carlos")
                    .apellido("Hernández")
                    .correo("chernandez@demo.com")
                    .contrasena("carlos123")
                    .rol(chofer)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Miguel")
                    .apellido("Torres")
                    .correo("mtorres@demo.com")
                    .contrasena("miguel123")
                    .rol(chofer)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Emilio")
                    .apellido("Garcia")
                    .correo("emilio@demo.com")
                    .contrasena("amilio123")
                    .rol(gerente)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Laura")
                    .apellido("Sánchez")
                    .correo("lsanchez@demo.com")
                    .contrasena("laura123")
                    .rol(gerente)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Andrés")
                    .apellido("López")
                    .correo("alopez@demo.com")
                    .contrasena("andres123")
                    .rol(gerente)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Verónica")
                    .apellido("Castro")
                    .correo("vcastro@demo.com")
                    .contrasena("vero123")
                    .rol(gerente)
                    .build());
        }

        // --- CLIENTES DE PRUEBA ---
        if (clienteRepository.count() == 0) {

            Cliente c1 = new Cliente();
            c1.setNombre("Jorge");
            c1.setCelular("1234567890");
            c1.setDireccion("New York");
            c1.setClienteUNAM(false);

            Auto a1 = new Auto();
            a1.setMarca("Nissan");
            a1.setModelo("Sentra");
            a1.setAnio(2020);
            a1.setPlaca("ABC123");
            a1.setColor("Rojo");
            a1.setCliente(c1);
            c1.setAutos(List.of(a1));

            Cliente c2 = new Cliente();
            c2.setNombre("Laura");
            c2.setCelular("5551112222");
            c2.setDireccion("CDMX");
            c2.setClienteUNAM(false);

            Auto a2 = new Auto();
            a2.setMarca("Toyota");
            a2.setModelo("Corolla");
            a2.setAnio(2021);
            a2.setPlaca("XYZ789");
            a2.setColor("Azul");
            a2.setCliente(c2);
            c2.setAutos(List.of(a2));

            Cliente c3 = new Cliente();
            c3.setNombre("Pedro");
            c3.setCelular("5553334444");
            c3.setDireccion("Monterrey");
            c3.setClienteUNAM(true);

            Auto a3 = new Auto();
            a3.setMarca("Honda");
            a3.setModelo("Civic");
            a3.setAnio(2018);
            a3.setPlaca("PLT123");
            a3.setColor("Negro");
            a3.setCliente(c3);
            c3.setAutos(List.of(a3));

            Cliente c4 = new Cliente();
            c4.setNombre("María");
            c4.setCelular("5552221111");
            c4.setDireccion("Guadalajara");
            c4.setClienteUNAM(false);

            Auto a4 = new Auto();
            a4.setMarca("Ford");
            a4.setModelo("Focus");
            a4.setAnio(2019);
            a4.setPlaca("FRD456");
            a4.setColor("Gris");
            a4.setCliente(c4);
            c4.setAutos(List.of(a4));

            Cliente c5 = new Cliente();
            c5.setNombre("Luis");
            c5.setCelular("5557778888");
            c5.setDireccion("Puebla");
            c5.setClienteUNAM(true);

            Auto a5 = new Auto();
            a5.setMarca("Mazda");
            a5.setModelo("CX-3");
            a5.setAnio(2020);
            a5.setPlaca("MAZ789");
            a5.setColor("Blanco");
            a5.setCliente(c5);
            c5.setAutos(List.of(a5));

            Cliente c6 = new Cliente();
            c6.setNombre("Valeria");
            c6.setCelular("5556665555");
            c6.setDireccion("Querétaro");
            c6.setClienteUNAM(false);

            Auto a6 = new Auto();
            a6.setMarca("Kia");
            a6.setModelo("Rio");
            a6.setAnio(2021);
            a6.setPlaca("KIA321");
            a6.setColor("Azul Marino");
            a6.setCliente(c6);
            c6.setAutos(List.of(a6));

            Cliente c7 = new Cliente();
            c7.setNombre("Fernando");
            c7.setCelular("5551239876");
            c7.setDireccion("Tijuana");
            c7.setClienteUNAM(false);

            Auto a7 = new Auto();
            a7.setMarca("Volkswagen");
            a7.setModelo("Jetta");
            a7.setAnio(2017);
            a7.setPlaca("VW654");
            a7.setColor("Negro");
            a7.setCliente(c7);
            c7.setAutos(List.of(a7));

            Cliente c8 = new Cliente();
            c8.setNombre("Ana");
            c8.setCelular("5553332221");
            c8.setDireccion("León");
            c8.setClienteUNAM(true);

            Auto a8 = new Auto();
            a8.setMarca("Hyundai");
            a8.setModelo("Tucson");
            a8.setAnio(2022);
            a8.setPlaca("HYD987");
            a8.setColor("Gris Oscuro");
            a8.setCliente(c8);
            c8.setAutos(List.of(a8));

            Cliente c9 = new Cliente();
            c9.setNombre("Miguel");
            c9.setCelular("5559090909");
            c9.setDireccion("Cancún");
            c9.setClienteUNAM(false);

            Auto a9 = new Auto();
            a9.setMarca("Chevrolet");
            a9.setModelo("Aveo");
            a9.setAnio(2021);
            a9.setPlaca("CHE111");
            a9.setColor("Rojo");
            a9.setCliente(c9);
            c9.setAutos(List.of(a9));

            Cliente c10 = new Cliente();
            c10.setNombre("Elena");
            c10.setCelular("5554567890");
            c10.setDireccion("Toluca");
            c10.setClienteUNAM(true);

            Auto a10 = new Auto();
            a10.setMarca("Jeep");
            a10.setModelo("Compass");
            a10.setAnio(2019);
            a10.setPlaca("JEP202");
            a10.setColor("Verde Olivo");
            a10.setCliente(c10);
            c10.setAutos(List.of(a10));

            Cliente c11 = new Cliente();
            c11.setNombre("Lucía");
            c11.setCelular("5551110000");
            c11.setDireccion("Veracruz");
            c11.setClienteUNAM(false);

            Auto a11 = new Auto();
            a11.setMarca("BMW");
            a11.setModelo("Serie 1");
            a11.setAnio(2023);
            a11.setPlaca("BMW333");
            a11.setColor("Blanco");
            a11.setCliente(c11);
            c11.setAutos(List.of(a11));

            Cliente c12 = new Cliente();
            c12.setNombre("Pablo");
            c12.setCelular("5551010101");
            c12.setDireccion("Hermosillo");
            c12.setClienteUNAM(false);

            Auto a12 = new Auto();
            a12.setMarca("Audi");
            a12.setModelo("A3");
            a12.setAnio(2022);
            a12.setPlaca("AUD444");
            a12.setColor("Negro");
            a12.setCliente(c12);
            c12.setAutos(List.of(a12));

            Cliente c13 = new Cliente();
            c13.setNombre("Raúl");
            c13.setCelular("5557776666");
            c13.setDireccion("Durango");
            c13.setClienteUNAM(true);

            Auto a13 = new Auto();
            a13.setMarca("Suzuki");
            a13.setModelo("Swift");
            a13.setAnio(2018);
            a13.setPlaca("SUZ555");
            a13.setColor("Amarillo");
            a13.setCliente(c13);
            c13.setAutos(List.of(a13));

            Cliente c14 = new Cliente();
            c14.setNombre("Camila");
            c14.setCelular("5554443332");
            c14.setDireccion("Mazatlán");
            c14.setClienteUNAM(false);

            Auto a14 = new Auto();
            a14.setMarca("Seat");
            a14.setModelo("Ibiza");
            a14.setAnio(2020);
            a14.setPlaca("SEA666");
            a14.setColor("Rosa");
            a14.setCliente(c14);
            c14.setAutos(List.of(a14));

            Cliente c15 = new Cliente();
            c15.setNombre("José");
            c15.setCelular("5559876543");
            c15.setDireccion("Mérida");
            c15.setClienteUNAM(false);

            Auto a15 = new Auto();
            a15.setMarca("Peugeot");
            a15.setModelo("208");
            a15.setAnio(2019);
            a15.setPlaca("PEU777");
            a15.setColor("Plateado");
            a15.setCliente(c15);
            c15.setAutos(List.of(a15));

            Cliente c16 = new Cliente();
            c16.setNombre("Andrés");
            c16.setCelular("5550900789");
            c16.setDireccion("Colima");
            c16.setClienteUNAM(true);

            Auto a16 = new Auto();
            a16.setMarca("Subaru");
            a16.setModelo("Impreza");
            a16.setAnio(2023);
            a16.setPlaca("SUB888");
            a16.setColor("Azul Marino");
            a16.setCliente(c16);
            c16.setAutos(List.of(a16));

            Cliente c17 = new Cliente();
            c17.setNombre("Daniela");
            c17.setCelular("5551122334");
            c17.setDireccion("Aguascalientes");
            c17.setClienteUNAM(false);

            Auto a17 = new Auto();
            a17.setMarca("Fiat");
            a17.setModelo("Punto");
            a17.setAnio(2017);
            a17.setPlaca("FIA999");
            a17.setColor("Gris Claro");
            a17.setCliente(c17);
            c17.setAutos(List.of(a17));

            Cliente c18 = new Cliente();
            c18.setNombre("Sergio");
            c18.setCelular("5556677889");
            c18.setDireccion("Chihuahua");
            c18.setClienteUNAM(false);

            Auto a18 = new Auto();
            a18.setMarca("Volkswagen");
            a18.setModelo("Golf");
            a18.setAnio(2021);
            a18.setPlaca("VOL111");
            a18.setColor("Rojo");
            a18.setCliente(c18);
            c18.setAutos(List.of(a18));

            Cliente c19 = new Cliente();
            c19.setNombre("Gabriela");
            c19.setCelular("5553344556");
            c19.setDireccion("Morelia");
            c19.setClienteUNAM(true);

            Auto a19 = new Auto();
            a19.setMarca("Renault");
            a19.setModelo("Kwid");
            a19.setAnio(2022);
            a19.setPlaca("REN222");
            a19.setColor("Blanco");
            a19.setCliente(c19);
            c19.setAutos(List.of(a19));

            Cliente c20 = new Cliente();
            c20.setNombre("Fernando");
            c20.setCelular("5557788990");
            c20.setDireccion("Cuernavaca");
            c20.setClienteUNAM(false);

            Auto a20 = new Auto();
            a20.setMarca("Volvo");
            a20.setModelo("XC40");
            a20.setAnio(2023);
            a20.setPlaca("VOL333");
            a20.setColor("Negro");
            a20.setCliente(c20);
            c20.setAutos(List.of(a20));

            clienteRepository.saveAll(List.of(
                    c1, c2, c3, c4, c5,
                    c6, c7, c8, c9, c10,
                    c11, c12, c13, c14, c15,
                    c16, c17, c18, c19, c20
            ));


        }
        if (estadoRepository.count() == 0) {
            estadoRepository.save(Estado.builder()
                    .estado("En transito")
                    .build());

            estadoRepository.save(Estado.builder()
                    .estado("En taller - pendiente revisión")
                    .build());

            estadoRepository.save(Estado.builder()
                    .estado("Diagnostico enviado")
                    .build());

            estadoRepository.save(Estado.builder()
                    .estado("Pendiente cotización")
                    .build());

            estadoRepository.save(Estado.builder()
                    .estado("Pendiente aprobación")
                    .build());

            estadoRepository.save(Estado.builder()
                    .estado("Pendiente recolección")
                    .build());

            estadoRepository.save(Estado.builder()
                    .estado("En reparación")
                    .build());

            estadoRepository.save(Estado.builder()
                    .estado("Pendiente VoBo")
                    .build());

            estadoRepository.save(Estado.builder()
                    .estado("En entrega")
                    .build());

            estadoRepository.save(Estado.builder()
                    .estado("Finalizado")
                    .build());
        }
    }
}

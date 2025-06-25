package com.example.HardBoiledEgg;

import javax.xml.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.DireccionCliente;
import com.example.HardBoiledEgg.model.DireccionEmpleado;
import com.example.HardBoiledEgg.model.DireccionTienda;
import com.example.HardBoiledEgg.model.Empleado;

import jakarta.validation.ConstraintViolation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Set;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;


package com.valmeida.begin;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import com.valmeida.begin.util.DatabaseCleaner;
import com.valmeida.begin.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroResturanteIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	TestDataFactory testData;	
	
	@Before
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		databaseCleaner.clearTables();
		
		testData.adicionaRestaurantes();
	}
	
	@Test
	public void deveRetornar200_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornar201_QuandoAdicionarRestaurante() {
		try {
			InputStream stream = ResourceUtils.class.getResourceAsStream("/adiciona_restaurante.json");
			String restauranteJson = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
			
			given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(restauranteJson)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
		} catch (IOException e) {
			throw new RuntimeException(e);		
			}
	}
	
	@Test
	public void deveRetornar404_QuandoConsultarSingletonRestuarante() {
		given()
			.accept(ContentType.JSON)
			.pathParam("restauranteId", testData.RESOURCE_INEXISTENTE)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveConterTamanhoDaListaRestaurantes_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("", Matchers.hasSize(testData.restaurantes.size()));
	}
	
	@Test 
	public void deveRetornar200_QuandoConsultarSingletonRestaurante() {
		given()
			.accept(ContentType.JSON)
			.pathParam("restauranteId", testData.restaurantes.get(0).getId())
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(testData.restaurantes.get(0).getNome()));			
		
	}

}

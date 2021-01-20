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
public class CadastroCozinhaIT {
	
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
		RestAssured.basePath = "/cozinhas";
		
		databaseCleaner.clearTables();
		
		testData.adicionaCozinhas();
		
	}
	
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
		
	}
	
	@Test
	public void deveConterTamanhoDaListaCozinhas_QuandoConsultarCozinhas() {	
		given()
			.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(testData.cozinhas.size()));	
		
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {		
		try {
			InputStream stream = ResourceUtils.class.getResourceAsStream("/adiciona_cozinha.json");
			String cozinhaJson = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
			
			given()
			.body(cozinhaJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.when()
				.post()
			.then()
			.statusCode(HttpStatus.CREATED.value());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	@Test
	public void deveRetornar200_QuandoConsultarSingletonCozinha() {
		given()
			.pathParam("cozinhaId", testData.cozinhas.get(0).getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(testData.cozinhas.get(0).getNome()));
		
	}
	
	@Test
	public void deveRetornar404_QuandoConsultarSingletonCozinha() {
		given()
			.pathParam("cozinhaId", testData.RESOURCE_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
			
	}
	
	
}

package com.bci.smart.com.practicas;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMaps {

  public static void main(String[] args) {/*

    List<String> frases = List.of("Hola mundo", "Java rocks");

      List<String> resultado = frases.stream()
        .peek(frase -> System.out.println("Frase original: " + frase)) // debug
        .flatMap(frase -> {
          String[] palabras = frase.split(" ");
          System.out.println("Partes: " + Arrays.toString(palabras)); // debug
          return Arrays.stream(palabras);
        })
        .peek(palabra -> System.out.println("Palabra final: " + palabra)) // debug
        .collect(Collectors.toList());

    System.out.println("\nResultado final:");
    resultado.forEach(p -> System.out.println("🔹 " + p));
  */
  List<List<Integer>> numbers= List.of(
      List.of(1,2,3),
      List.of(4,5),
      List.of(6)
  );
  numbers.stream().flatMap(x -> x.stream()).forEach(System.out::println);

 // numbers.forEach(System.out::println);

  }

}

package com.example.lykkehjulet.Files

val HIGHSCORES : List<Person> =
    listOf(
        Person("Per Mikkelsen", "2500"),
        Person("Mikkel Persen", "3200"),
        Person("Gabe Newell", "20000"),
        Person("Jørgen Jensen", "25000"),
        Person("Jens Jørgensen", "100000"),
        Person("Mark ZuckerBerg", "12500"),
        Person("Elon Musk", "3000000"),
        Person("Bill Gates", "5000000000")
    )

class Person {
    constructor(Navn: String, Score: String)

    lateinit var name : String
    lateinit var score : String
}

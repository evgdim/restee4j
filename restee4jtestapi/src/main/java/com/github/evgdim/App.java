package com.github.evgdim;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static spark.Spark.*;
import com.google.gson.Gson;
public class App 
{
    public static void main( String[] args ) {
        List<Person> people = initPeople();
        Gson gson = new Gson();

        get("people", (req, res) -> {
            return people;
        }, gson::toJson);
        get("/people/:id", (request, response) -> {
            Long paramId = Long.parseLong(request.params(":id"));
            Optional<Person> person = people.stream().filter(p -> p.getId().equals(paramId)).findAny();
            if(person.isPresent()) {
                return person.get();
            } else {
                response.status(404);
                return "Not found";
            }
        }, gson::toJson);
    }

    private static ArrayList<Person> initPeople() {
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person(1L,"Person1", (short)20));
        people.add(new Person(2L,"Person2", (short)30));
        people.add(new Person(3L,"Person3", (short)40));
        people.add(new Person(4L,"Person4", (short)50));
        return people;
    }

    private static class Person {
        private Long id;
        private String name;
        private Short age;

        public Person(Long id, String name, Short age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Short getAge() {
            return age;
        }
    }
}

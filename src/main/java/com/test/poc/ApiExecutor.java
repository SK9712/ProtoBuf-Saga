package com.test.poc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

@Component
public class ApiExecutor {

    public JsonObject processApiRequest(JsonObject request){
        JsonObject response = new JsonObject();

        String filePath = request.get("filePath").getAsString();
        String mode = request.get("mode").getAsString();

        try {
            if (mode.equalsIgnoreCase("write")) {
                String email = request.get("email").getAsString();
                int id = new Random().nextInt();
                String name = request.get("name").getAsString();
                List<String> number = new Gson().fromJson(request.getAsJsonArray("number"),
                        new TypeToken<List<String>>() {}.getType());

                AddressBookProtos.Person person =
                        AddressBookProtos.Person.newBuilder()
                                .setId(id)
                                .setName(name)
                                .setEmail(email)
                                .addAllNumbers(number)
                                .build();
                AddressBookProtos.AddressBook addressBook
                        = AddressBookProtos.AddressBook.newBuilder().addPeople(person).build();

                FileOutputStream fos = new FileOutputStream(filePath);
                addressBook.writeTo(fos);
            } else {
                int index = request.has("index") ? request.get("index").getAsInt() : 0;

                AddressBookProtos.AddressBook deserializedProto
                        = AddressBookProtos.AddressBook.newBuilder()
                        .mergeFrom(new FileInputStream(filePath)).build();

                JsonObject data = new JsonObject();
                data.addProperty("Id", deserializedProto.getPeople(index).getId());
                data.addProperty("name", deserializedProto.getPeople(index).getName());
                data.addProperty("email", deserializedProto.getPeople(index).getEmail());
                data.addProperty("number", deserializedProto.getPeople(index).getNumbersList().toString());

                response.add("data", data);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.addProperty("result", "failure");
            response.addProperty("reason", ex.getMessage());
            return response;
        }

        response.addProperty("result", "success");

        return response;
    }

}

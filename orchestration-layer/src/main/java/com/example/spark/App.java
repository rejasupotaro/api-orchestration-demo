package com.example.spark;

import static spark.Spark.*;

import com.example.spark.resources.UserResource;
import spark.servlet.SparkApplication;

public class App implements SparkApplication {
    @Override
    public void init() {
        get("/", (req, res) -> "Hello World!");

        get("/users/:id", (req, res) -> {
            String id = req.params(":id");
            return UserResource.DUMMY_USER.toJson();
        });
    }
}

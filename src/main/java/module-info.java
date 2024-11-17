module org.hibernateproject.pruebajavafxconhibernatehello {
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;
    requires java.naming;
    requires javafx.web;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;

    opens org.hibernateproject.pruebajavafxconhibernatehello.model;

    opens org.hibernateproject.pruebajavafxconhibernatehello to javafx.fxml;
    exports org.hibernateproject.pruebajavafxconhibernatehello;

    exports org.hibernateproject.pruebajavafxconhibernatehello.controller;
    opens org.hibernateproject.pruebajavafxconhibernatehello.controller to javafx.fxml;

    exports org.hibernateproject.pruebajavafxconhibernatehello.dao;
    opens org.hibernateproject.pruebajavafxconhibernatehello.dao to javafx.fxml;

    exports org.hibernateproject.pruebajavafxconhibernatehello.dto;
    opens org.hibernateproject.pruebajavafxconhibernatehello.dto to javafx.fxml;

    exports org.hibernateproject.pruebajavafxconhibernatehello.utils;
    opens org.hibernateproject.pruebajavafxconhibernatehello.utils to javafx.fxml;

}
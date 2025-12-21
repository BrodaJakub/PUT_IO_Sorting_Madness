package pl.put.poznan.sortingmadness.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import pl.put.poznan.sortingmadness.Logic.context.SortingContext;
import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.strategy.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@ComponentScan({
        "pl.put.poznan.sortingmadness.app",
        "pl.put.poznan.sortingmadness.Logic",
        "pl.put.poznan.sortingmadness.api"
})
public class Main {
	final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("Początek programu");

        SpringApplication.run(Main.class, args);
    }
}
